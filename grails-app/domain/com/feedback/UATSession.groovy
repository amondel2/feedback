package com.feedback

class UATSession extends GemericDomainObject {

    static constraints = {
        title nullable: false, blank: false
        program nullable: false
        endDate nullable: true
        startDate nullable: false, validator: { String val ->
            return (val >= new Date())
        }

    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    static hasMany = [users:UserUats,issues:Issue,questions:UATSessionQuestions]
    static belongsTo = [program:Program]

    Date startDate
    Date endDate
    Program program
    String title

    @Override
    String toString() {
        this.title
    }

}
