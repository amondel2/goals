package com.amondel2.techtalk

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddressList
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.streaming.SXSSFSheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.grails.plugins.excelimport.ImportCellCollector

import java.time.LocalDate
import java.time.ZoneId

class MyExcelImportService extends org.grails.plugins.excelimport.ExcelImportService {

    def test() {
        System.out.println("HERE I AM")
    }


    def decodeEmpLocation(String location, String geo) {
        try {
            def em = EmpLocation.findByLocationAndGeo(location.trim(),geo.trim())
            if(!em) {
                em = new EmpLocation()
                em.location = location.trim()
                em.geo = geo.trim()
                em.company = Company.first()
                em.save(flush:true,failOnError: true)
            }
            em
        } catch (Exception e) {
            null
        }
    }

    def decodeHireDate(String date) {
        try {
            def dateArr = date.split("/")
            new GregorianCalendar(dateArr[0].toInteger(), dateArr[1].toInteger(), dateArr[2].toInteger()).getTime()
        } catch (Exception e) {
            null
        }
    }

    def decodeHireDate(LocalDate localDate) {
        try {
            Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        } catch (Exception e) {
            null
        }
    }

    def decodeHireDate(org.joda.time.LocalDate localDate) {
        try {
            localDate.toDateTimeAtStartOfDay().toDate()
        } catch (Exception e) {
            null
        }
    }

    def decodeHireDate(Date localDate) {
        localDate
    }


    reactor.bus.Bus sendAndReceive(java.lang.Object obj, groovy.lang.Closure cl) {
        org.grails.plugins.excelimport.ExcelImportService.sendAndReceive(obj, cl)
    }

    @Override
    Serializable getCellValueByColName(Row row, String columnName, ImportCellCollector pcc = null, FormulaEvaluator evaluator = null, Map propertyConfiguration = [:]) {
        int colIndex = CellReference.convertColStringToIndex(columnName)
        log.debug "getCellValueByColName $columnName row ${row.rowNum}, propConfig $propertyConfiguration, colIndex = $colIndex"

        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)

        getCellValue(cell, pcc, evaluator, propertyConfiguration)
    }

    def encodeEmpLocation(String loc) {
        loc.replaceAll(',','-').reverse().replaceFirst(' ', '').reverse()
    }

    String exportDate(Date date) {
        if(!date) {
            return ""
        }
        Calendar cal = Calendar.getInstance()
        cal.setTime(date)
      return  (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +  cal.get(Calendar.YEAR)
    }

    def getEmployeeExportWorkBook() {

        def empl1 = []
        def empG1 = []
        EmpLocation.list(sort: "location").each{
           if(! empl1.contains(it.location.trim())  )
                   empl1 <<  it.location.trim()
            if(! empG1.contains(it.geo.trim()) )
                empG1 <<  it.geo.trim()
        }
        String[] empLocations = empl1.toArray()
        String[] empGeo= empG1.toArray()



        String[] bol = ['yes','no']
        SXSSFWorkbook wb = new SXSSFWorkbook(100)
        SXSSFSheet sheet = wb.createSheet("Employee Import")
        DataFormat fmt = wb.createDataFormat();
        CellStyle textStyle = wb.createCellStyle();
        textStyle.setDataFormat(fmt.getFormat("@"));
        sheet.setDefaultColumnStyle(0, textStyle);
        Row row = sheet.createRow(0)
        row.createCell(0).setCellValue("First Name")
        row.createCell(1).setCellValue("Last Name")
        row.createCell(2).setCellValue("Employee ID")
        row.createCell(3).setCellValue("Employee Location")
        row.createCell(4).setCellValue("Employee Geo")
        row.createCell(5).setCellValue("Hire Date (MM/DD/YYYY)")
        row.createCell(6).setCellValue("Exit Date (MM/DD/YYYY)")

        row.createCell(7).setCellValue("E-MAIL address")
        row.createCell(8).setCellValue("Boss Employee ID")
        row.createCell(9).setCellValue("Is Admin")
        row.createCell(10).setCellValue("UserName")
        sheet.setColumnWidth(0, 35*256)
        sheet.setColumnWidth(1, 35*256)
        sheet.setColumnWidth(2, 15*256)
        sheet.setColumnWidth(3, 20*256)
        sheet.setColumnWidth(4, 22*256)
        sheet.setColumnWidth(5, 22*256)
        sheet.setColumnWidth(6, 22*256)
        sheet.setColumnWidth(7, 20*256)
        sheet.setColumnWidth(8, 15*256)

        DataValidationHelper dvHelper = sheet.getDataValidationHelper()


        DataValidationConstraint  dvConstraint3 = dvHelper.createExplicitListConstraint(empGeo)
        DataValidation  validation3 = dvHelper.createValidation(dvConstraint3, new CellRangeAddressList(1, 2000, 4, 4))
        validation3.setShowErrorBox(true)
        validation3.setSuppressDropDownArrow(true)
        sheet.addValidationData(validation3)


        DataValidationConstraint dvConstraint1 = dvHelper.createExplicitListConstraint(bol)
        DataValidation validation1 = dvHelper.createValidation(dvConstraint1, new CellRangeAddressList(1, 2000, 9, 9))
        validation1.setShowErrorBox(true)
        validation1.setSuppressDropDownArrow(true)
        sheet.addValidationData(validation1)






        DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(empLocations)
        DataValidation validation = dvHelper.createValidation(dvConstraint, new CellRangeAddressList(1, 2000, 3, 3))
        validation.setShowErrorBox(true)
        validation.setSuppressDropDownArrow(true)
        sheet.addValidationData(validation)


        [wb,sheet]
    }
}
