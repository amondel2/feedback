package com.feedback

import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    def saveResetToken(params,guid) {
        def empid = params.emp
        User emp = User.get(empid)
        emp.restToken = token
        emp.save(flush:true)
    }

    def getIdeas(User u) {
        u.ideas.collect{
            ['title':it.title,'votes':it.votes.size()]
        }
    }

    def getLatestIdeas() {
        def cc = Idea.createCriteria()
        def sessions = cc.list (max: 20, offset: 0, sort: 'dateCreated', order: 'dateCreaated') {

         }.collect{
            ['title':it.title,'votes':it.votes.size(),'creaated':it.dateCreated]
        }
    }

    Boolean createUser(RegisterCommand registerCommand){
        User u = new User()
        u.email = registerCommand.email
        u.accountExpired = false
        u.enabled = true
        u.firstName = registerCommand.firstName
        u.lastName = registerCommand.lastName
        u.employeeId = registerCommand.employeeId
        u.username = registerCommand.username
        u.password = registerCommand.password
        u.accountLocked= false
        u.passwordExpired = false
        u.validate()
        if(u.hasErrors())
            return false
        UserRole.withTransaction {
            u.save()
            UserRole ur = new UserRole()
            ur.user = u
            ur.role = Role.findByAuthority('ROLE_USER')
            ur.save()
        }
        true
    }
}
