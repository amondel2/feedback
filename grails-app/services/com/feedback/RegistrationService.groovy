package com.feedback

import grails.gorm.transactions.Transactional

@Transactional
class RegistrationService {

    def springSecurityService

    def getChallegneAnswers(eid) {
        User emp = User.get(eid)
        [emp.restToken,emp.employeeId]
    }

    Boolean updatePassword(ResetPasswordCommand resetPasswordCommand ) {
        def query = User.where {
            id == resetPasswordCommand.eid
        }
        query.updateAll([password:springSecurityService.encodePassword(resetPasswordCommand.password),restToken:null,accountExpired:false,enabled:true,accountLocked:false,passwordExpired:false]) == 1
    }
}
