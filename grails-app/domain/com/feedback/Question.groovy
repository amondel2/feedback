package com.feedback

class Question extends GemericDomainObject  {

    static constraints = {
        questionType nullable: false
        question nullable: false, blank: false, minSize: 5
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    static hasMany = [answers:Answer,sessions:UATSessionQuestions]

    QuestionType questionType
    String question
    Boolean requiresReset = false


}
