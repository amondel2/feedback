package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.apache.poi.ss.formula.functions.T

@Secured(['IS_AUTHENTICATED_FULLY'])
class UATController {

    SpringSecurityService springSecurityService
    UATService UATService
    def initalize() {
        session["uatSession"] = [:]
        session["uatSession"]['uatId'] = params.id
        User me = springSecurityService.getCurrentUser()
        //Make sure it exists and this user has access
        session["uatSession"]["userId"] = me.id
        redirect(action:"myQuestionAndAnswers")
    }

    def myQuestionAndAnswers() {
        def res = [:]
        if(session["uatSession"]?.uatId) {
            res = UATService.getUATQuestions(springSecurityService.getCurrentUser(),session["uatSession"].uatId)
        } else {
            res = session["uatSession"]
        }
        withFormat {
            '*' { render res as JSON }
        }
    }

    def unload() {
        session["uatSession"] = null
    }
}
