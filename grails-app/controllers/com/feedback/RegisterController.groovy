package com.feedback

import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.InitializingBean

//import grails.validation.Validateable
class RegisterController implements InitializingBean {

	def springSecurityService
	GrailsApplication grailsApplication
	UserService userService
//    def registrationService

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
//
//	def showChanallage() {
//
//		withForm {
//			if(!params || !params.usernameForgot || !(params.usernameForgot?.trim().size() > 0)) {
//				flash.message = "Missing UserName"
//				redirect(controller: "login", action: "auth")
//			} else {
//				//makeSure User Exists
//				def userName = params?.usernameForgot?.trim()
//				def user
//				try {
//					user = User.withCriteria(uniqueResult: true){
//						eq("username", userName, [ignoreCase: true])
//					}
//				} catch (NonUniqueResultException e) {
//					user = User.withCriteria(uniqueResult: true){
//						eq("username", userName)
//					}
//				} catch (Exception e) {
//					flash.message = e.getMessage()
//					redirect(controller: "login", action: "auth")
//					return true
//				}
//
//				if(!user) {
//					flash.message = "Missing UserName"
//					redirect(controller: "login", action: "auth")
//				} else {
//					Employees emp = Employees.findByUser(user)
//					render(view:"showChanallage",model:[emp:emp])
//
//				}
//
//
//			}
//
//		}.invalidToken {
//			flash.message = "Invalid Form Submission"
//			redirect(controller: "login", action: "auth")
//		}
//	}
//
//	@Override
//	def verifyRegistration() {
//
//		String token = params.t
//
//		RegistrationCode registrationCode = token ? RegistrationCode.findByToken(token) : null
//		if (!registrationCode) {
//			flash.error = message(code: 'spring.security.ui.register.badCode')
//			redirect uri: successHandlerDefaultTargetUrl
//			return
//		}
//
//		def user = uiRegistrationCodeStrategy.finishRegistration(registrationCode)
//
//		if (!user) {
//			flash.error = message(code: 'spring.security.ui.register.badCode')
//			redirect uri: successHandlerDefaultTargetUrl
//			return
//		}
//
//		if (user.hasErrors()) {
//			// expected to be handled already by ErrorsStrategy.handleValidationErrors
//			return
//		}
//		def r = registerPostRegisterUrl ?: successHandlerDefaultTargetUrl
//		flash.message = message(code: 'spring.security.ui.register.complete')
//		springSecurityService.reauthenticate user.username
//		redirect uri: "/profile/create" //+ "?autologout=true"
//	}
//
//	@Transactional
//	def checkChallenge() {
//		Employees emp
//		def qa
//		withForm {
//			try{
//				def eid = params.eid
//				emp = Employees.get(eid)
//				qa = [emp.restToken,emp.employeeId]
//			} catch (Exception e) {
//				flash.message = "Profile Not Completed..Please contanct Site Admin for help"
//				redirect(controller: "login", action: "auth")
//			}
//			if(params.question1.trim().size() > 4 && params.question1.trim().toLowerCase() == qa[0]?.trim()?.toLowerCase() && params.question2.trim().toLowerCase() == qa[1]?.trim()?.toLowerCase() ) {
//				registrationService.removeLoginToken(emp)
//				RegistrationCode registrationCode = registrationService.getForgotPassLink(emp)
//				String url = generateLink('resetPassword', [t: registrationCode.token])
//				redirect(url: url)
//			} else {
//				def tokenurlnya = "/register/showChanallage"
//				def tokensHolder = SynchronizerTokensHolder.store(session)
//				flash.message = "One of More Answers Not Correct Please Correct Them."
//				def model = [:]
//				model[SynchronizerTokensHolder.TOKEN_KEY] = tokensHolder.generateToken(tokenurlnya)
//				model[SynchronizerTokensHolder.TOKEN_URI]=tokenurlnya
//				model['usernameForgot'] = emp.user.username
//				redirect(controller: "register", action: "showChanallage",  params:model)
//			}
//
//		}.invalidToken {
//			flash.message = "Invalid Form Submission"
//			try{
//				def eid = params.eid
//				emp = Employees.get(eid)
//
//				def tokenurlnya = "/register/showChanallage"
//				def tokensHolder = SynchronizerTokensHolder.store(session)
//				def model = [:]
//				model[SynchronizerTokensHolder.TOKEN_KEY] = tokensHolder.generateToken(tokenurlnya)
//				model[SynchronizerTokensHolder.TOKEN_URI]=tokenurlnya
//				model['usernameForgot'] = emp?.user.username
//				redirect(controller: "register", action: "showChanallage",  params:model)
//			} catch(Exception e) {
//				redirect(controller: "login", action: "auth")
//			}
//		}
//	}
}