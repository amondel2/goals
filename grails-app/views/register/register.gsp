<html>
	<head>
		<meta name="layout" content="basic">
		<s2ui:title messageCode='spring.security.ui.register.title'/>
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
			<s2ui:form type='register' focus='username'>
				<fieldset class="form">
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
					<g:submitButton name="create"  class="btn btn-primary btn-sm active" value="${message(code: 'spring.security.ui.register.submit', default: 'Create')}" />
				</fieldset>
			</s2ui:form>
	</body>
</html>
