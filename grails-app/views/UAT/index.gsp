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
    <div class="accordion" id="mainfrm">
        <g:each in="${res.questions}" var="q" status="i">
            <div class="card">
                <div class="card-header" id="qhead${i}">
                    <h2 class="mb-0">
                        <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#q${i}" aria-expanded="false" aria-controls="q${i}">
                           Question #${i + 1}: ${q.text}
                        </button>
                    </h2>
                </div>
                <div id="q${i}" class="collapse" aria-labelledby="qhead${i}" data-parent="#mainfrm">
                    <div class="card-body">
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
                </div>
            </div>
        </g:each>
    </div>

    <h3>Get Answers to Questions</h3>
     <button id="myquestions" class="btn-secondary btn">Ask A New Question</button>
        <div class="accordion" id="questfrm">
            <g:each in="${res.issuesQuestions}" var="q" status="i">
                <div class="card">
                    <div class="card-header" id="iqhead${q.id}">
                        <h2 class="mb-0">
                            <button class="btn btn-link collapsed text-truncate" type="button" data-toggle="collapse" data-target="#iq${q.id}" aria-expanded="false" aria-controls="iq${q.id}">
                                ${q.issueDescription}
                            </button>
                        </h2>
                    </div>
                    <div id="iq${q.id}" class="collapse" aria-labelledby="iqhead${q.id}" data-parent="#questfrm">
                        <div class="card-body">
                              ${q.issueResponse}
                        </div>
                    </div>
                </div>
            </g:each>
        </div>

    <h3>Report an a Issue</h3>
    <button id="myissues" class="btn-secondary btn">Report A New Issue</button>
    <div class="accordion" id="issuefrm">
        <g:each in="${res.issuesProblems}" var="q" status="i">
            <div class="card">
                <div class="card-header" id="iphead${q.id}">
                    <h2 class="mb-0">
                        <button class="btn btn-link collapsed text-truncate" type="button" data-toggle="collapse" data-target="#ip${q.id}" aria-expanded="false" aria-controls="ip${q.id}">
                            ${q.issueDescription}
                        </button>
                    </h2>
                </div>
                <div id="ip${q.id}" class="collapse" aria-labelledby="iphead${q.id}" data-parent="#issuefrm">
                    <div class="card-body">
                        ${q.issueResponse}
                    </div>
                </div>
            </div>
        </g:each>
    </div>

    <div class="m-3">
        <button class="btn btn-primary">Complete</button>
        <button class="btn btn-secondary">Save for Later</button>
    </div>
</body>
</html>