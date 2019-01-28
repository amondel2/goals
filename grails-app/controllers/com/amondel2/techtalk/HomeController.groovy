package com.amondel2.techtalk

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import pl.touk.excel.export.WebXlsxExporter

@Secured(['permitAll'])
class HomeController {

	SpringSecurityService springSecurityService
	def employeeService
    def reportsService
        def index() {
			def hasDirect;
			try {
				Employees me = Employees.findByUser(springSecurityService.getCurrentUser())
				hasDirect = employeeService.doesEmpHaveDirects(me)
			} catch(Exception e) {
				hasDirect = false;
			}
		render(view:"index",model:[param:params,messages:[],has_directs:hasDirect])
	}

	def generateEmpReport() {
		def months = ['Jan','Feb','Mar','Apr','May','June','July','Aug','Sept','Oct','Nov','Dec']
        def currentTime = new GregorianCalendar().getInstance()
		def year = currentTime.get(Calendar.YEAR)
		def headers = ['First Name','Last Name','CNUM','HIRE DATE','END DATE','e-mail','Manager','Portfolio','Project','Sub Project','Job','Job Function'] + months
		def withProperties = ['emp.firstName','emp.lastName','emp.employeeId','emp.hireDate','emp.endDate','emp.email','empManager','pfname','prname','subname','jname','jfname'] + months
        Employees me = Employees.findByUser(springSecurityService.getCurrentUser())
		def emp = []
		if(me) {
			employeeService.getAllEmployeesChildernFlat(me?.id,emp,year)
		} else {
            employeeService.getAllEmployeesFlat(emp,year)
		}



		def products = reportsService.generateEmpReport(year,emp)

		new WebXlsxExporter().with {
			setResponseHeaders(response)
			setHeaders(response,"Employee " + year + ".xlsx")
			fillHeader(headers)
			add(products, withProperties)
			save(response.outputStream)
		}
	}

    def generateReport() {
        def months = ['Jan','Feb','Mar','Apr','May','June','July','Aug','Sept','Oct','Nov','Dec']
        def currentTime = new GregorianCalendar().getInstance()
        def year = currentTime.get(Calendar.YEAR)
        def headers = ['Portfolio'] + months
        def withProperties = ['name', new PeopleGetter('percents', '1'),new PeopleGetter('percents', '2'),new PeopleGetter('percents', '3'), new PeopleGetter('percents', '4'),new PeopleGetter('percents', '5'), new PeopleGetter('percents', '6'),new PeopleGetter('percents', '7'), new PeopleGetter('percents', '8'),new PeopleGetter('percents', '9'), new PeopleGetter('percents', '10'),new PeopleGetter('percents', '11'),new PeopleGetter('percents', '12')]


        Employees me = Employees.findByUser(springSecurityService.getCurrentUser())
        def emp = []
        if(me) {
            employeeService.getAllEmployeesChildernFlat(me?.id,emp,year)

        } else {
            employeeService.getAllEmployeesFlat(emp,year)
        }

        def products = reportsService.generateReport(year,emp)
        new WebXlsxExporter().with {
            setResponseHeaders(response)
            setHeaders(response,"Portfolio " + year +  ".xlsx")
            fillHeader(headers)
            add(products, withProperties)
            save(response.outputStream)
        }
    }

	def getPeopleUnder() {
		Employees me = Employees.findByUser(springSecurityService.getCurrentUser())
		def currentTime = new GregorianCalendar().getInstance()
		def gtTime
		if(params.fut && params.fut?.toInteger() > 0) {
			gtTime = new GregorianCalendar().getInstance()
			currentTime.add(Calendar.DAY_OF_YEAR,params.fut?.toInteger())
		}
		def rtn = [:]
		if(me) {
			employeeService.peopleUnder(rtn,me,currentTime,gtTime)
		} else {
			rtn.error = "You Some Sorta Super user man."
		}

		withFormat {
			'*' {
				render([msg: rtn] as JSON)
			}
		}
	}


	def getMyGoals() {
		Employees me = Employees.findByUser(springSecurityService.getCurrentUser())
		def rtn = [:]
		if(me) {
			def currentTime = new GregorianCalendar().getInstance()
			def gtTime
			if (params.fut && params.fut?.toInteger() > 0) {
				gtTime = new GregorianCalendar().getInstance()
				currentTime.add(Calendar.DAY_OF_YEAR, params.fut?.toInteger())
			}
			employeeService.getSingleEmployee(rtn, me, currentTime, gtTime)
		} else {
			rtn.error = "Super User"
		}

		withFormat {
			'*' {
				render([msg: rtn] as JSON)
			}
		}
	}
       
}