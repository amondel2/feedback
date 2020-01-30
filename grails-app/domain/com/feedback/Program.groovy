package com.feedback

class Program extends  GemericDomainObject {

    static constraints = {
        name nullable: false, blank: false
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    static hasMany = [uatSessions:UATSession]

    String name

}
