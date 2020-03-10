package com.feedback

class UserUATResponse extends GemericDomainObject {

    UserUats userUats
    UATSessionQuestions question
    Answer singleAnswer
    String textAnswer

    static constraints = {
        singleAnswer nullable: true
        textAnswer nullable: true
        question nullable: false, unique: ['userUats']
        userUats nullable: false, unique: ['question']
    }

    static mapping = {
        textAnswer type: 'text'
        id generator: 'assigned'
        version false
    }
}