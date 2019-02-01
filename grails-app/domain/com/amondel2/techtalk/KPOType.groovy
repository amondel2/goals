package com.amondel2.techtalk

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/KPOType', formats=['json', 'xml'])
class KPOType implements Serializable  {

    def utilService = Utils.getInstance()
    private static final serialVersionUID = 1L

    static hasMany = [employeeGoalTypes:EmployeeGoalType]

    static mapping = {
        id generator:'assigned'
        version false
        sort 'title'
    }

    static constraints = {
        title unique: true, nullable: false, blank: false, maxSize: 100
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

    @Override
    String toString(){
        return this.title
    }

    String id
    String title
    String description
    Date endDate
    Boolean isActive = true
}