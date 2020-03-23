package com.feedback

class ProgramVersion extends  GemericDomainObject {

    static constraints = {
        versionNumber nullable: false, blank: false, unique: 'program'
        program nullable: false
        passed nullable: true
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    static belongsTo = [program:Program]
    static hasMany = [uats:ProgramVersionUATSessions]

    @Override
    String toString() {
        return "${program.toString()} ${versionNumber}"
    }

    Date createDate = new Date()
    Program program
    String versionNumber
    Boolean passed
}