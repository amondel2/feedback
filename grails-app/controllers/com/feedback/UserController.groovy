package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class UserController {

    static scaffold =  User

    UserService userService;

    def generateResetToken() {
        String guid = Utils.getInstance().idGenerator()
        userService.saveResetToken(params,guid)
        def p = ['token': guid]
        withFormat {
            '*' {
                render p as JSON
            }
        }
    }

}
