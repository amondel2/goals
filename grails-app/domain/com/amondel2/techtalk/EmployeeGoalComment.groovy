package com.amondel2.techtalk

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/employeeGoalComment', formats=['json', 'xml'])
class EmployeeGoalComment implements Serializable  {

    def utilService = Utils.getInstance()
    private static final serialVersionUID = 1L

    static belongsTo = [employeeGoal:EmployeeGoal]


    static mapping = {
        id generator:'assigned'
        version false
        sort createdDate: "desc"
    }

    static constraints = {
        employeeGoal nullable: false, blank: false
        createdDate nullable: false, blank: false, maxSize: 500
        commentStr nullable: false, blank: false, maxSize: 500
    }

    def beforeValidate() {
        createdDate = new Date()
        if(!id || id.equals(null)) {
            id  = utilService.idGenerator()
        }
    }

    def beforeInsert() {
        createdDate = new Date()
        if(!id || id.equals(null)) {
            id  = utilService.idGenerator()

        }
    }


    Date createdDate
    String commentStr
    EmployeeGoal employeeGoal
    String id
}
