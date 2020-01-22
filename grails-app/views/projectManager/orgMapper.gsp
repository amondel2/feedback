<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="basic"/>
                <script>
                    fmBaseDir = '${request.contextPath}/${controllerName}/';
                    var fmGoalsDir = '${request.contextPath}/goals/';
                    fmCompanyName = '${companyName}';
                    var fmBossId = '${boss?.id}';
                    var fmBossName = '${boss.toString()}';
                    fmSuccessMsg = '<g:message code="page.message.success" encodeAs="JavaScript" />';
                    fmErrorMsg = '<g:message code="page.message.error" encodeAs="JavaScript" />';
                    fmConfirmMsg= '<g:message code="page.button.confirm" encodeAs="JavaScript" />';
                    fmCancelMsg = '<g:message code="page.button.cancel" encodeAs="JavaScript" />';
                    fmProblemMsg = '<g:message code="page.error.FatalError" encodeAs="JavaScript" />';
                    fmLoadingMsg = '<g:message code="page.status.loading" encodeAs="JavaScript" />';
                    fmHasWritePermission = true;
                    fmHasImportExportPermission = false;
                    fmSaveMsg = '<g:message code="page.button.Save" encodeAs="JavaScript" />';
                </script>
                <asset:javascript src="jstree-min.js" />
                <asset:javascript src="OrgMapUI.js" />
                <asset:stylesheet src="mondelMapperUI.css" />
                <asset:stylesheet src="themes/default/style.min.css" />
	</head>
	<body>
		<h1>Org Management</h1>
       <div class="fm-content">
        <div class="fm-message"></div>
        <div class="fm-container fm-border">
            <div class="fm-left-panel fm-border">
                <div class="fm-left-tree-container">
                    <div id="jstree"></div>
                </div>
            </div>
        </div>
    </div>
	</body>
</html>