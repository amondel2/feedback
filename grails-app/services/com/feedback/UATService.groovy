package com.feedback

import grails.gorm.transactions.Transactional

@Transactional
class UATService {
    def getUATsByUser(User user) {
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
        }
    }
}
