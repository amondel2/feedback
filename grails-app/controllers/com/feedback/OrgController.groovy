package com.feedback

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class OrgController {

    static scaffold =  Organization
}
