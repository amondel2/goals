package com.amondel2.techtalk
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class EmployeeGoalTypeController {

    static responseFormats = ['html', 'json', 'xml']
    static scaffold = EmployeeGoalType
}
