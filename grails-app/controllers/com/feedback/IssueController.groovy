package com.feedback

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_UAT_ADMIN'])
class IssueController {
    static scaffold =  Issue
}
