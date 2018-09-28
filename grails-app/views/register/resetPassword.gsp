<html>
	<head>
		<<meta name="layout" content="basic">
		<s2ui:title messageCode='spring.security.ui.resetPassword.title'/>
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
			<s2ui:form  type='resetPassword' focus='password'>
			<fieldset class="form">
				<g:hiddenField name='t' value='${token}'/>
				<h3><g:message code='spring.security.ui.resetPassword.description'/></h3>
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
			</s2ui:form>
	</body>
</html>