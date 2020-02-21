package com.feedback

import grails.gorm.transactions.Transactional

@Transactional
class UATService {
    def getActiveUATsByUser(User user) {
        def sdate = new Date()
        UserUats.withCriteria {
            eq("user", user)
            uats {
                ge('startDate', sdate)
                or {
                    lt('endDate', sdate)
                    isNull('endDate')
                }
            }
            ne("status",Status.Completed)
        }
    }

    def getUATQuestions(User u,UATSession uats) {
        uats.questions.sort('orderNumber').each { Question q -> }


    }

}
