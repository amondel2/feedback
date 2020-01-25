package com.feedback

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class UserJobController {

    static scaffold =  UserJob
}
