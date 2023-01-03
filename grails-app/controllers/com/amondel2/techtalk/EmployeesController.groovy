package com.amondel2.techtalk

import grails.converters.JSON
import grails.core.GrailsApplication
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.streaming.SXSSFSheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@Secured(["ROLE_USER", "ROLE_ADMIN"])
class EmployeesController {

    MyExcelImportService myExcelImportService
    SpringSecurityService springSecurityService
    BaseService baseService
    static responseFormats = ['html', 'json', 'xml']
    static scaffold = Employees
    EmployeeService employeeService
    GrailsApplication grailsApplication


    @Secured(["permitAll"])
    def index(Integer max) {
        if (SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN")) {
            params.max = Math.min(max ?: 10, 100)
            respond Employees.list(params), model: [employeesCount: Employees.count()]

        } else {
            redirect(action: "show")
        }
    }

    @Secured(['ROLE_ADMIN'])
    def generateResetToken() {
        String guid = UUID.randomUUID()?.toString().replaceAll("-", "")
        employeeService.saveResetToken(params,guid)
        def p = ['emp': guid]
        withFormat {
            '*' {
                render p as JSON
            }
        }
    }

    @Secured(["permitAll"])
    def show(Employees employeesInstance) {

        if (!employeesInstance) {
            employeesInstance = Employees.findByUser(springSecurityService.currentUser)
        }

        if (!employeesInstance) {
            redirect(action: "create")
        } else {


            respond employeesInstance, model: [user: springSecurityService.currentUser]
        }
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        respond new Employees(params), model: [user: springSecurityService.currentUser, hl: User.list()]
    }


    @Secured(['ROLE_ADMIN'])
    def createFromUser() {
        Employees p = new Employees()
        def rtn = []
        try {
            p.user = User.load(params.uid)
            p.firstName = p.user.username
            p.lastName = "ChangeME"
            p.email = p.firstName.charAt(0) + p.lastName[0..6] + "@reedtech.com"
            p.hireDate = new Date()
            p.employeeId p.id
            if (!p.validate()) {
                throw new Exception(p.errors.join(" "))
            }
            employeeService.saveEmployee(p)
            rtn = [true]
        } catch (Exception e) {
            rtn = [false, e.message]
        }
        withFormat {
            '*' { render rtn as JSON }
        }
    }


    @Secured(["permitAll"])
    def save(Employees employeesInstance) {
        if (employeesInstance == null) {
            notFound()
            return
        }

        if (employeesInstance.hasErrors()) {
            respond employeesInstance.errors, view: 'create', model: [user: springSecurityService.currentUser]
            return
        }
        employeeService.saveEmployee(employeesInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'Employees.label', default: 'Profile'), employeesInstance.id])
                redirect employeesInstance
            }
            '*' { respond employeesInstance, [status: CREATED] }
        }
    }


    @Secured(["permitAll"])
    def edit(Employees employeesInstance) {
        if (SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN")) {
            if (!employeesInstance) {
                employeesInstance = Employees.findByUser(springSecurityService.currentUser)
            }
        } else {
            //prevent other users from modifing profiles they don't have access to
            employeesInstance = Employees.findByUser(springSecurityService.currentUser)
        }
        respond employeesInstance, model: [user: springSecurityService.currentUser, hl: User.list()]
    }


    @Secured(["permitAll"])
    def update(Employees employeesInstance) {
        if (employeesInstance == null) {
            notFound()
            return
        }
        if (!SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN")) {
            //make sure this is my profile
            def tmpprofileInstance = Employees.findByUser(springSecurityService.currentUser)
            if (tmpprofileInstance.id != employeesInstance.id) {
                redirect action: "edit"
            }
        }

        if (employeesInstance.hasErrors()) {
            respond employeesInstance.errors, view: 'edit', model: [user: springSecurityService.currentUser, hl: User.list()]
            return
        }
        employeeService.saveEmployee(employeesInstance)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Profile.label', default: 'Profile'), employeesInstance.id])
                redirect employeesInstance
            }
            '*' { respond employeesInstance, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def exportUsers() {
        def arr = myExcelImportService.getEmployeeExportWorkBook()
        SXSSFWorkbook wb
        SXSSFSheet sheet
        wb = arr[0]
        sheet = arr[1]
        Role admin_role = Role.findByAuthority('ROLE_ADMIN')
        Employees.list().eachWithIndex { Employees emp, index ->
            Row row1 = sheet.createRow(index + 1)
            EmployeeBoss boss = EmployeeBoss.findByEmployeeAndDefaultBoss(emp, true)
            if (!boss) {
                boss = EmployeeBoss.findByEmployee(emp)
            }
            row1.createCell(0).setCellValue(emp.firstName)
            row1.createCell(1).setCellValue(emp.lastName)
            row1.createCell(2).setCellValue(emp.employeeId)
            row1.createCell(3).setCellValue(myExcelImportService.exportDate(emp.hireDate))
            row1.createCell(4).setCellValue(myExcelImportService.exportDate(emp.endDate))
            row1.createCell(5).setCellValue(emp.email)
            if (boss)
                row1.createCell(6).setCellValue(boss.boss.employeeId)
            if (emp.user && UserRole.findByUserAndRole(emp.user, admin_role)) {
                row1.createCell(7).setCellValue('yes')
            } else if (emp.user) {
                row1.createCell(7).setCellValue('no')
            }
            row1.createCell(8).setCellValue(emp.user.username)
        }

        response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        response.setHeader("Content-disposition", "attachment; filename=employees.xlsx")
        wb.write(response.outputStream)
        response.outputStream.flush()
        response.outputStream.close()
        wb.close()

    }

    @Secured(['ROLE_ADMIN'])
    def importTemplate() {

        def arr = myExcelImportService.getEmployeeExportWorkBook()
        SXSSFWorkbook wb
        SXSSFSheet sheet
        wb = arr[0]
        sheet = arr[1]

        Row row1 = sheet.createRow(1)
        row1.createCell(0).setCellValue("Aaron")
        row1.createCell(1).setCellValue("Mondelblatt")
        row1.createCell(2).setCellValue("2G5939897")
        row1.createCell(3).setCellValue("08/22/2011")
        row1.createCell(4).setCellValue("")
        row1.createCell(5).setCellValue("amondel2@gmail.com")
        row1.createCell(6).setCellValue("1G123456")
        row1.createCell(7).setCellValue("no")
        row1.createCell(8).setCellValue("amondelblatt")
        Row row2 = sheet.createRow(2)
        row2.createCell(0).setCellValue("Charlie")
        row2.createCell(1).setCellValue("Barba")
        row2.createCell(2).setCellValue("1G123456")
        row2.createCell(3).setCellValue("08/22/2011")
        row2.createCell(4).setCellValue("")
        row2.createCell(5).setCellValue("cbarba@gmail.com")
        row2.createCell(6).setCellValue("")
        row2.createCell(7).setCellValue("yes")
        row2.createCell(8).setCellValue("cbaraba")

        response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        response.setHeader("Content-disposition", "attachment; filename=EmployeeImport.xlsx")
        wb.write(response.outputStream)
        response.outputStream.flush()
        response.outputStream.close()
        wb.close()

    }

    @Secured(['ROLE_ADMIN'])
    def importEmployee() {

        render view: 'uploadme'
    }

    @Secured(['ROLE_ADMIN'])
    def employeeFileUpload() {
        MultipartFile file = params.employeeFile
        def file2
        byte[] bytes = file.bytes
        File convFile = new File(grailsApplication.config.upload.directory?.toString()?.trim() + file.getOriginalFilename())
        convFile.createNewFile()
        FileOutputStream fos = new FileOutputStream(convFile)
        fos.write(file.getBytes())
        fos.close()

        EmployeeExcelImporter em = new EmployeeExcelImporter()
        em.myExcelImportService = myExcelImportService
        FileInputStream fis = new FileInputStream(convFile);

        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        em.workbook = myWorkBook
//        em.evaluator = em.workbook.creationHelper.createFormulaEvaluator()
        def booklist = em.getEmployees()
        def managerList = [:]
        Company c = Company.first()
        Role my_role = Role.findByAuthority('ROLE_USER')
        Role admin_role = Role.findByAuthority('ROLE_ADMIN')
        booklist.each { employee ->
            Employees emp = Employees.findOrCreateByEmployeeId(employee.employeeID)
            emp.firstName = employee.firstName
            emp.lastName = employee.lastName
            emp.email = employee.email
            emp.company = c
            emp.hireDate = myExcelImportService.decodeHireDate(employee.hireDate)
            emp.endDate = employee.endDate ? myExcelImportService.decodeHireDate(employee.endDate) : null
            emp.validate()
            if (emp.hasErrors()) {
                flash.error += emp.errors
            } else {
                if (!emp.user) {
                    User u = new User()
                    def min = Math.min(9,emp.lastName.size()) - 1
                    def username = employee.username ?: new String(emp.firstName[0] + emp.lastName.trim()[0..min]).toLowerCase().replaceAll(/[ ]+/,'')
                    def testuser = User.findByUsername(username)
                    def count= 0
                    while(testuser) {
                        count++
                        testuser = User.findByUsername(username + count.toString())
                    }
                    u.username = username + (count ?  count.toString() : '')
                    u.password = baseService.generateGuid()
                    employeeService.saveUser(u)
                    emp.user = u
                    employeeService.saveUser(emp.user)
                } else if (employee.username) {
                    def u = User.findByUsername(employee.username)
                    if(!u || u.id == emp.userId) {
                        emp.user.username = employee.username
                    }
                    employeeService.saveUser(emp.user)
                }
                employeeService.saveEmployee(emp)
                managerList[employee.bossEmployeeID] = (managerList[employee.bossEmployeeID] ?: []) + [employee.employeeID]
                employeeService.saveUserRole(UserRole.findOrCreateByUserAndRole(emp.user, my_role))
                if (employee.hasAdmin == 'yes') {
                    employeeService.saveUserRole( UserRole.findOrCreateByUserAndRole(emp.user, admin_role))
                }
                if(!employee.bossEmployeeID || employee.bossEmployeeID.trim().size() == 0) {
                    employeeService.deleteEmployeeBosses(emp)
                }
            }
        }

        managerList.each { managerarry ->
            Employees manager = Employees.findByEmployeeId(managerarry.key)
            if (manager && managerarry.value?.size() > 0) {
                managerarry.value.each { empId ->
                    Employees emp = Employees.findByEmployeeId(empId)
                    def eb = EmployeeBoss.createCriteria().get {
                        eq("employee", emp)
                    }
                    if (!eb) {
                        eb = new EmployeeBoss()
                        eb.employee = emp
                        eb.defaultBoss = true
                    }
                    eb.boss = manager
                    employeeService.saveEmployeeBoss(eb)
                }
            } else if (managerarry.key == "null" && managerarry.value?.size() > 0) {
                managerarry.value.each { empId ->
                    Employees emp = Employees.findByEmployeeId(empId)
                    if (emp) {
                        employeeService.deleteEmployeeBosses(emp)
                    }
                }
            }
        }

        String contentType = file.contentType
        convFile.delete()
        request.withFormat {
            form multipartForm {
                flash.message = "FIle Upload Success"
                redirect action: "importEmployee"
            }
            '*' { respond [:], [status: OK] }
        }
    }

}
