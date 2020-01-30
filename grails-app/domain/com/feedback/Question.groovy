package com.feedback

class Question extends GemericDomainObject  {

    static constraints = {
        questionType nullable: false
        question nullable: false
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    static hasMany = [answers:Answer]

    QuestionType questionType
    String question
    Boolean requiresReset = false


}
