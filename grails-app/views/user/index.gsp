<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="basic" />
        <asset:stylesheet src="main.css"/>
        <g:set var="entityName" value="${message(code: 'user.label', default: 'Employee')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><a class="home" href="${request.contextPath}/user/importEmployee">Import Employees</a></li>

                <li><a class="home" href="${request.contextPath}/user/exportUsers">Export Users</a></li>
            </ul>
        </div>
        <div id="list-profile" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                <tr>
                    <g:each in="${['id','firstName','lastName','employeeId','restToken']}" var="p" status="i">
                        <g:set var="propTitle">${'user'}.${p}.label</g:set>
                        <g:sortableColumn property="${p}" title="${message(code: propTitle, default: p)}" />
                    </g:each>

                </tr>
                </thead>
                <tbody>
                <g:each in="${userList}" var="bean" status="i">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <g:each in="${['id','firstName','lastName','employeeId','restToken']}" var="p" status="j">
                           <td>
                            <g:if test="${j==0}">
                                <g:link method="GET" resource="${bean}"><f:display bean="${bean}" property="${p}" /></g:link>
                            </g:if>
                            <g:else>
                                <g:if test="${j==4}"><button name="resetToken" empid="${bean.id}">Create Reset Token</button><span></g:if>
                                <f:display bean="${bean}" property="${p}" />
                                <g:if test="${j==4}"> </span></g:if>
                            </g:else>
                            </td>
                        </g:each>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <g:if test="${userCount > 10}">
                <div class="pagination">
                    <g:paginate total="${userount ?: 0}" />
                </div>
            </g:if>
        </div>
    <script>
        $("button[name='resetToken']").on('click',function(){
            var that = this;
           $.get("${request.contextPath}/user/generateResetToken?emp=" + $(this).attr('empid')).then(function(data) {
                $(that).parent().find('span').html(data.token );
           })
        });
    </script>
    </body>
</html>