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
        if(!e) {
            redirect(controller:"projectManager")
            return;
        }
        def date = params?.myDate_year ? new GregorianCalendar(params.myDate_year?.toInteger(),0,1) : new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),0,1)
        render(view:"index",model:[emp:e,date:date,companyName:Company.first().name,goalTypes:GoalType.list(),goalSet:goalService.getGoalSetForEmployee(e?.id,date.get(Calendar.YEAR))])
    }

    def chagneGoals()  {
        def e = params.id ? params : Employees.findByUser(springSecurityService.getCurrentUser())
        def goalSet = goalService.getGoalSetForEmployee(e?.id,params.myDate_year)
        withFormat {
            '*' { render goalSet as JSON}
        }
    }
}
