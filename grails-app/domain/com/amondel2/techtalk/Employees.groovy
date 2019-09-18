package com.amondel2.techtalk

import groovy.transform.EqualsAndHashCode
import grails.rest.*

@EqualsAndHashCode(includes=['id'])
class Employees implements Serializable  {

    def utilService = Utils.getInstance()
    private static final serialVersionUID = 1L

    static constraints = {
        firstName nullable:false,blank:false
        employeeId nullable:false,blank:false,unique:['company']
        lastName nullable:false,blank:false
        email email: true, nullable: true
        id display:false
        hireDate nullable:true
        endDate nullable:true
        user nullable: true
        restToken display: false, nullable: true

    }

    static mapping = {
        id generator:'assigned'
        version false
        bosses cascade: "none"
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
    public String toString(){
        return this.firstName + ' ' + this.lastName
    }

    static belongsTo = [company:Company]
    static hasMany = [bosses:EmployeeBoss,employees:EmployeeBoss,goals:EmployeeGoal]
    static mappedBy = [bosses:'employee',employees:'boss']

    User user
    Company company
    String employeeId
    String firstName
    String lastName
    Date hireDate
    Date endDate
    String email
    Boolean showHidden = true
    String id
    String restToken = ''
    Boolean manager = false
}