package com.amondel2.techtalk

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/employeeGoalType', formats=['json', 'xml'])
class EmployeeGoalType implements Serializable  {

    def utilService = new Utils()
    private static final serialVersionUID = 1L

    static belongsTo = [employeeGoal:EmployeeGoal,type:GoalType]


    static mapping = {
        id generator:'assigned'
        version false
    }

    static constraints = {
        type unique: ['employeeGoal'], nullable: false, blank: false
        employeeGoal nullable: false, blank: false
    }

    def beforeValidate() {
        if(!id || id.equals(null)) {
            id  = utilService.idGenerator()
        }
    }

    def beforeInsert() {
        if(!id || id.equals(null)) {
            id  = utilService.idGenerator()

        }
    }


    GoalType type
    EmployeeGoal employeeGoal
    String id
}
