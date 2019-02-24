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
        activeStartDate nullable: true, blank: false
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
        /*Make sure the dates are correct */
        Calendar cal = Calendar.getInstance()
        if(activeStartDate) {
            cal.setTime(activeStartDate)
            cal.set(cal.get(Calendar.YEAR),0,1,0,0,0)
            activeStartDate = cal.getTime()
        }
        cal.setTime(endDate)
        cal.set(cal.get(Calendar.YEAR),11,31,23,59,59)
        endDate = cal.getTime()
    }

    def beforeUpdate() {
        Calendar cal = Calendar.getInstance()
        if(activeStartDate) {
            cal.setTime(activeStartDate)
            cal.set(cal.get(Calendar.YEAR),0,1,0,0,0)
            activeStartDate = cal.getTime()
        }
        cal.setTime(endDate)
        cal.set(cal.get(Calendar.YEAR),11,31,23,59,59)
        endDate = cal.getTime()
    }

    @Override
    String toString(){
        return this.title
    }

    String id
    String title
    String description
    Date endDate
    Date activeStartDate
    Boolean isActive = true
}