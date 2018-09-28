package com.amondel2.techtalk

import grails.plugin.springsecurity.annotation.Secured
import pl.touk.excel.export.WebXlsxExporter

@Secured(['ROLE_REPORTER','ROLE_ADMIN'])
class ReportsController {

    def reportsService

    def index()
    {
        render(view:"index")
    }

    def generateReport() {
        def months = ['Jan','Feb','Mar','Apr','May','June','July','Aug','Sept','Oct','Nov','Dec']
        def year = params.year?.toInteger()
        def headers = ['Portfolio'] + months
        def withProperties = ['name', new PeopleGetter('percents', '1'),new PeopleGetter('percents', '2'),new PeopleGetter('percents', '3'), new PeopleGetter('percents', '4'),new PeopleGetter('percents', '5'), new PeopleGetter('percents', '6'),new PeopleGetter('percents', '7'), new PeopleGetter('percents', '8'),new PeopleGetter('percents', '9'), new PeopleGetter('percents', '10'),new PeopleGetter('percents', '11'),new PeopleGetter('percents', '12')]

        def products = reportsService.generateReport(year)

        new WebXlsxExporter().with {
            setResponseHeaders(response)
            setHeaders(response,"Portfolio " + params.year.toString() +  ".xlsx")
            fillHeader(headers)
            add(products, withProperties)
            save(response.outputStream)
        }
    }

    def generateEmpReport() {
        def months = ['Jan','Feb','Mar','Apr','May','June','July','Aug','Sept','Oct','Nov','Dec']
        def year = params.year?.toInteger()
        def headers = ['First Name','Last Name','CNUM','HIRE DATE','END DATE','e-mail','Manager','Portfolio','Project','Sub Project','Job','Job Function'] + months
        def withProperties = ['emp.firstName','emp.lastName','emp.employeeId','emp.hireDate','emp.endDate','emp.email','empManager','pfname','prname','subname','jname','jfname'] + months
        def products = reportsService.generateEmpReport(year)

        new WebXlsxExporter().with {
            setResponseHeaders(response)
            setHeaders(response,"Employee " + params.year.toString() + ".xlsx")
            fillHeader(headers)
            add(products, withProperties)
            save(response.outputStream)
        }
    }
}