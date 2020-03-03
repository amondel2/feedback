package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class UATController {

    SpringSecurityService springSecurityService
    UATService UATService
    def initalize() {
        def res =  [msg : "success"]
        try {
            session["uatSession"] = [:]
            session["uatSession"]['uatId'] = params.id?.trim()
            User me = springSecurityService.getCurrentUser()
            //Make sure it exists and this user has access
            session["uatSession"]["userId"] = me.id
        }  catch (Exception e) {
            res = [msg:e.getMessage(),st:e.getStackTrace(),ses:session["uatSession"]]
        }
        withFormat {
            '*' { render res as JSON }
        }
    }

    def myQuestionAndAnswers() {
        def res = [:]
        try {
            if (session["uatSession"]?.uatId) {
                res.questions = UATService.getUatQues(springSecurityService.getCurrentUser(), session["uatSession"].uatId)
                res.issues = UATService.getUatIssuesForUser(springSecurityService.getCurrentUser(), session["uatSession"].uatId)
            } else {
                res = session["uatSession"]
            }
        } catch (Exception e) {
            res = [msg:e.getMessage(),st:e.getStackTrace(),ses:session["uatSession"]]
        }
        withFormat {
            '*' { render res as JSON }
        }
    }

    def unload() {
        session["uatSession"] = null
    }

    def saveQandRes() {
        def res = [:]
        try {
            res = UATService.saveUATQuestions(springSecurityService.getCurrentUser(), session["uatSession"].uatId, params)
        } catch (Exception e) {
            res = [msg:e.getMessage(),st:e.getStackTrace(),ses:session["uatSession"]]
        }
        withFormat {
            '*' { render res as JSON }
        }
    }
}
