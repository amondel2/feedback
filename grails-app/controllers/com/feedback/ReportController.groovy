package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_UAT_ADMIN'])
class ReportController {

    SpringSecurityService springSecurityService
    UATService UATService

    def uatReort() {
        render(view:"uatReport")
    }

    def findUATs(){
        def rtn = [:]
        try {
            rtn.info = UATService.findUATs(params)?.collect{UATSession uat ->
                ['title':uat.title, 'program':uat.program.name,'startDate':uat.startDate,'endDate':uat.endDate]
            }
        } catch(Exception e) {
            rtn.msg = e.getMessage()
        }
        withFormat {
            '*' { render rtn as JSON }
        }

    }
}
