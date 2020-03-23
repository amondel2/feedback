package com.feedback

class ProgramVersionUATSessions extends  GemericDomainObject {

    static constraints = {
        programVersion nullable: false
        uatSession nullable: false

    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    @Override
    String toString() {
        return "${uatSession.toString()}  for ${programVersion.toString()}"
    }

    static belongsTo = [programVersion:ProgramVersion,uatSession:UATSession]

    ProgramVersion programVersion
    UATSession uatSession

}
