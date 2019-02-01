package com.amondel2.techtalk

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class GoalsController {

    SpringSecurityService springSecurityService
    def employeeService
    def goalService
    def index() {
        Employees e = params.mid ? Employees.findById(params.mid) : (params.id ? Employees.findById(params.id) : Employees.findByUser(springSecurityService.getCurrentUser()))
        if (!e) {
            redirect(controller: "projectManager")
            return;
        }

        int year
        try {
            year = params?.myDate_year.toInteger()
        } catch (Exception v) {
            try {
                year = params?.year.toInteger()
            } catch (Exception x) {
                try {
                    year = Calendar.getInstance().get(Calendar.YEAR)
                } catch (Exception z) {
                    throw z
                }
            }
        }
        def date = new GregorianCalendar(year, 0, 1)
        def endDate = new GregorianCalendar(year, 12, 31, 23, 59, 59)

        def gts = KPOType.withCriteria {
            eq('isActive', true)
            between('endDate', date.getTime(), endDate.getTime())
        }

        render(view: "index", model: [emp: e, date: date, companyName: Company.first().name, goalTypes:gts , goalSet : goalService.getGoalSetForEmployee(e?.id, date.get(Calendar.YEAR))])
    }

    def chagneGoals()  {
        def e = params.id ? params : Employees.findByUser(springSecurityService.getCurrentUser())
        def goalSet = goalService.getGoalSetForEmployee(e?.id,params.myDate_year)
        withFormat {
            '*' { render goalSet as JSON}
        }
    }

    def createCard() {
        def p = [:]
        p.id = Utils.getInstance().idGenerator()
        withFormat {
            '*' { render p as JSON}
        }
    }

    def saveGoals() {
        def p = [:]
        p =  goalService.saveGoals(params)
        withFormat {
            '*' { render p as JSON}
        }
    }
}