package com.feedback

class UATSessionQuestions extends GemericDomainObject {
    UATSession session
    Question question
    int orderNumber

    static belongsTo = [session:UATSession,question:Question]

    static constraints = {
        orderNumber nullable: false
        question nullable: false
        session nullable: false
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }
}
