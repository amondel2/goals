package com.amondel2.techtalk

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

@Transactional
class GoalService {

    GrailsApplication grailsApplication
    MessageSource messageSource
    SpringSecurityService springSecurityService

    def getGoalSetForEmployee(Employees emp, year) {
        year = year ? year.toInteger() : GregorianCalendar.getInstance().get(Calendar.YEAR).toInteger()
        def restultSet = []

        GregorianCalendar gc = new GregorianCalendar(year,0,1,0,0,0)
        GregorianCalendar ec = new GregorianCalendar(year,11,31,23,59,59)
        EmployeeGoal.withCriteria {
            eq('employee', emp)
             between('targetCompletDate',gc.getTime(),ec.getTime())
        }.sort{a,b -> a.title <=> b.title}?.each { EmployeeGoal eg ->
            def rs = [:]
            if( ( !emp.showHidden && eg.status in [GoalStatus.OnTrack,GoalStatus.Behind, GoalStatus.Ongoing, GoalStatus.NotStarted] ) || emp.showHidden ) {
                rs['id'] = eg.id
                rs['title'] = eg.title
                rs['description'] = eg.description
                rs['actualCompletedDate'] = eg.actualCompletedDate
                rs['targetCompletDate'] = eg.targetCompletDate
                rs['orginTargetDate'] = eg.orginTargetDate
                rs['status'] = eg.status
                rs['commentsCount'] = EmployeeGoalComment.countByEmployeeGoal(eg)
                rs['goalType'] = eg.types.collect { it.type.id }
                restultSet.add(rs)
            }
        }
        restultSet
    }

    def getGoalSetForEmployee(empId, year) {
        getGoalSetForEmployee(Employees.findById(empId),year)
    }

    def saveGoalsComments (params) {
        def locale = LocaleContextHolder.getLocale()
        def egc = EmployeeGoal.findById(params.goalId)
        def worked  = [success:true]
        if(egc) {
            EmployeeGoalComment eg = new EmployeeGoalComment()
            try {
                eg.modifiedUser = springSecurityService.getCurrentUser()
                eg.commentStr = params.newComment
                eg.employeeGoal = egc
                eg.save(flush: true, failOnError: true)
            } catch (Exception e) {
                worked = [success:false,msg:
                    eg.errors.collect { errs ->
                        errs.allErrors.collect {
                            messageSource.getMessage(it, locale)
                        }.join(" ")
                    }.join(" ")]

            }
        } else {
            worked = [success:false,msg:"Must Save Parent Goal First"]
        }
        worked
    }


    def saveGoals(p) {
        com.amondel2.techtalk.ExtendTagsTagLib ps = grailsApplication.mainContext.getBean('com.amondel2.techtalk.ExtendTagsTagLib')
        def ids = [:]
        def err = [:]
        Employees emp = Employees.findById(p.empId);
        if(p['ids'] instanceof String) {
            p['ids'] = [p['ids']]
        }
        def locale = LocaleContextHolder.getLocale()
        p['ids'].each { String id ->
            id = id.substring(0, id.indexOf('_'))

            EmployeeGoal eg = EmployeeGoal.findOrCreateById(id)
            eg.id = id
            eg.employee = emp
            eg.title = p[id + "_title"]
            eg.description = p[id + "_descript"]?.trim()
            Calendar c = GregorianCalendar.getInstance();
            if ( eg.status.toString() != p[id + "_status"]) {
                if ( ((GoalStatus)p[id + "_status"]) in [GoalStatus.Cancelled, GoalStatus.Completed]) {
                    eg.actualCompletedDate = new Date()
                    if(eg.targetCompletDate.equals(null)) {
                        eg.targetCompletDate = new Date()
                    }
                } else {
                    eg.actualCompletedDate = null;
                }
            }

            try{
                c.set(p[id + "_targetDate_year"].toInteger(),p[id + "_targetDate_month"].toInteger() - 1,p[id + "_targetDate_day"].toInteger())
                eg.targetCompletDate = c.getTime()
            } catch (Exception e) { }
            if(eg.orginTargetDate == null ) {
                eg.orginTargetDate =  eg.targetCompletDate
            }

            eg.status = p[id + "_status"]
            eg.validate()
            if(eg.hasErrors()) {
                if(!err[id]) {
                    err[id] = []
                }
                eg.errors.each { errs ->
                    errs.allErrors.each {
                        err[id] << messageSource.getMessage(it, locale)
                    }
                }
                try {
                    eg.clearErrors()
                    eg.refresh()
                } catch (Exception e) { }
            } else {
                eg.save(flush: true, failOnError: true)
                EmployeeGoalType.where { employeeGoal == eg }.deleteAll()

                if (p[id + "_types"] instanceof String) {
                    EmployeeGoalType egt = new EmployeeGoalType()
                    egt.employeeGoal = eg
                    egt.type = KPOType.findById(p[id + "_types"])
                    egt.save()
                } else {
                    p[id + "_types"].each {
                        EmployeeGoalType egt = new EmployeeGoalType()
                        egt.employeeGoal = eg
                        egt.type = KPOType.findById(it)
                        egt.save()
                    }
                }
                ids[id] = [ps.generateTitle(goal: eg),eg.orginTargetDate ? eg.orginTargetDate?.format('MM-dd-YYYY') : '']
            }

        }

       [titles:ids,errors: err]

    }
}
