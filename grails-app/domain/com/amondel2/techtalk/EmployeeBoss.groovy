package com.amondel2.techtalk

import org.apache.commons.lang.builder.HashCodeBuilder
import grails.rest.*

@Resource(uri='/EmployeeBoss', formats=['json', 'xml'])
class EmployeeBoss implements Serializable {

    private static final long serialVersionUID = 1
    def utilService = Utils.getInstance()

    String id
    Employees employee
    Employees boss
    Boolean defaultBoss = false

    EmployeeBoss(Employees u, Employees r) {
        this()
        employee = u
        boss = r
    }

    static boolean exists(String employeeId, String bossId) {
        EmployeeBoss.where {
            employee == Employees.load(employeeId) &&
            boss == Employees.load(bossId)
        }.count()
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof EmployeeBoss)) {
            return false
        }

        other.employee?.id == employee?.id && other.boss?.id == boss?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (employee) builder.append(employee.id)
        if (boss) builder.append(boss.id)
        builder.toHashCode()
    }

    static constraints = {
        defaultBoss display:true
        id display:true
        defaultBoss unique: ['boss','employee']
        employee unique: 'boss'
        boss unique: 'employee'
    }

    def removeOtherDefaults() {
        if(this.defaultBoss) {
            def e = this.employee
            def b = this.boss
            def query = EmployeeBoss.where {
                defaultBoss == true
                employee == e
                boss != b
            }
            query.updateAll(defaultBoss:false)
        }
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

    def afterInsert() {
        removeOtherDefaults()
    }

    def afterUpdate() {
        removeOtherDefaults()
    }

    public String toString(){
        if(this.defaultBoss) {
            return this.boss?.toString() + " is the offical boss of  " + this.employee?.toString()
        }
        return this.boss?.toString() + " is the lined boss of  " + this.employee?.toString()
    }

    static mapping = {
        version false
        id generator:'assigned'
    }
}