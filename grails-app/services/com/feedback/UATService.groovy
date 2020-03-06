package com.feedback

import grails.gorm.transactions.Transactional

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

    UATSession findUatById(String uatId) {
        UATSession.withCriteria(uniqueResult: true) {
            eq('id',uatId)
        }
    }

    def getUatByUserAndUatSession(User u,UATSession uat) {
        UserUats.withCriteria(uniqueResult: true) {
            eq("user",u)
            eq("uats",uat)
            ne("status",Status.Completed)
        }
    }

    def findAmswerInPramaas(params,id) {
        params.quest?.find{ it.id == id }?.responses
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
        UserUats uuat = UserUats.withCriteria {
            eq("user",u)
            eq("uats",uats)
        }?.get(0)

        uats.questions.each { UATSessionQuestions q ->
            //remove old responses
            def t = 0
            deleteAllRespomses(uuat,q)
            findAmswerInPramaas(params,q.id)?.each {
                UserUATResponse res = new UserUATResponse()
                res.question = q
                res.userUats = uuat
                Answer a = Answer.findByQuestionAndId(q.question,it.toString())
                if(a) {
                    res.response = a
                } else {
                    res.textAnswer = it
                }
                res.save()
                t++
            }
            ret.add([id:q.id,count:t])
        }
        ret
    }

     def getUatQues(User u, String uatId) {
        UATSession uats = findUatById(uatId)
        UATCommand uatCmd = new UATCommand()
        uatCmd.title = uats.title
        uatCmd.id = uats.id
        uatCmd.startDate = uats.startDate
        uatCmd.endDate = uats.endDate
        uatCmd.questions = []
        UATQuestionCommand quest
        UATAnswerCommand quest_ans
        UserUats uuat = UserUats.withCriteria {
            eq("user",u)
            eq("uats",uats)
        }?.get(0)
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
        uatCmd.issues = issueService.getUatIssuesForUser(u,uats)
        uatCmd
    }
}