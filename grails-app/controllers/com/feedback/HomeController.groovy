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

        withFormat {
            '*' {
                render p.collect{ UserUats uat ->
                        UATSession t = uat.uats
                        [title:t.title,startDate:t.startDate,endDAte:t.endDate,id:uat.id]
                } as JSON
            }
        }
    }

    def index() {
        render(view:"index")
    }
}
