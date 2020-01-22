<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>New Account Creation</title>
</head>

<body>
<h1>Register</h1>
<g:hasErrors bean="${registerCommand}">
    <ul class="errors" role="alert">
        <g:eachError bean="${registerCommand}" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>
<g:form autofocus="username" method="post" useToken="true" action="createUser">
    <fieldset class="form">

        <div class="form-group ${hasErrors(bean: registerCommand, field: 'firstName', 'has-error')} required">
            <label for="firstName">First Name <span  class="required-indicator">*</span></label>
            <input type="text" name="firstName" id="firstName" class="form-control" required="" value="${registerCommand?.firstName}" />
        </div>


        <div class="form-group ${hasErrors(bean: registerCommand, field: 'lastName', 'has-error')} required">
            <label for="lastName">Last Name <span  class="required-indicator">*</span></label>
            <input type="text" name="lastName" id="lastName" class="form-control" required="" value="${registerCommand?.lastName}" />
        </div>


        <div class="form-group ${hasErrors(bean: registerCommand, field: 'employeeId', 'has-error')} required">
            <label for="employeeId">Employee Id <span  class="required-indicator">*</span></label>
            <input type="text" name="employeeId" id="employeeId" class="form-control" required="" value="${registerCommand?.employeeId}" />
        </div>


        <div class="form-group required  ${hasErrors(bean: registerCommand, field: 'username', 'has-error')}">
            <label for="username">Username <span  class="required-indicator">*</span></label>
            <g:textField name="username" id="username" class="form-control" required="" value="${registerCommand?.username}" />

        </div>
        <div class="form-group required ${hasErrors(bean: registerCommand, field: 'email', 'has-error')}">
            <label for="email">E-mail <span  class="required-indicator">*</span></label>
            <g:textField name="email" id="email" class="form-control" required="" value="${registerCommand?.email}" />

        </div>
        <div class="form-group ${hasErrors(bean: registerCommand, field: 'password', 'has-error')} required">
            <label for="password">Password (Must have at least one letter, number, and special character: !@#$%^&) <span  class="required-indicator">*</span></label>
            <input type="password" name="password" id="password" class="form-control" required="" value="" />

        </div>
        <div class="form-group ${hasErrors(bean: registerCommand, field: 'password2', 'has-error')} required">
            <label for="password2">Password (again) <span  class="required-indicator">*</span></label>
            <input type="password" name="password2" id="password2" class="form-control" required="" value="" />
        </div>
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="create"  class="btn btn-primary btn-sm active" value="${message(code: 'default.button.create.label', default: 'Create')}" />
    </fieldset>
</g:form>
</body>
</html>