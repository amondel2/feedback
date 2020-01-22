package com.feedback

import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat


class EmployeeService {

    def springSecurityService

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy")

    def getBosses(empId) {
        UserBoss.withCriteria{
            eq('employee',User {
                    eq('id', empId)
                })
        }
    }

    @Transactional
    def saveResetToken(params,String token){
        def empid = params.emp
        User emp = User.get(empid)
        emp.restToken = token
        emp.save(flush:true)
    }

    def getEmployee(String id){
        User.load(id)
    }

    def getInitialData(year,bossId) throws Exception {

        def cmdList = []
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        def ebl = UserBoss.list()?.collect{ it -> it.employee?.id }
        def em = User.withCriteria {
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

        em?.each{ User boss->
                def cmd = new UserMCommand(boss)
                if(bossId) {
                    cmd.canEdit = false
                }
                def eb = UserBoss.withCriteria {

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

    def doesEmpHaveDirects(User emp) {
        doesEmpHaveDirects(emp, GregorianCalendar.getInstance().get(Calendar.YEAR))
    }


    def doesEmpHaveDirects(User emp, year) {
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        (UserBoss.withCriteria{
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

    def saveEmployee(UserMCommand cmd) {
        def rtn = ''
        User o = new User()
        def name = cmd.name.split(' ')
        o.firstName = name[0]
        o.lastName = name[1]
        o.employeeId = cmd.employeeId
        o.save(flush:true,failOnError:true)
        if(cmd.parentId) {
           def emp2 =  User.load(cmd.parentId)
            UserBoss eb = new UserBoss()
            eb.employee = o
            eb.boss = User.load(cmd.parentId)
            eb.defaultBoss = true
            eb.save(flush:true)
        }

        new UserMCommand(o)?.getDataForJSTree()
    }

    def loadEmployeeServiceChildren(String id,year,currUser) throws Exception {
        def cmdList = []
        def curemp = currUser
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        this.getDirectReports(id,year,curemp)?.each {
            User emp = it.employee
            def cmd = new UserMCommand(emp)
            def eb = UserBoss.withCriteria {
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
        def em = User.createCriteria().get {

            eq('id', id)
        }

        UserBoss.where{
            employee == em
        }.deleteAll()
        if (parentId.toString().size() > 4) {
            UserBoss eb = new UserBoss()
            eb.employee = em
            eb.defaultBoss = true
            eb.boss = User.findById(parentId)
            eb.save(flush:true,failOnError:true)
        }

        [status: 'SUCCESS']
    }


    def getDirectReports(empId,year,emp) {
        def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
        def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
        def eb
        if(emp && emp.id) {
           eb = UserBoss.withCriteria {
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

            eb = UserBoss.withCriteria {
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

    def getAllUserChildernFlat(empId,rtn,year) {
        def sdate = new GregorianCalendar(year?.toInteger(), 0, 1, 0, 0, 0).getTime()
        def edate = new GregorianCalendar(year?.toInteger() + 1, 0, 1, 0, 0, 0).getTime()
        UserBoss.withCriteria {
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

        def getAllUserFlat(rtn,year) {
            def sdate =  new GregorianCalendar(year?.toInteger(),0,1,0,0,0).getTime()
            def edate =   new GregorianCalendar(year?.toInteger() + 1,0,1,0,0,0).getTime()
            User.withCriteria {
                    lt('hireDate', edate)
                    or {
                        ge('endDate', sdate)
                        isNull('endDate')
                    }
            }?.each{
                rtn << it
            }
        }

    @Deprecated
    def getEmpGoalStates(User emp, Date sdate, Date edate) {
        return [0,0]
//        def completedGoals = EmployeeGoal.withCriteria {
//            eq ("employee",emp)
//            'in'('status',[GoalStatus.Cancelled, GoalStatus.Completed])
//            between('actualCompletedDate',sdate,edate)
//        }
//        def totalGoals = EmployeeGoal.withCriteria {
//            eq ("employee",emp)
//            or{
//                and {
//                    'in'('status',[GoalStatus.Cancelled, GoalStatus.Completed])
//                    between('actualCompletedDate',sdate,edate)
//                }
//                and {
//                    not {'in'("status",[GoalStatus.Cancelled, GoalStatus.Completed])}
//                    between('targetCompletDate',sdate,edate)
//                }
//            }
//        }
//        [completedGoals?.size() ?: 0, totalGoals?.size() ?: 0]
    }

    def getEmployeeOver(emp,cal,gtTime) {
        EmployeeGoal.withCriteria {
            eq("employee", emp)
            'in'('status', [GoalStatus.NotStarted, GoalStatus.Behind, GoalStatus.Ongoing, GoalStatus.OnTrack])
            if(gtTime) {
                between('targetCompletDate',gtTime.getTime(),cal.getTime() )
            } else {
                lt('targetCompletDate',cal.getTime() )
            }
        }
    }

    def getSingleEmployee(rtn,me,cal,gtTime) {
        getEmployeeOver(me,cal,gtTime)?.each {
            if (rtn[me.toString()]) {
                rtn[me.toString()] << [goal:it.title,due: sdf.format(it.targetCompletDate)]
            } else {
                rtn[me.toString()] = [[goal:it.title,due:sdf.format(it.targetCompletDate)]]
            }
        }
    }

    Boolean isUserHaveAccessChildren(User emp, User user, year) {
        if(emp.user == user) {
            return true
        }
        User empt = User.findByUser(user)
        if(!empt) {
            //Super user
            return true
        }
        def rtn = []
        this.getAllUserChildernFlat(empt.id,rtn,year)
         rtn.find{ it.equals(emp) }.equals(null) == false
    }

    def peopleUnder(rtn,me,cal,gtTime) {
        UserBoss.findAllByBoss(me)?.each { UserBoss eb ->
            if (!eb.employee.endDate || eb.employee.endDate > cal.getTime()) {
                getEmployeeOver(eb.employee,cal,gtTime)?.each {
                    if (rtn[eb.employee.toString()]) {
                        rtn[eb.employee.toString()] << [goal:it.title,due:sdf.format(it.targetCompletDate)]
                    } else {
                        rtn[eb.employee.toString()] = [[goal:it.title,due:sdf.format(it.targetCompletDate)]]
                    }
                }
                if (eb.employee?.employees?.size() > 0) {
                    peopleUnder(rtn, eb.employee, cal,gtTime)
                }
            }
        }
    }
}
