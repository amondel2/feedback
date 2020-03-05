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

    @Override
    String toString() {
        return question
    }

    static hasMany = [answers:Answer,sessions:UATSessionQuestions]

    QuestionType questionType
    String question
    Boolean isRequired = true
    Boolean requiresReset = false


}
