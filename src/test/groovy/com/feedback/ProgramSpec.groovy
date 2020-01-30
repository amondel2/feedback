package com.feedback

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProgramSpec extends Specification implements DomainUnitTest<Program> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        setup:
            domain.name = "trey burot"
        expect:
        domain.name == "trey burot"
    }
}
