package com.feedback

class UATSessionQuestions extends GemericDomainObject {
    UATSession session
    Question question

    static belongsTo = [session:UATSession,question:Question]

    static constraints = {
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }
}
