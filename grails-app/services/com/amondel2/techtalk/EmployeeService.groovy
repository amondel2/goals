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

    def getEmployee(String id){
        Employees.load(id)
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

                if(cmd.canEdit) {
                    def states = getEmpGoalStates(boss,sdate,edate)
                    cmd.goalStatus = states[0] + " Goals Completed out of  " + states[1]
                } else {
                    cmd.goalStatus = "No Access To View"
                }
                cmd.hasChildren = eb[0].value > 0
                cmdList.add(cmd.getDataForJSTree())

        }
        cmdList
    }

    def doesEmpHaveDirects(Employees emp) {
        doesEmpHaveDirects(emp, GregorianCalendar.getInstance().get(Calendar.YEAR))
    }


    def doesEmpHaveDirects(Employees emp,year) {
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        (EmployeeBoss.withCriteria{
            eq('boss', emp)
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
        }?.getAt(0)?.value) > 0

    }

    def saveEmployee(EmployeeMCommand cmd) {
        def rtn = ''
        Employees o = new Employees()
        def name = cmd.name.split(' ')
        o.firstName = name[0]
        o.lastName = name[1]
        o.employeeId = cmd.employeeId
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
            def states = getEmpGoalStates(emp,sdate,edate)

            cmd.goalStatus = states[0] + " Goals Completed out of  " +states[1]
            cmdList.add(cmd.getDataForJSTree())
        }
        cmdList

    }

    def updateParentOfEmployee(companyId, id, parentId) throws Exception {
        def em = Employees.createCriteria().get {

            eq('id', id)
        }

        EmployeeBoss.where{
            employee == em
        }.deleteAll()
        if (parentId.toString().size() > 4) {
            EmployeeBoss eb = new EmployeeBoss()
            eb.employee = em
            eb.defaultBoss = true
            eb.boss = Employees.findById(parentId)
            eb.save(flush:true,failOnError:true)
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

    def getEmpGoalStates(Employees emp,Date sdate, Date edate) {
        def completedGoals = EmployeeGoal.withCriteria {
            eq ("employee",emp)
            'in'('status',[GoalStatus.Cancelled,GoalStatus.Completed])
            between('actualCompletedDate',sdate,edate)
        }
        def totalGoals = EmployeeGoal.withCriteria {
            eq ("employee",emp)
            or{
                and {
                    'in'('status',[GoalStatus.Cancelled,GoalStatus.Completed])
                    between('actualCompletedDate',sdate,edate)
                }
                and {
                    not {'in'("status",[GoalStatus.Cancelled,GoalStatus.Completed])}
                    between('targetCompletDate',sdate,edate)
                }
            }
        }
        [completedGoals?.size() ?: 0, totalGoals?.size() ?: 0]
    }

    def getEmployeeOver(emp,cal,gtTime) {
        EmployeeGoal.withCriteria {
            eq("employee", emp)
            'in'('status', [GoalStatus.NotStarted, GoalStatus.Behind, GoalStatus.Ongoing, GoalStatus.OnTrack])
            if(gtTime) {
                println(gtTime.format('MM-dd-YYYY') + " " + cal.format('MM-dd-YYYY' ) )
                between('targetCompletDate',gtTime.getTime(),cal.getTime() )
            } else {
                lt('targetCompletDate',cal.getTime() )
            }
        }
    }

    def getSingleEmployee(rtn,me,cal,gtTime) {
        getEmployeeOver(me,cal,gtTime)?.each {
            if (rtn[me.toString()]) {
                rtn[me.toString()] << [goal:it.title,due:it.targetCompletDate.format('MM-dd-YYYY')]
            } else {
                rtn[me.toString()] = [[goal:it.title,due:it.targetCompletDate.format('MM-dd-YYYY')]]
            }
        }
    }

    def peopleUnder(rtn,me,cal,gtTime) {
        EmployeeBoss.findAllByBoss(me)?.each { EmployeeBoss eb ->
            if (!eb.employee.endDate || eb.employee.endDate > cal.getTime()) {
                getEmployeeOver(eb.employee,cal,gtTime)?.each {
                    if (rtn[eb.employee.toString()]) {
                        rtn[eb.employee.toString()] << [goal:it.title,due:it.targetCompletDate.format('MM-dd-YYYY')]
                    } else {
                        rtn[eb.employee.toString()] = [[goal:it.title,due:it.targetCompletDate.format('MM-dd-YYYY')]]
                    }
                }
                if (eb.employee?.employees?.size() > 0) {
                    peopleUnder(rtn, eb.employee, cal)
                }
            }
        }
    }
}
