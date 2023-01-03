package com.amondel2.techtalk

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

abstract class MondelMapperUIController {

    BaseService baseFMService
    EmployeeService employeeService


    abstract String getCompanyId()

    abstract boolean hasReadPermission()

    abstract boolean hasWritePermission()
	
	abstract boolean hasImportExportPermission()

    abstract SpringSecurityService getSSservice()



    def getInitialData() {
        try {
            def data = employeeService.getInitialData(params.year,params.bossId)
            render ([childData: data] as JSON)
        } catch (Exception e) {
            log.error('getInitialData: ' + e)
            render(status: 500)
        }
    }

    def loadEmployeeChildren() {
        SpringSecurityService ss =  getSSservice()
        try {
            def user =  ss.getCurrentUser()
            def companyId = getCompanyId()
            def data = employeeService.loadEmployeeServiceChildren(params.id,params.year,user)
            render data as JSON
        } catch (Exception e) {
            log.error('loadPortfolioChildren: ' + e)
            render(status: 500)
        }
    }

    def updateParentOfEmployee() {
        withFormat {
            '*' {
                render(employeeService.updateParentOfEmployee(Company.first(), params.id, params.parentId) as JSON)
            }
        }

    }

    def createEmployee() {
        try {
            if (!hasWritePermission()) {
                throw new Exception('Permission denied')
            }

            def errors = [:]
            def saveData = []

            def cmd = new EmployeeMCommand()
            cmd.companyId = getCompanyId()

            bindData(cmd, params)
            cmd.validate()
            if (!cmd.hasErrors()) {
                saveData = employeeService.saveEmployee(cmd)
            } else {
                errors = baseFMService.getErrorList(cmd)
            }

            render ([
                    errors: errors,
                    saveData: saveData
            ] as JSON)
        } catch (Exception e) {
            log.error('createEmployee: ' + e)
            render(status: 500)
        }
    }




}