<div>
    <g:each in="${q.answers}" var="a">
        <div>
            <input <g:if test="${q.isRequired}"> required </g:if>  class="custom-radio" type="checkbox" id="mc${a.id}" name="${q.id}" value="${a.id}" <g:if test="${a.id == a.response}"> checked="checked"</g:if> />
            <label  for="mc${a.id}">${a.text}</label>
        </div>
    </g:each>
</div>