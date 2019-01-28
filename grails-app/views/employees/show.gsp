<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="basic" />
        <asset:stylesheet src="main.css"/>
        <g:set var="entityName" value="${message(code: 'employees.label', default: 'Profile')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>


    </head>
    <body>

        <sec:ifAnyGranted roles="ROLE_ADMIN">
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>

            </ul>
        </div>
        </sec:ifAnyGranted >
        <div id="show-profile" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="employees" except="restToken,user,bosses,employees,goals,company,manager" />


            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.employees}"><g:message code="default.button.edit.label" default="Edit" /></g:link>

                        <g:form resource="${this.employees}" method="DELETE">
                         <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                         </g:form>

                </fieldset>
            </sec:ifAnyGranted >
        </div>
    </body>
</html>
