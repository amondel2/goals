package com.amondel2.techtalk

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/employeeGoalComment', superClass=SubclassRestfulController, formats=['json', 'xml'])
class EmployeeGoalComment implements Serializable  {

    def utilService = Utils.getInstance()
    private static final serialVersionUID = 1L

    static belongsTo = [employeeGoal:EmployeeGoal,modifiedUser:User]


    static mapping = {
        id generator:'assigned'
        version false
        sort createdDate: "desc"
    }

    static constraints = {
        employeeGoal nullable: false, blank: false
        createdDate nullable: false, blank: false, maxSize: 500
        commentStr nullable: false, blank: false, maxSize: 500, minSize: 5
    }

    def beforeValidate() {
        createdDate = new Date()
        modifiedDate = new Date()
        if(!id || id.equals(null)) {
            id  = utilService.idGenerator()
        }
    }

    def beforeInsert() {
        createdDate = new Date()
        modifiedDate = new Date()
        if(!id || id.equals(null)) {
            id  = utilService.idGenerator()

        }
    }

    def beforeUpdate() {
        modifiedDate = new Date()
    }


    Date modifiedDate
    Date createdDate
    String commentStr
    User modifiedUser
    EmployeeGoal employeeGoal
    String id
}
