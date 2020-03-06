package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired

@Secured(['permitAll'])
class HomeController {

    @Autowired
    UATService uatService
    IssueService issueService
    SpringSecurityService springSecurityService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def getMyUats(){
        List<UserUats> p = uatService.getActiveUATsByUser(springSecurityService.getCurrentUser())
        def res = p.collect{ UserUats uat ->
            UATSession t = uat.uats
            [title:t.title,startDate:t.startDate,endDate:t.endDate,id:t.id,program:t.program.toString()]
        }
        res.sort{a,b -> a.endDate <=> b.endDate}
        withFormat {
            '*' {
                render res as JSON
            }
        }
    }

    @Secured(['ROLE_ADMIN','ROLE_UAT_ADMIN'])
    def getIssues() {
        List<Issue> p = issueService.getUatIssues(false,Integer.valueOf(params.fut.toString()))
        def res = p.collect{ Issue is ->
            [issue:is.issueDescription,uat:is.uatSession.toString(),type:is.issueType.toString(),id:is.id,user:is.employee.toString()]
        }
        withFormat {
            '*' {
                render res as JSON
            }
        }
    }

    def index() {
        render(view:"index")
    }
}
