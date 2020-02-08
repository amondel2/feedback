package com.feedback

import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.servlet.mvc.SynchronizerTokensHolder
import org.springframework.beans.factory.InitializingBean

//import grails.validation.Validateable
class RegisterController implements InitializingBean {

	def springSecurityService
	GrailsApplication grailsApplication
	UserService userService
   def registrationService

//
	@Secured(['permitAll'])
	def index() {
		render(view:"createUser")
	}

	@Secured(['permitAll'])
	def createUser(RegisterCommand registerCommand) {

		withForm {
			if (!request.post) {
				return [registerCommand: new RegisterCommand()]
			}

			if (registerCommand.hasErrors()) {
				return [registerCommand: registerCommand]
			}

			if(userService.createUser(registerCommand)) {
				flash.message = "USER CREATED!, Please Login"
				redirect(controller: "login", action: "auth")
			}
		}.invalidToken {
			flash.message = "Invalid Form Submission"
			redirect(controller: "login", action: "auth")
		}

	}

	protected static int passwordMinLength
	protected static int passwordMaxLength
	protected static String passwordValidationRegex

	void afterPropertiesSet() {

		RegisterCommand.User = User
		RegisterCommand.usernamePropertyName = 'username'

		passwordMaxLength = grailsApplication.config.password.maxLength instanceof Number ? grailsApplication.config.password.maxLength : 64
		passwordMinLength = grailsApplication.config.password.minLength instanceof Number ? grailsApplication.config.password.minLength : 8
		passwordValidationRegex = grailsApplication.config.password.validationRegex ?: '^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$'
	}

	static final passwordValidator = { String password, command ->
		if (command.username && command.username.equals(password)) {
			return 'command.password.error.username'
		}

		if (!checkPasswordMinLength(password, command) || !checkPasswordMaxLength(password, command)) {
			return ['command.password.error.length', passwordMinLength, passwordMaxLength]
		}
		if (!checkPasswordRegex(password, command)) {
			return 'command.password.error.strength'
		}
	}

	static boolean checkPasswordMinLength(String password, command) {
		password && password.length() >= passwordMinLength
	}

	static boolean checkPasswordMaxLength(String password, command) {
		password && password.length() <= passwordMaxLength
	}

	static boolean checkPasswordRegex(String password, command) {
		password && password.matches(passwordValidationRegex)
	}

	static final password2Validator = { value, command ->
		if (command.password != command.password2) {
			return 'command.password2.error.mismatch'
		}
	}
	@Secured(['permitAll'])
	def showChanallage() {
		withForm {
			if(!params || !params.usernameForgot || !(params.usernameForgot?.trim().size() > 0)) {
				flash.message = "Missing UserName"
				redirect(controller: "login", action: "auth")
			} else {
				def user
				try {
					user = userService.getUserByUserName(params.usernameForgot?.trim())
				} catch (Exception e) {
					flash.message = e.getMessage()
					redirect(controller: "login", action: "auth")
					return true
				}
				if(!user) {
					flash.message = "Missing UserName"
					redirect(controller: "login", action: "auth")
				} else {
					render(view:"showChanallage",model:[usernameForgot:params.usernameForgot,eid:user.id])
				}
			}
		}.invalidToken {
			flash.message = "Invalid Form Submission"
			redirect(controller: "login", action: "auth")
		}
	}

	@Secured(['permitAll'])
	def resetPassword(ResetPasswordCommand resetPasswordCommand) {
		if (!request.post) {
			return [eid: params.eid, resetPasswordCommand: new ResetPasswordCommand()]
		}
		resetPasswordCommand.clearErrors()
		resetPasswordCommand.username = userService.getUserById(resetPasswordCommand.eid)?.username
		resetPasswordCommand.validate()
		if (resetPasswordCommand.hasErrors()) {
			return [eid: params.eid,resetPasswordCommand: resetPasswordCommand]
		}
		if(registrationService.updatePassword(resetPasswordCommand)) {
			flash.message = "User Updated!, Please Login"
			redirect(controller: "login", action: "auth")
		}
	}

	@Secured(['permitAll'])
	def renderForgot() {
		render(view:"resetPassword",model: [eid:params.eid])
	}

	@Secured(['permitAll'])
	def checkChallenge() {
		def qa
		withForm {
			try{
				qa = registrationService.getChallegneAnswers(params.eid)
			} catch (Exception e) {
				flash.message = "Profile Not Completed..Please contanct Site Admin for help"
				redirect(controller: "login", action: "auth")
			}
			if(params.question1.trim().size() > 4 && params.question1.trim().toLowerCase() == qa[0]?.trim()?.toLowerCase() && params.question2.trim().toLowerCase() == qa[1]?.trim()?.toLowerCase() ) {
				redirect(action:"renderForgot", controller: "register", params:[eid:params.eid])
			} else {
				def tokenurlnya = "/register/showChanallage"
				def tokensHolder = SynchronizerTokensHolder.store(session)
				flash.message = "One of More Answers Not Correct Please Correct Them."
				def model = [:]
				model[SynchronizerTokensHolder.TOKEN_KEY] = tokensHolder.generateToken(tokenurlnya)
				model[SynchronizerTokensHolder.TOKEN_URI]=tokenurlnya
				model['eid'] = params.eid
				model['usernameForgot'] = params.usernameForgot
				redirect(controller: "register", action: "showChanallage",  params:model)
			}
		}.invalidToken {
			flash.message = "Invalid Form Submission"
			try{
				def tokenurlnya = "/register/showChanallage"
				def tokensHolder = SynchronizerTokensHolder.store(session)
				def model = [:]
				model[SynchronizerTokensHolder.TOKEN_KEY] = tokensHolder.generateToken(tokenurlnya)
				model[SynchronizerTokensHolder.TOKEN_URI]=tokenurlnya
				model['eid'] = params.eid
				model['usernameForgot'] = params.usernameForgot
				redirect(controller: "register", action: "showChanallage",  params:model)
			} catch(Exception e) {
				redirect(controller: "login", action: "auth")
			}
		}
	}
}