package com.amondel2.techtalk

import org.grails.plugins.excelimport.AbstractExcelImporter
import org.grails.plugins.excelimport.DefaultImportCellCollector

import static org.grails.plugins.excelimport.ExpectedPropertyType.DateType
import static org.grails.plugins.excelimport.ExpectedPropertyType.StringType


class EmployeeExcelImporter extends AbstractExcelImporter {
    static cellReporter = new DefaultImportCellCollector()

   def myExcelImportService

    static Map configuratiomMap = [
//            dateIssued: ([expectedType: DateType, defaultValue: null]),
            firstName: ([expectedType: StringType, defaultValue: null]),
            employeeID: ([expectedType: StringType, defaultValue: null]),
            employeeLocation : ([expectedType: StringType, defaultValue: null]),
            lastName : ([expectedType: StringType, defaultValue: null]),
            hireDate : ([expectedType: DateType, defaultValue: null]),
            endDate: ([expectedType: DateType, defaultValue: null]),
            employeeGeo : ([expectedType: StringType, defaultValue: null]),
            email : ([expectedType: StringType, defaultValue: null]),
            bossEmployeeID: ([expectedType: StringType, defaultValue: null]),
            hasAdmin:  ([expectedType: StringType, defaultValue: null]),
            username:  ([expectedType: StringType, defaultValue: null])
    ]


    static Map CONFIG_BOOK_COLUMN_MAP = [
            sheet: 'Employee Import',
            startRow: 1,
            columnMap:  [
                    'A' : 'firstName',
                    'B':'lastName',
                    'C':'employeeID',
                    'D':'employeeLocation',
                    'E':'employeeGeo',
                    'F': 'hireDate',
                    'G': 'endDate',
                    'H': 'email',
                    'I' : 'bossEmployeeID',
                    'J' : 'hasAdmin',
                    'K' : 'username'
            ]
    ]

    public EmployeeExcelImporter(fileName) {
        super(fileName)
    }

    public EmployeeExcelImporter() {
        super()
    }

    def getEmployees() {
        this.myExcelImportService.columns(workbook, CONFIG_BOOK_COLUMN_MAP,cellReporter,configuratiomMap )
    }

}
