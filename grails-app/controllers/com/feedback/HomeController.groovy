package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired

@Secured(['permitAll'])
class HomeController {

    @Autowired
    UATService uatService
    SpringSecurityService springSecurityService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def getMyUats(){
        List<UserUats> p = uatService.getActiveUATsByUser(springSecurityService.getCurrentUser())
        def res = p.collect{ UserUats uat ->
            UATSession t = uat.uats
            [title:t.title,startDate:t.startDate,endDate:t.endDate,id:t.id]
        }
        res.sort{a,b -> a.endDate <=> b.endDate}
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
