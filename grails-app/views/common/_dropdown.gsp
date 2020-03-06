<div>
    <h3><label for="sel${q.id}">${q.text}</label></h3>
</div>
<div>
    <select id="sel${q.id}" name="${q.id}" <g:if test="${q.isRequired}"> required </g:if>>
    <g:each in="${q.answers}" var="a">
        <option value="${a.id}" <g:if test="${a.id == a.response}"> selected="selected"</g:if>>${a.text}</option>
    </g:each>
</select>
</div>
