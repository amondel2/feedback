package com.feedback

class RegisterCommand implements  grails.validation.Validateable {

    protected static Class<?> User
    protected static String usernamePropertyName

    String username
    String email
    String password
    String password2
    String employeeId
    String lastName
    String firstName
    Date hireDate = new Date()

    static constraints = {
        username validator: { value, command ->
            if (!value) {
                return
            }

            if (User.findWhere((usernamePropertyName): value)) {
                return 'registerCommand.username.unique'
            }
        }
        employeeId nullable: false
        firstName nullable: false
        lastName nullable: false
        hireDate nullable: false
        email email: true, validator: { value, command ->

            if(!value.toLowerCase().trim().endsWith('@foo.com')) {
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