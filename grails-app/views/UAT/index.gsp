<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.feedback.UATCommand" %>
<%@ page import="com.feedback.UATQuestionCommand" %>
<%@ page import="com.feedback.UATAnswerCommand" %>
<%@ page import="com.feedback.QuestionType" %>
<html>
<head>
    <meta name="layout" content="basic"/>
    <title>${res.title}</title>
</head>
<body>
    <h1>${res.title}</h1>
    <div id="mainfrm">
        <g:each in="${res.questions}" var="q" status="i">
            <div>
                <h3>Question #${i + 1}</h3>
                <g:if test="${q.type == QuestionType.Likert}">
                    <g:render template="/common/likert" model="[q:q]" />
                </g:if>
                <g:elseif test="${q.type == QuestionType.MultiChoice}">
                    <g:render template="/common/multiChoice" model="[q:q]" />
                </g:elseif>
                <g:elseif test="${q.type == QuestionType.Open}">
                    <g:render template="/common/open" model="[q:q]" />
                </g:elseif>
                <g:elseif test="${q.type == QuestionType.CheckBox}">
                    <g:render template="/common/checkbox" model="[q:q]" />
                </g:elseif>
                <g:elseif test="${q.type == QuestionType.Dropdown}">
                    <g:render template="/common/dropdown" model="[q:q]" />
                </g:elseif>
            </div>
        </g:each>
    </div>
    <div class="m-3">
        <button class="btn btn-primary">Complete</button>
        <button class="btn btn-secondary">Save for Later</button>
    </div>
</body>
</html>