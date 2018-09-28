<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="basic" />
        <asset:stylesheet src="main.css"/>
        <g:set var="entityName" value="${message(code: 'profile.label', default: 'Employee')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-profile" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.employees}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.employees}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.employees}" method="POST">
                <fieldset class="form">
                    <f:all bean="employees" except="user,bosses,employees,employeeJobs,employeeJobRolesPercents,restToken" />
                     <sec:ifAnyGranted roles="ROLE_ADMIN">
                                        <div class="form-group">
                                            <label for="User">
                                                Select a User
                                            </label>
                                            <g:select name="user" from="${hl}" value="${employees?.user?.id}" optionKey="id" noSelection="['':'-No User-']"   />
                                        </div>
                     </sec:ifAnyGranted>
                     <input type="hidden" style="display:none" id="user" name="user.id" required="" value="${employees.user ? employees.user?.id : user.id }" />
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
