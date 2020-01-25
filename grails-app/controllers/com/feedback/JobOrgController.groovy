package com.feedback

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class JobOrgController {

    static scaffold =  JobOrg
}
