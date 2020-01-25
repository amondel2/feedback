package com.feedback

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class JobController {

    static scaffold =  Job
}
