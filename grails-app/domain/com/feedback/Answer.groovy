package com.feedback

class Answer extends GemericDomainObject {

    static constraints = {
        answer nullable: false, blank: false
        question nullable: false
        orderNumber nullable: false
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    @Override
    String toString() {
        return answer
    }

    static belongsTo = [question:Question]

    String answer
    Question question
    int orderNumber

}
