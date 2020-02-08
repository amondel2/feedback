<html>
	<head>
		<<meta name="layout" content="basic">
		<title>Create New password</title>
	</head>
	<body>
		<h1>Reset Password</h1>
		<g:hasErrors bean="${resetPasswordCommand}">
			<ul class="errors" role="alert">
				<g:eachError bean="${resetPasswordCommand}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action='resetPassword' focus='password'>
			<fieldset class="form">
				<g:hiddenField name="eid" id="eid" value="${eid}" />
				<h3><g:message code='spring.security.ui.resetPassword.description' default="Reset Password"/></h3>
				<div class="form-group ${hasErrors(bean: resetPasswordCommand, field: 'password', 'has-error')} required">
					<label for="password">Password <span  class="required-indicator">*</span></label>
					<input type="password" name="password" id="password" class="form-control" required="" value="" />
					
					</div>
					<div class="form-group ${hasErrors(bean: resetPasswordCommand, field: 'password2', 'has-error')} required">
					<label for="password2">Password (again) <span  class="required-indicator">*</span></label>
					<input type="password" name="password2" id="password2" class="form-control" required="" value="" />
					
					</div>
				</fieldset>
			    <fieldset class="buttons">
					<g:submitButton name="create"  class="btn btn-primary btn-sm active" value="${message(code: 'spring.security.ui.resetPassword.submit', default: 'Reset')}" />
				</fieldset>
			</g:form>
	</body>
</html>