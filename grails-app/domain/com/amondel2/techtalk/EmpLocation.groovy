package com.amondel2.techtalk
import groovy.transform.EqualsAndHashCode
import grails.rest.*


@EqualsAndHashCode(includes=['id'])
@Resource(uri='/empLocation', formats=['json', 'xml'])
class EmpLocation implements Serializable  {

    def utilService = new Utils()
    private static final serialVersionUID = 1L
    static constraints = {
        geo unique: ['company','location'], nullable: false, blank: false
        location unique: ['company','geo'], nullable: false, blank: false
    }

    static mapping = {
        id generator:'assigned'
        version false
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
        return this.location + "," + this.geo
    }

    static belongsTo = [company:Company]
    static hasMany = [employees:Employees]

    String geo
    String location
    String id
    Company company

}
