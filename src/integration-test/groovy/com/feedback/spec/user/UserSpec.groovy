package com.feedback.spec


import com.feedback.page.user.UserListPage
import geb.driver.CachingDriverFactory
import geb.spock.GebReportingSpec
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.testing.mixin.integration.Integration
import spock.lang.Stepwise

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Stepwise
class UserSpec extends GebReportingSpec  {

    def setup() {
    }

    def cleanup() {
        CachingDriverFactory.clearCache()
    }

    void "test something"() {
        when:"The home page is visited"
            to UserListPage

        then:"The title is correct"
        	title == "Welcome to Grails"
    }
}
