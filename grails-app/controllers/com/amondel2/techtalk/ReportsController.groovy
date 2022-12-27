package com.amondel2.techtalk

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.docx4j.openpackaging.packages.WordprocessingMLPackage

import pl.touk.excel.export.WebXlsxExporter

@Secured(['ROLE_REPORTER','ROLE_ADMIN'])
class ReportsController {

    def reportsService
    SpringSecurityService springSecurityService

    def index()
    {
        render(view:"index")
    }



    def generateReport() {
        Employees e = params.mid ? Employees.findById(params.mid) : (params.id ? Employees.findById(params.id) : Employees.findByUser(springSecurityService.getCurrentUser()))
        def year = params.year?.toInteger()
        WordprocessingMLPackage document = reportsService.generateKPOUserReport(e,year)
        response.setContentType("APPLICATION/OCTET-STREAM")
        response.setHeader("Content-Disposition", "Attachment;Filename=${year}_${e.firstName}_KPOReport.docx")
        def outputStream = response.getOutputStream()
        document.save(outputStream)
        outputStream.flush()
        outputStream.close()
    }

    def generateEmpReport() {
//        def months = ['Jan','Feb','Mar','Apr','May','June','July','Aug','Sept','Oct','Nov','Dec']
        def year = params.year?.toInteger()
        def headers = ['First Name','Last Name','CNUM','HIRE DATE','END DATE','e-mail','Manager'] // + months
        def withProperties = ['emp.firstName','emp.lastName','emp.employeeId','emp.hireDate','emp.endDate','emp.email','empManager'] //+ months
        def products = reportsService.generateEmpReport(year)

        new WebXlsxExporter().with {
            setHeaders(response,"Employee " + params.year.toString() + ".xlsx")
            fillHeader(headers)
            add(products, withProperties)
            save(response.outputStream)
        }
    }
}