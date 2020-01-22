package com.feedback

import grails.gorm.transactions.Transactional

@Transactional
class ProjectManagerService {

    def parseResponseSet(params) {
        def paramsbak = params
        def successBox = params.successBoxes
        def slurper = new groovy.json.JsonSlurper()
        def result = slurper.parseText(successBox)
        def responses = slurper.parseText(params.responses)
        def emp = null
        def empId = null
        result.each { val ->
            def index = val.key
            if (!emp) {
                def keyval = val.value.keySet().getAt(0).split('_')
                empId = keyval.getAt(2)
                emp = User.findById(empId)
            }

            //get all the responses
            def thisItemsRespponses = responses.getAt(index)
            def i = 1
            for (i; i <= 12; i++) {
                if (ej && thisItemsRespponses) {
                    def thisResponse = thisItemsRespponses[index + "_" + i + "_title_" + empId]
                    if(thisResponse && thisResponse.size() > 0) {
                        def cal = new GregorianCalendar(params.year?.toInteger(), i - 1, 1, 0, 0, 0, 0)
                        def currentValue = EmployeePercentMonthYear.findOrCreateByMonthYearAndEmployeeJRP(cal.getTime(), ej)
                        currentValue.percentage =  thisResponse?.toDouble()
                        currentValue.save(flush: true, failOnError: true)
                    }
                }
            }
        }
        [succcess: true]
    }

    def deleteWorkItem(params) {
        def successBox = params.selectBox
        def slurper = new groovy.json.JsonSlurper()
        def val = slurper.parseText(successBox)
        def emp = User.findById(params.empId)
        def empId = params.empId
        def index = params.index
        def ej
        if (ej) {
            User.where {
                id == ej
            }.deleteAll()
            ej.delete(flush: true)
        }
        [status: "success"]
    }

    def getTopLevelOrgs() {
        def cmdList = []
        Organization.findAllByParentIsNull()?.each { Organization org->
            def cmd = new OrganizationCommnad(org)
            cmd.hasChildren = this.orgHasChildren(org)
            cmdList.add(cmd.getDataForJSTree())
        }
        cmdList
    }

    boolean orgJobHasChildren(JobOrg org) {
        org?.employees?.size() > 0
    }

    boolean orgHasChildren(Organization org) {
        if(org.children?.size() > 0 || org.jobs?.size() > 0)
            return true
        false
    }

    def getChildOrg(String id) {
        Organization parent =  Organization.get(id)
        if(parent)
          return getChildOrg(parent) + getChildJob(parent)
        return getorgJobEmployees(JobOrg.get(id))
    }

    def getChildOrg(Organization parentOrg) {
        def cmdList = []
        parentOrg.children?.each { Organization org->
            def cmd = new OrganizationCommnad(org)
            cmd.hasChildren = this.orgHasChildren(org)
            cmdList.add(cmd.getDataForJSTree())
        }
        cmdList
    }

    def getChildJob(Organization parentOrg) {
        def cmdList = []
        parentOrg.jobs?.each { JobOrg org->
            def cmd = new OrgJobCommnad(org)
            cmd.hasChildren = this.orgJobHasChildren(org)
            cmdList.add(cmd.getDataForJSTree())
        }
        cmdList
    }

    def getorgJobEmployees(JobOrg jo) {
        def cmdList = []
        jo.employees?.each{ UserJob uj ->
            def cmd = new UserMCommand(uj.user)
            cmdList.add(cmd.getDataForJSTree())
        }
        cmdList
    }


    def getResponseSetForEmployee(empId,year) {
        def restultSet = []
        def empSet = []
        User emp = User.findById(empId)
        GregorianCalendar gc = new GregorianCalendar()
        gc.setTime( emp.hireDate)
        if(gc.get(Calendar.YEAR) == year?.toInteger()) {
            empSet[0]  = gc.get(Calendar.MONTH)
        } else {
            empSet[0] = -1
        }
        empSet[1] = 16
        if(emp.endDate) {
            gc.setTime(emp.endDate)
            if (gc.get(Calendar.YEAR) == year?.toInteger()) {
                empSet[1] = gc.get(Calendar.MONTH)
            }
        }

                [restultSet,empSet]
    }
}
