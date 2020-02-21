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
        def p = uatService.getUATsByUser(springSecurityService.getCurrentUser())
        withFormat {
            '*' {
                render p as JSON
            }
        }
    }

    def index() {
        render(view:"index")
    }
}
