package com.amondel2.techtalk

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.docx4j.openpackaging.packages.WordprocessingMLPackage

@Secured(['ROLE_USER'])
class GoalsController {

    SpringSecurityService springSecurityService
    def employeeService
    def goalService
    def reportsService
    def kpoService

    def index() {
        Employees e = params.mid ? Employees.findById(params.mid) : (params.id ? Employees.findById(params.id) : Employees.findByUser(springSecurityService.getCurrentUser()))
        if (!e) {
            flash.error = "No Employee Found"
            redirect(controller: "projectManager")
            return;
        }
        int year
        try {
            year = params?.myDate_year.toInteger()
        } catch (Exception v) {
            try {
                year = params?.year.toInteger()
            } catch (Exception x) {
                try {
                    year = Calendar.getInstance().get(Calendar.YEAR)
                } catch (Exception z) {
                    throw z
                }
            }
        }
        if (employeeService.isUserHaveAccessChildren(e,springSecurityService.getCurrentUser(),year) == false ) {
            flash.error = "No Employee Found"
            redirect(controller: "projectManager")
            return;
        }
        def date = new GregorianCalendar(year, 0, 1,0,0,0)
        def endDate = new GregorianCalendar(year, 11, 31, 23, 59, 59)

        render(view: "index", model: [uid:springSecurityService.getCurrentUserId(), emp: e, date: date, companyName: Company.first().name, goalTypes:kpoService.getKPOList(date,endDate) , goalSet : goalService.getGoalSetForEmployee(e?.id, date.get(Calendar.YEAR))])
    }

    def generateKPOReport() {
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

    def setHidden() {
        goalService.saveHiddenStatus(params)
        withFormat {
            '*' { render ( [msg:"success"]  as JSON) }
        }
    }

    def chagneGoals()  {
        def e = params.id ? params : Employees.findByUser(springSecurityService.getCurrentUser())
        def goalSet = goalService.getGoalSetForEmployee(e?.id,params.myDate_year)
        withFormat {
            '*' { render goalSet as JSON}
        }
    }

    def saveComments() {
        def worked = goalService.saveGoalsComments(params)
        withFormat {
            '*' { render worked as JSON}
        }
    }

    def createCard() {
        def p = [:]
        p.id = Utils.getInstance().idGenerator()
        withFormat {
            '*' { render p as JSON}
        }
    }

    def saveGoals() {
        def p = [:]
        p =  goalService.saveGoals(params)
        withFormat {
            '*' { render p as JSON}
        }
    }
}