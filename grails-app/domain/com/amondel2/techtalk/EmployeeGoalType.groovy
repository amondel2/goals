package com.amondel2.techtalk


import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/employeeGoalType', formats=['json', 'xml'])
class EmployeeGoalType implements Serializable  {

    def utilService = new Utils()
    private static final serialVersionUID = 1L

    static belongsTo = [employeeGoal:EmployeeGoal,type:KPOType]


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


    KPOType type
    EmployeeGoal employeeGoal
    String id
}
