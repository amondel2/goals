<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic"/>
    <script>
        fmBaseDir = '/${controllerName}/';

    </script>

</head>
<body>
<h1>Employee Import Sheet</h1>
<div>${flash.message}</div>
<div>${flash.errors}</div>
<li><a class="home" href="/employees/importTemplate">Import Sheet Template</a></li>
<g:uploadForm name="uploadFeaturedImage" action="employeeFileUpload">

    Check to Append: <input type="checkbox" name="append" value="append" checked="true" />
    <input type="file" name="employeeFile" />
    <fieldset class="buttons">
        <input class="save" type="submit" value="Upload" />
    </fieldset>
</g:uploadForm>
</body>
</html>
