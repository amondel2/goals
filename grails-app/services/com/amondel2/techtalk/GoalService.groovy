package com.amondel2.techtalk

import grails.gorm.transactions.Transactional

@Transactional
class GoalService {


    def getResponseSetForEmployee(empId,year) {
        def restultSet = []
        def empSet = []
        Employees emp = Employees.findById(empId)
        GregorianCalendar gc = new GregorianCalendar(year?.toInteger(),0,1,0,0,0)
        EmployeeGoal.withCriteria {
            eq('employee', emp)
            not{
                'in'('status', [GoalStatus.Cancelled, GoalStatus.Completed])
                gte('actualCompletedDate', gc.getTime())

            }
        }?.each { EmployeeGoal eg ->
            def rs = [:]
            rs['id'] = eg.id
            rs['title'] = eg.title
            rs['description'] = eg.description
            rs['actualCompletedDate'] = eg.actualCompletedDate
            rs['targetCompletDate'] = eg.targetCompletDate
            rs['status'] = eg.status
            rs['goalType'] = eg.types.collect{it.type.title}
            restultSet.add(rs)
        }
        [restultSet,empSet]
    }

    def getActiveGoalTypes(){
        GoalType.findAllByActive(true)
    }
}
