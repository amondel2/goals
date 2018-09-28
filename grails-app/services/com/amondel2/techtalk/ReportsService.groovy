package com.amondel2.techtalk

import grails.gorm.transactions.Transactional
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFDataFormat
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@Transactional
class ReportsService {

    def generateReport(year) {
        generateReport(year,null)
    }
    def generateReport(year,emp) {
        def rs = []

        rs
    }

    def getEmployeeExportWorkBook(title,headers) {

        XSSFWorkbook wb = new XSSFWorkbook()
        XSSFSheet sheet = wb.createSheet(title)
        XSSFDataFormat fmt = wb.createDataFormat();
        CellStyle textStyle = wb.createCellStyle();
        textStyle.setDataFormat(fmt.getFormat("@"))
        sheet.setDefaultColumnStyle(0, textStyle)
        Row row = sheet.createRow(0)
        headers.eachWithIndex{name, idx ->
            row.createCell(idx).setCellValue(name)
    }
        [wb,sheet]
    }

    def generateEmpReport(year) {
        generateEmpReport(year,null)
    }

     def generateEmpReport(year,emp) {
         def rs = []
         def months = ['Jan','Feb','Mar','Apr','May','June','July','Aug','Sept','Oct','Nov','Dec']
         def sdate = new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
         def edate = new GregorianCalendar(year?.toInteger(),11,1,0,0,0).getTime()
         Calendar cal = Calendar.getInstance()
         if(!emp) {
             emp =  Employees.withCriteria {
                 le('hireDate',edate)
                 or{
                     isNull('endDate')
                     le('endDate',edate)
                 }
             }
         }
         emp.each{ Employees e ->
             def baseVal  = ['emp':e, 'empManager': e.bosses[0]?.boss?.toString()]

         }
        rs
    }
}
