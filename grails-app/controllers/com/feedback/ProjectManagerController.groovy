package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ProjectManagerController extends MondelMapperUIController {

    SpringSecurityService springSecurityService
    def assetResourceLocator
    def projectManagerService
    def goalService

    def index() {
        User me = springSecurityService.getCurrentUser()
        User boss
        if(me && me.bosses && me.bosses.size() > 0) {
            boss = me.bosses.first()?.boss
        }


        def cal = Calendar.getInstance()
        def date = params?.myDate_year ? new GregorianCalendar(params.myDate_year?.toInteger(),0,1) : new GregorianCalendar(cal.get(Calendar.YEAR),0,1)
//        def edate = params?.myDate_year ? new GregorianCalendar(params.myDate_year?.toInteger(),11,31) : new GregorianCalendar(cal.get(Calendar.YEAR),11,31)

        render(view:"index",model:[date:date, boss:boss, companyName:"Foo", goalTypes:[:]])
    }


    def orgMapper() {
        render (view:"orgMapper")
    }

    def getTopLevelOrgs() {
        def data  = projectManagerService.getTopLevelOrgs()
        render ([childData: data] as JSON)
    }

    def loadOrgChildren() {
        try {
            def data = projectManagerService.getChildOrg(params.id)
            render data as JSON
        } catch (Exception e) {
            log.error('loadOrgChildren: ' + e)
            render(status: 500)
        }
    }


    def deleteWorkItem() {
        def rs = projectManagerService.deleteWorkItem(params)
        withFormat {
            '*' {
                render(rs as JSON)
            }
        }
    }

    def savePortfolioProjectJobs() {

        def paramsback = projectManagerService.parseResponseSet(params)
        withFormat {
            '*' {
                render(paramsback as JSON)
            }
        }
    }

    def getResultSet() {
        def paramsback = goalService.getGoalSetForEmployee(params?.empId,params.year)
            withFormat {
            '*' {
                render([rs:paramsback] as JSON)
            }
        }
    }

    def saveLeaveDate() {
        def msg = "Success"
        try {
            User e = User.findById(params.empId)
            if (e) {
                def darr = params.leaveDate?.split("/")
                e.endDate = new GregorianCalendar(darr[2]?.toInteger(), darr[0]?.toInteger(), darr[1]?.toInteger()).getTime()
                e.save(flush: true, failOnError: true)
            }
        } catch(Exception e) {
            msg = "Fail"
        }
        withFormat {
            '*' {
                render([rs: msg] as JSON)
            }
        }
    }


    @Override
    boolean hasReadPermission() {
       true
    }

    @Override
    boolean hasWritePermission() {
       true
    }
    
	@Override
    boolean hasImportExportPermission() {
        return false
    }

    @Override
    SpringSecurityService getSSservice() {
        return springSecurityService
    }
}