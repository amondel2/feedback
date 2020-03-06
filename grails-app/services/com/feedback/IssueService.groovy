package com.feedback

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.CompileStatic

@Transactional
class IssueService {

    SpringSecurityService springSecurityService

    def saveIssue(params,uatsession) {
        try {
            Issue ic = Issue.findById(params.id)
            if(!ic) {
                ic = new Issue()
            }
            ic.issueType = IssueType.find{ it.toString().toLowerCase() == params.issueType?.trim().toLowerCase() }
            ic.issueDescription = params.issueDescription ?: ic.issueDescription
            ic.issueResponse = params.issueResponse ?: ic.issueResponse
            ic.parentIsssue = Issue.findById(params.parentIsssue) ?: ic.parentIsssue
            ic.uatSession = UATSession.findById(params.uatSession) ?: (ic.uatSession ?: uatsession)
            ic.employee = ic.employee ?: springSecurityService.getCurrentUser()
            if(ic.validate()) {
                ic.save(failOnError:true,flush:true)
                return ic
            } else {
                return ic.getErrors()
            }
        } catch (Exception e) {
            return e
        }
    }

    @CompileStatic
    List<IssueCommand> getUatIssuesForUser (User u, UATSession uats,IssueType ist) {
        Issue.withCriteria {
            eq("uatSession",uats)
            eq("employee",u)
            eq("issueType",ist)
        }?.collect{ Issue myi ->
            IssueCommand c = new IssueCommand()
            c.issueDescription = myi.issueDescription
            c.issueResponse = myi.issueResponse
            c.issueType = myi.issueType
            c.id = myi.id
            c
        }
    }



    List<Issue> getUatIssues (Boolean answered,Integer maxRees) {
        Issue.withCriteria {
            if(!answered) {
                isNull("issueResponse")
            }
            order('createDate','desc')
            maxResults(maxRees)
        }
    }
}
