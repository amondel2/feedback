package com.feedback

class ProgramVersion extends  GemericDomainObject {

    static constraints = {
        versionNumber nullable: false, blank: false, unique: 'program'
        program nullable: false
        passed nullable: true
        uatSession nullable: true
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    static belongsTo = [program:Program,uatSession:UATSession]

    @Override
    String toString() {
        return "${program.toString()} ${versionNumber}"
    }

    Date createDate = new Date()
    UATSession uatSession
    Program program
    String versionNumber
    Boolean passed
}