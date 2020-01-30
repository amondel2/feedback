package com.feedback

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes = 'authority')
@ToString(includes = 'authority', includeNames = true, includePackage = false)
class Role extends GemericDomainObject {


    String authority

    static constraints = {
        authority nullable: false, blank: false, unique: true
    }

    static hasMany = [urs:UserRole]

    static mapping = {
        id generator: 'assigned'
        authority cache:  true
        id cache: true
    }
}
