package com.feedback

class RegisterCommand implements  grails.validation.Validateable {

    protected static Class<?> User
    protected static String usernamePropertyName

    String username
    String email
    String password
    String password2

    static constraints = {
        username validator: { value, command ->
            if (!value) {
                return
            }

            if (User.findWhere((usernamePropertyName): value)) {
                return 'registerCommand.username.unique'
            }
        }
        email email: true, validator: { value, command ->

            if(!value.toLowerCase().trim().endsWith('@reedtech.com')) {
                return 'reedtech.email.required'
            }

            if (User.findWhere(('email'): value)) {
                return 'registerCommand.username.unique'
            }
        }
        password validator: RegisterController.passwordValidator
        password2 nullable: true, validator: RegisterController.password2Validator
    }
}