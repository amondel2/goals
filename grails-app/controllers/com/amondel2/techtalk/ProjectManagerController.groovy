package com.amondel2.techtalk

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.hibernate.FetchMode

@Secured(['ROLE_USER'])
class ProjectManagerController extends FranchiseMapperUIController {

    SpringSecurityService springSecurityService
    def assetResourceLocator
    def projectManagerService
    def goalService

    def index() {
        Employees me = Employees.findByUser(springSecurityService.getCurrentUser())
        Employees boss
        if(me && me.bosses && me.bosses.size() > 0) {
            boss = me.bosses.first()?.boss
        }



        def cal = Calendar.getInstance()
        def date = params?.myDate_year ? new GregorianCalendar(params.myDate_year?.toInteger(),0,1) : new GregorianCalendar(cal.get(Calendar.YEAR),0,1)
//        def edate = params?.myDate_year ? new GregorianCalendar(params.myDate_year?.toInteger(),11,31) : new GregorianCalendar(cal.get(Calendar.YEAR),11,31)

        render(view:"index",model:[date:date,boss:boss,companyName:Company.first().name,goalTypes:KPOType.list()])
    }



    def deleteWorkItem() {
        def rs = projectManagerService.deleteWorkItem(params)
        withFormat {
            '*' {
                render(rs as JSON)
            }
        }
    }

    def savePortfolioProjectJobs() {

        def paramsback = projectManagerService.parseResponseSet(params)
        withFormat {
            '*' {
                render(paramsback as JSON)
            }
        }
    }

    def getResultSet() {
        def paramsback = goalService.getResponseSetForEmployee(params?.empId,params.year)
            withFormat {
            '*' {
                render([rs:paramsback[0],smonth:paramsback[1][0],emonth:paramsback[1][1]] as JSON)
            }
        }
    }

    def saveLeaveDate() {
        def msg = "Success"
        try {
            Employees e = Employees.findById(params.empId)
            if (e) {
                def darr = params.leaveDate?.split("/")
                e.endDate = new GregorianCalendar(darr[2]?.toInteger(), darr[0]?.toInteger(), darr[1]?.toInteger()).getTime()
                e.save(flush: true, failOnError: true)
            }
        } catch(Exception e) {
            msg = "Fail"
        }
        withFormat {
            '*' {
                render([rs: msg] as JSON)
            }
        }
    }


    @Override
    String getCompanyId() {
        return Company.first().id
    }

    @Override
    boolean hasReadPermission() {
       true
    }

    @Override
    boolean hasWritePermission() {
       true
    }
    
	@Override
    boolean hasImportExportPermission() {
        return false
    }

    @Override
    SpringSecurityService getSSservice() {
        return springSecurityService
    }
}