package com.feedback

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@Transactional
class UATService {
    IssueService issueService

    List<UserUats> getActiveUATsByUser(User user) {
        def sdate = new Date()
        UserUats.withCriteria {
            eq("user", user)
            uats {
                le('startDate', sdate)
                or {
                    ge('endDate', sdate)
                    isNull('endDate')
                }
            }
            ne("status", Status.Completed)
        } as List<UserUats>
    }

    List<UATSession> getActiveUATs() {
        def sdate = new Date()
        UATSession.withCriteria {
            le('startDate', sdate)
            or {
                ge('endDate', sdate)
                isNull('endDate')
            }
        } as List<UATSession>
    }

    UATSession findUatById(String uatId) {
        UATSession.withCriteria(uniqueResult: true) {
            eq('id',uatId)
        }
    }

    UserUats getUatByUserAndUatSession(User u,UATSession uat) {
        UserUats.withCriteria(uniqueResult: true) {
            eq("user",u)
            eq("uats",uat)
            ne("status",Status.Completed)
        }
    }

    Integer getUatCounts(UATSession uat,Status status) {
        def c = UserUats.createCriteria()
        c.count {
            eq("uats",uat)
            if(status)
                eq("status",status)
        }
    }

    def findAmswerInPramaas(params,id) {
        params?.findAll{
            it.key == id
        }
    }

    def deleteAllRespomses(UserUats uuat,UATSessionQuestions q) {
        UserUATResponse.where {
            userUats == uuat
            question == q
        }.deleteAll()
    }

    def saveUATQuestions(User u,String uatId,params) {
        def ret = []
        UATSession uats = findUatById(uatId)
        UserUats uuat = getUatByUserAndUatSession(u,uats)


        uats.questions.each { UATSessionQuestions q ->
            //remove old responses
            def t = 0
            deleteAllRespomses(uuat,q)
            findAmswerInPramaas(params,q.id)?.each {
                UserUATResponse res = new UserUATResponse()
                res.question = q
                res.userUats = uuat
                Answer a = Answer.findByQuestionAndId(q.question,it.value.toString())
                if(a) {
                    res.singleAnswer = a
                } else {
                    res.textAnswer = it.value
                }
                res.save(failOnError:true)
                t++
            }
            ret.add([id:q.id,count:t])
        }
        ret
    }

    ProgramVersion getUatVersionNumber(UATSession uat) {
        ProgramVersion.withCriteria {
            eq("uatSession",uat)
            isNull("passed")
            order('createDate','desc')
        }?.get(0)
    }

    @CompileStatic
    Boolean setStatus(Status status,String uatId,User u) {
        UserUats uuat = getUatByUserAndUatSession(u,findUatById(uatId))
        uuat.status = status
        uuat.save(failOnError:true)
        true
    }

     def getUatQues(User u, String uatId) {
        UATSession uats = findUatById(uatId)
        UATCommand uatCmd = new UATCommand()
         ProgramVersion pv = getUatVersionNumber(uats)
        uatCmd.title = uats.title
        uatCmd.id = uats.id
        uatCmd.appName = pv.program.toString()
        uatCmd.versionNumber =  pv?.versionNumber
        uatCmd.startDate = uats.startDate
        uatCmd.endDate = uats.endDate
        uatCmd.questions = []
        UATQuestionCommand quest
        UATAnswerCommand quest_ans
        UserUats uuat = getUatByUserAndUatSession(u,uats)
        uats.questions.sort{a,b -> a.orderNumber <=> b.orderNumber}.each { UATSessionQuestions q ->
            quest = new UATQuestionCommand()
            quest.id = q.id
            quest.orderNumber = q.orderNumber
            quest.text = q.question.question
            quest.type = q.question.questionType
            quest.isRequired = q.question.isRequired
            quest.answers = []
            q.question.answers.sort{a,b -> a.orderNumber <=> b.orderNumber}.each { Answer a ->
                quest_ans = new UATAnswerCommand()
                quest_ans.id = a.id
                quest_ans.orderNumber = a.orderNumber
                quest_ans.text = a.answer
                quest_ans.response = (UserUATResponse.withCriteria {
                    eq("userUats",uuat)
                    eq("question",q)
                    eq("singleAnswer",a)
                }?.getAt(0)?.singleAnswer?.id)  ?: UserUATResponse.withCriteria {
                        eq("userUats",uuat)
                        eq("question", q)
                }.getAt(0)?.textAnswer
                quest.answers.add(quest_ans)
            }
            uatCmd.questions.add(quest)
        }
        uatCmd.issuesProblems = issueService.getUatIssuesForUser(u,uats,IssueType.Problem)
        uatCmd.issuesQuestions = issueService.getUatIssuesForUser(u,uats,IssueType.Question)
        uatCmd
    }
}