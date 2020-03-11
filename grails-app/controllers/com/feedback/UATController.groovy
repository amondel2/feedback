package com.feedback

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class UATController {

    SpringSecurityService springSecurityService
    UATService UATService
    IssueService issueService

    def initialize() {
        def res =  [msg : "success"]
        try {
            session["uatSession"] = [:]
            session["uatSession"]['uatId'] = params.id?.trim()
            UATSession uats= UATService.findUatById(session["uatSession"]['uatId'])
            if(!(uats instanceof UATSession)) {
                throw new Exception("UAT SESSION NOT FOUND")
            }
            User me = springSecurityService.getCurrentUser()
            if(!(UATService.getUatByUserAndUatSession(me,uats) instanceof UserUats)){
                throw new Exception("UAT User Not Test Access")
            }
            //Make sure it exists and this user has access
            session["uatSession"]["userId"] = me.id
        }  catch (Exception e) {
            res = [msg:e.getMessage(),st:e.getStackTrace(),ses:session["uatSession"]]
        }
        withFormat {
            json { render res as JSON }
            '*' {
                if(res.msg == "success") {
                    redirect(action:"myQuestionAndAnswers")
                } else {
                    render res as JSON
                }
            }
        }
    }

    def myQuestionAndAnswers() {
        def res = [:]
        try {
            if (session["uatSession"]?.uatId) {
                res = UATService.getUatQues(springSecurityService.getCurrentUser(), session["uatSession"].uatId)
                UATService.setStatus(Status.Active,session["uatSession"].uatId,springSecurityService.getCurrentUser())
            } else {
                res = session["uatSession"]
            }
        } catch (Exception e) {
            res = [msg:e.getMessage(),st:e.getStackTrace(),ses:session["uatSession"]]
        }
        withFormat {
            json { render res as JSON  }
            '*' { render(view: "index", model: [res:res]) }
        }
    }

    def unload() {
        session["uatSession"] = null
    }

    def saveQandRes() {
        def res = [:]
        try {
            res = UATService.saveUATQuestions(springSecurityService.getCurrentUser(), session["uatSession"].uatId, params)
            if(params.st == "Complete")
                UATService.setStatus(Status.Completed,session["uatSession"].uatId,springSecurityService.getCurrentUser())
        } catch (Exception e) {
            res = [msg:e.getMessage(),st:e.getStackTrace(),ses:session["uatSession"]]
        }
        withFormat {
            '*' { render res as JSON }
        }
    }

    def createNewIssue() {
        def res = [msg : "success"]
        def er
        try {
            er = issueService.saveIssue(params,UATService.findUatById(session["uatSession"]['uatId']))
            if(! er instanceof Issue) {
                throw new Exception(er.toString())
            }
            res.id = er.id
        } catch (Exception e) {
            res = [msg:e.getMessage(),st:e.getStackTrace()]
        }
        withFormat {
            '*' { render res as JSON }
        }
    }
}
