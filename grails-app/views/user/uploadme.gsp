<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic"/>
    <script>
        fmBaseDir = '${request.contextPath}/${controllerName}/';

    </script>

</head>
<body>
<h1>Employee Import Sheet</h1>
<div>${flash.message}</div>
<div>${flash.errors}</div>
<li><a class="home" href="${request.contextPath}/user/importTemplate">Import Sheet Template</a></li>
<g:uploadForm name="uploadFeaturedImage" action="employeeFileUpload">

    Check to Append: <input type="checkbox" name="append" value="append" checked="true" />
    <input type="file" name="employeeFile" />
    <fieldset class="buttons">
        <input class="save" type="submit" value="Upload" />
    </fieldset>
</g:uploadForm>
</body>
</html>
