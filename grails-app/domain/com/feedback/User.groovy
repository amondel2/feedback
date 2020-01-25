package com.feedback

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'firstName,lastName', includeNames = false, includePackage = false)
class User extends GemericDomainObject {

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String restToken
    String email
    String employeeId
    String firstName
    String lastName
    Date hireDate
    Date endDate
    Boolean manager = false


    String getFullName(){
        "${this.firstName} ${this.lastName}"
    }

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static hasMany = [urs:UserRole, bosses:UserBoss, employees:UserBoss,jobs:UserJob]
    static mappedBy = [bosses:'employee',employees:'boss']

    static constraints = {
        id nullable: false, unique: true
        password nullable: false, blank: false, password: true, display: false
        username nullable: false, blank: false, unique: true
        restToken nullable: true, blank: true, unique: true, display: false
        email nullable: false, blank: false, email: true, unique: true, validator: { String val ->
            return (val.trim().toLowerCase().endsWith('@foo.com'))
        }
       firstName nullable:false,blank:false
       employeeId nullable:false,blank:false,unique:true
       lastName nullable:false,blank:false
       hireDate nullable:false
       endDate nullable:true
    }

    static mapping = {
        id generator: 'assigned'
        password column: '`password`'
    }
}
