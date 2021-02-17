package com.amondel2.techtalk

import grails.gorm.transactions.Transactional

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFDataFormat
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.springframework.web.util.HtmlUtils

import java.text.SimpleDateFormat


@Transactional
class ReportsService {
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy")
    def generateReport(year) {
        generateReport(year,null)
    }
    def generateReport(year,emp) {
        def rs = []

        rs
    }

    String charEncode(String str) {
        //The only way I could think how to do this and not mess with the HTML Tags but also recode > and < to actual entities.
        HtmlUtils.htmlUnescape(str.replaceAll('&gt;',"thegreatesttageintheworldgt12340099876543").replaceAll('&lt;',"thegreatesttageintheworldlt12340099876543")).replaceAll('&','&amp;').replaceAll("thegreatesttageintheworldgt12340099876543",'&gt;').replaceAll("thegreatesttageintheworldlt12340099876543","&lt;")
    }

    WordprocessingMLPackage generateKPOUserReport(Employees e,year) {
        def sdate = Calendar.getInstance()
        sdate.set(year,0,1,0,0,0)
        sdate = sdate.getTime()
        def edate = Calendar.getInstance()
        edate.set(year,11,31,23,59,59)
        edate = edate.getTime()
        def goalByKpo = [:]

        EmployeeGoal.withCriteria {
           eq('employee',e)
            or{
                between('targetCompletDate',sdate,edate)
                between('orginTargetDate',sdate,edate)
            }
            'in'('status',[GoalStatus.Completed,GoalStatus.NotStarted,GoalStatus.OnTrack,GoalStatus.Behind, GoalStatus.Ongoing])

        }?.each{ empg1 ->
            EmployeeGoalType.withCriteria {
                eq('employeeGoal',empg1)
            }?.each{EmployeeGoalType egt ->
                if(goalByKpo[egt?.type?.title].equals(null)) {

                    goalByKpo[egt?.type?.title] = [empg1]
                } else {
                    goalByKpo[egt?.type?.title] << empg1
                }
            }
        }
        CreateWordBulletOrDecimalList cl = new CreateWordBulletOrDecimalList()
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

        mdp.addStyledParagraphOfText("Title", "$year KPO Report for ${e.toString()}");
        goalByKpo.each { k, v ->
            mdp.getContent().add(cl.addPageBreak())
            mdp.addStyledParagraphOfText("Heading1",k)
            v?.each { EmployeeGoal empg ->
                XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage)
                mdp.addStyledParagraphOfText("Heading2",charEncode(empg.title))
                wordMLPackage.getMainDocumentPart().getContent().addAll(
                        XHTMLImporter.convert('<div>' + charEncode(empg.description) + '</div>', null) );
                def myList = []
                if(empg.status == GoalStatus.Completed) {
                    myList << "Completed on ${sdf.format(empg.actualCompletedDate)} original target was ${sdf.format(empg.orginTargetDate)}"
                } else {
                    myList << "<span style='color:red;'>Not Completed Targeted original target is ${sdf.format(empg.orginTargetDate)} new target is ${sdf.format(empg.targetCompletDate)}</span>"
                }
                myList << "Comments"

                def commentList = []
                EmployeeGoalComment.withCriteria {
                    eq('employeeGoal',empg)
                }?.each {EmployeeGoalComment egc ->
                    commentList << egc.commentStr
                }
                myList << commentList
                XHTMLImporterImpl bulletImp = new XHTMLImporterImpl(wordMLPackage)
                wordMLPackage.getMainDocumentPart().getContent().addAll(
                        bulletImp.convert( cl.createBulletsFromList(myList), null) );

            }
        }
        wordMLPackage
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