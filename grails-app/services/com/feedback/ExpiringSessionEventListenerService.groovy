package com.feedback

import grails.gorm.DetachedCriteria
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService

import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

@Transactional
class ExpiringSessionEventListenerService implements HttpSessionListener {

    SpringSecurityService springSecurityService

    public void sessionCreated(HttpSessionEvent event) {
        //  log.info("Session created");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        //setany active UAT to InActive
        DetachedCriteria<UserUats> dx = UserUats.where { user == springSecurityService.getCurrentUser() && status == Status.Active }
        dx.updateAll([status:Status.InActive])
    }
}
