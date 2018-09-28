<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="basic">
<g:set var="entityName"	value="${message(code: 'employee.label', default: 'Reset Questions')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
	<div id="edit-profile" class="content scaffold-edit" role="main">
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<g:form controller='register' action='checkChallenge' useToken="true">
			<fieldset class="form">
			<g:hiddenField name="eid" value="${emp.id}" />
			<div class="form-group required">
				<label for="question1">What is your Reset Token?<span  class="required-indicator">*</span></label>
				<g:textField name="question1" id="question1" class="form-control" required=""
					value="" />
			</div>
			<div class="form-group required">
				<label for="question2">What is your Employee Id?<span  class="required-indicator">*</span></label>
				<g:textField name="question2" id="question2" class="form-control" required=""
					value="" />
			</div>
			</fieldset>
			<fieldset class="buttons">
					<input type="submit" class="btn btn-primary btn-sm active" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:link class="btn btn-secondary btn-sm active" controller="login" action="auth"><g:message code="default.button.cancel.label" default="Cancel" /></g:link>
			</fieldset>
		</g:form>
	</div>
</body>
</html>