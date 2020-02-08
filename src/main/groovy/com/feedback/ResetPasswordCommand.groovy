package com.feedback

class ResetPasswordCommand implements grails.validation.Validateable {
    String eid
    String username
    String password
    String password2

    static constraints = {
        eid nullable: false, blank: false
        password validator: RegisterController.passwordValidator
        password2 nullable: true, validator: RegisterController.password2Validator
        username nullable: false
    }
}
