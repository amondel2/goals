<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="basic">
        <g:set var="entityName" value="${message(code: 'profile.label', default: 'Profile')}" />
        <title>Create ${entityName}</title>
        <script type="text/javascript">
         var redirectURL = '<g:resource dir="employees" file="createFromUser" absolute="true" />';
          redirectURL = redirectURL.replace('static/','');
        </script>
    </head>
    <body>

        <div id="edit-profile" class="content scaffold-edit" role="main">
            <h1>Create ${entityName}</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:each in="${userInstanceList}" status="i" var="propertyName">
                <div style="margin:0.2em;"><button class="btn btn-primary btn-sm active" id="${propertyName.id}" >Create Profile</button> ${propertyName.username}</div>
            </g:each>
        </div>
    <asset:javascript src="employees/createmissprofile.js" />
</body>
</html>