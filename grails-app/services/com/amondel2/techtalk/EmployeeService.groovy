package com.amondel2.techtalk



class EmployeeService extends BaseService {

    def springSecurityService

    def getBosses(empId) {
        EmployeeBoss.withCriteria{
            eq('eemployee',Employee {
                    eq('id', empId)
                })
        }
    }

    def getInitialData(year,bossId) throws Exception {

        def cmdList = []
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        def ebl = EmployeeBoss.list()?.collect{ it -> it.employee?.id }
        def em = Employees.withCriteria {
                if(bossId) {
                    eq('id', bossId)
                } else {
                    if(ebl) {
                        not { 'in'('id', ebl) }
                    }
                    lt('hireDate', edate)
                    or {
                        ge('endDate', sdate)
                        isNull('endDate')
                    }
                }
            }

        em?.each{ Employees boss->
                def cmd = new EmployeeMCommand(boss)
                if(bossId) {
                    cmd.canEdit = false
                }
                def eb = EmployeeBoss.withCriteria {

                        eq('boss', boss)
                        eq('defaultBoss', true)
                        employee {
                            lt('hireDate', edate)
                            or {
                                ge('endDate', sdate)
                                isNull('endDate')
                            }
                        }

                    projections  {
                        rowCount()
                    }
                }
                cmd.hasChildren = eb[0].value > 0
                cmdList.add(cmd.getDataForJSTree())

        }
        cmdList
    }

    def saveEmployee(EmployeeMCommand cmd) {
        def rtn = ''
        Employees o = new Employees()
        def name = cmd.name.split(' ')
        o.firstName = name[0]
        o.lastName = name[1]
        o.employeeId = cmd.employeeId
        o.location =  EmpLocation.first()
        o.company = Company.first()
        o.save(flush:true,failOnError:true)
        if(cmd.parentId) {
           def emp2 =  Employees.load(cmd.parentId)
            EmployeeBoss eb = new EmployeeBoss()
            eb.employee = o
            eb.boss = Employees.load(cmd.parentId)
            eb.defaultBoss = true
            eb.save(flush:true)
        }

        new EmployeeMCommand(o)?.getDataForJSTree()
    }

    def loadEmployeeServiceChildren(String id,year,currUser) throws Exception {
        def cmdList = []
        def curemp = Employees.findByUser(currUser)
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        this.getDirectReports(id,year,curemp)?.each {
            Employees emp = it.employee
            def cmd = new EmployeeMCommand(emp)
            def eb = EmployeeBoss.withCriteria {
                eq('boss',emp)
                eq('defaultBoss',true)
                employee {
                    lt('hireDate', edate)
                    or {
                        ge('endDate', sdate)
                        isNull('endDate')
                    }
                }
                projections  {
                    rowCount()
                }
            }
            cmd.hasChildren = eb[0].value > 0
            cmdList.add(cmd.getDataForJSTree())
        }
        cmdList

    }

    def updateParentOfEmployee(companyId, id, parentId) throws Exception {
        def em = Employees.createCriteria().get {

            eq('id', id)
        }


        if (parentId.toString().size() > 4) {
            def eb = EmployeeBoss.createCriteria().get {
                eq( "employee", em)
            }
            if(!eb) {
                eb = new EmployeeBoss()
                eb.employee = em
                eb.defaultBoss = true
            }
            eb.boss = Employees.findById(parentId)
            eb.save(flush:true,failOnError:true)
        } else {
            EmployeeBoss.where{
                employee == em
            }.deleteAll()
        }

        [status: 'SUCCESS']
    }


    def getDirectReports(empId,year,emp) {
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        def eb
        if(emp && emp.id) {
           eb = EmployeeBoss.withCriteria {
               boss {
                   eq('id', empId)
               }
               eq('defaultBoss', true)
               employee {
                   eq('id', emp.id)
                   lt('hireDate', edate)
                   or {
                       ge('endDate', sdate)
                       isNull('endDate')
                   }
               }
           }
       }
        if(!eb || eb.size() == 0) {

            eb = EmployeeBoss.withCriteria {
                boss {
                    eq('id', empId)
                }
                eq('defaultBoss', true)
                employee {
                    lt('hireDate', edate)
                    or {
                        ge('endDate', sdate)
                        isNull('endDate')
                    }
                }
            }
        }

        eb


    }

    def getAllEmployeesChildernFlat(empId,rtn,year) {
        def sdate = new GregorianCalendar(year?.toInteger(), 0, 1, 0, 0, 0).getTime()
        def edate = new GregorianCalendar(year?.toInteger() + 1, 0, 1, 0, 0, 0).getTime()
        EmployeeBoss.withCriteria {
            boss {
                eq('id', empId)
            }
            employee {

                lt('hireDate', edate)
                or {
                    ge('endDate', sdate)
                    isNull('endDate')
                }
            }
        }?.each {
            rtn << it.employee
            getAllEmployeesChildernFlat(it.employeeId, rtn, year)
        }
    }

        def getAllEmployeesFlat(rtn,year) {
            def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
            def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
            Employees.withCriteria {
                    lt('hireDate', edate)
                    or {
                        ge('endDate', sdate)
                        isNull('endDate')
                    }
            }?.each{
                rtn << it
            }
        }


    def peopleUnder(rtn,me,cal) {

        EmployeeBoss.findAllByBoss(me)?.each { EmployeeBoss eb ->
            def sum = 0
            if (!eb.employee.endDate || eb.employee.endDate > cal.getTime()) {
                EmployeePercentMonthYear.withCriteria {
                    employeeJRP {
                        employee {
                            eq('id', eb.employeeId)
                            or {
                                isNull('endDate')
                                ge('endDate', cal.getTime())
                            }
                        }
                    }
                    eq('monthYear', cal.getTime())
                }?.each {
                    sum += it.percentage
                }
                if (sum < 100) {
                    if (rtn[eb.boss.toString()]) {
                        rtn[eb.boss.toString()] << eb.employee.toString()
                    } else {
                        rtn[eb.boss.toString()] = [eb.employee.toString()]
                    }
                }
                if (eb.employee?.employees?.size() > 0) {
                    peopleUnder(rtn, eb.employee,cal)
                }
            }
        }
    }


}
