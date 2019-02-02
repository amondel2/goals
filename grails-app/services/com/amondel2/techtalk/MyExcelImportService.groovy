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


    String exportDate(Date date) {
        if(!date) {
            return ""
        }
        Calendar cal = Calendar.getInstance()
        cal.setTime(date)
      return  (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +  cal.get(Calendar.YEAR)
    }

    def getEmployeeExportWorkBook() {

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
        row.createCell(2).setCellValue("Employee Id")
        row.createCell(3).setCellValue("Hire Date (MM/DD/YYYY)")
        row.createCell(4).setCellValue("Exit Date (MM/DD/YYYY)")
        row.createCell(5).setCellValue("E-MAIL address")
        row.createCell(6).setCellValue("Boss Employee ID")
        row.createCell(7).setCellValue("Is Admin")
        row.createCell(8).setCellValue("UserName")
        sheet.setColumnWidth(0, 35*256)
        sheet.setColumnWidth(1, 35*256)
        sheet.setColumnWidth(2, 22*256)
        sheet.setColumnWidth(3, 22*256)
        sheet.setColumnWidth(4, 22*256)
        sheet.setColumnWidth(5, 22*256)
        sheet.setColumnWidth(6, 20*256)
        sheet.setColumnWidth(7, 15*256)
        sheet.setColumnWidth(8, 35*256)

        DataValidationHelper dvHelper = sheet.getDataValidationHelper()

        DataValidationConstraint dvConstraint1 = dvHelper.createExplicitListConstraint(bol)
        DataValidation validation1 = dvHelper.createValidation(dvConstraint1, new CellRangeAddressList(1, 2000, 7, 7))
        validation1.setShowErrorBox(true)
        validation1.setSuppressDropDownArrow(true)
        sheet.addValidationData(validation1)



        [wb,sheet]
    }
}
