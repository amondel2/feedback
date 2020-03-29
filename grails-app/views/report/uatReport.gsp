<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="basic"/>
    <title>UAT Reports</title>
    <asset:javascript src="jquery.tablesorter.min.js" />
    <asset:javascript src="jquery.tablesorter.widgets.min.js" />
    <asset:stylesheet src="theme.bootstrap_4.css" />
    <script>
        fmBaseDir = '${request.contextPath}/${controllerName}/';
    </script>
</head>
<body>

    <form METHOD="post" name="searchfrm" id="searchfrm"onsubmit="return false;">
        <label for="title">Enter Title</label><input type="text" placeholder="Enter Title" id="title" name="title">
        <label for="program">Enter Program Name</label><input type="text" placeholder="Enter Program Name" id="program" name="program">
        <button id="searchBrn" class="btn btn-primary">Search</button>
    </form>

    <div id="uatstatus"></div>
    <asset:javascript src="report.js" />
    <asset:javascript src="uatreport.js" />
</body>
</html>