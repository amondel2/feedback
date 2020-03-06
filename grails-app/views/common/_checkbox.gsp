<div>
    <h3>${q.text}</h3>
    <g:each in="${q.answers}" var="a">
        <div> <label  for="mc${a.id}">${a.text}</label>
            <input <g:if test="${q.isRequired}"> required </g:if>  class="custom-radio" type="checkbox" id="mc${a.id}" name="${q.id}" value="${a.id}" <g:if test="${a.id == a.response}"> selected="selected"</g:if> />
        </div>
    </g:each>
</div>