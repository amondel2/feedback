<%--
  Created by IntelliJ IDEA.
  User: aaron
  Date: 1/14/20
  Time: 7:54 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="basic"/>
    <script>
        fmBaseDir = '${request.contextPath}/${controllerName}/';
    </script>
    <asset:javascript src="jquery.tablesorter.min.js" />
    <asset:javascript src="jquery.tablesorter.widgets.min.js" />
    <asset:stylesheet src="theme.bootstrap_4.css" />
    <title></title>
</head>

<body>
<h1>Idea Submission</h1>
<p class="lead">
<g:if test='${flash.message}'>
    <div class='login_message'>${flash.message}</div>
</g:if>

<sec:ifNotLoggedIn>
    Please login
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
    <h2>Dashboard</h2>
    <div>
        <h3>New Ideas</h3>
        <div id="newIdeas">Loading .. <asset:image src="spinner.gif" /></div>

        <h3>My Ideas</h3>
        <div id="myIdeas">Loading .. <asset:image src="spinner.gif" /></div>

    </div>
</sec:ifLoggedIn>
<br>

</p>

<g:if test="${param?.autologout}">
    <script type="text/javascript">
        $(document).ready(function(){
            $("#logout").trigger("click");
        });
    </script>
</g:if>
<sec:ifLoggedIn>
    <asset:javascript src="home.js" />
</sec:ifLoggedIn>
</body>
</html>