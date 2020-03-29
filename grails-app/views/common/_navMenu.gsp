<sec:ifLoggedIn>
    <sec:ifAnyGranted roles="ROLE_REPORTER,ROLE_ADMIN,ROLE_UAT_ADMIN">
        <g:render template="/common/reportMenu" />
    </sec:ifAnyGranted>
    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <g:render template="/common/adminMenu" />
    </sec:ifAnyGranted>
    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_UAT_ADMIN">
        <g:render template="/common/UATADMINMENU" />
    </sec:ifAnyGranted>
</sec:ifLoggedIn>