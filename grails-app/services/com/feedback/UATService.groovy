package com.feedback

import grails.gorm.transactions.Transactional

@Transactional
class UATService {
    def getActiveUATsByUser(User user) {
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
            ne("status",Status.Completed)
        }

    }

    UATSession getUatById(String uatId) {
        UATSession.withCriteria(uniqueResult: true) {
            eq('id',uatId)
        }
    }

    def getUatByUserAndUatSession(User u,UATSession uats) {
        UserUats.withCriteria {
            eq("user",u.id)
            eq("uats",uats)
            ne("status",Status.Completed)
            or{
                isNull("endDate")
                le("endDate",new Date())
            }
        }
    }

    def getUATQuestions(User u,String uatId) {
        getUATQuestions(u,getUatById(uatId))
    }

    def getUATQuestions(User u,UATSession uats) {
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
        uatCmd

    }



}
