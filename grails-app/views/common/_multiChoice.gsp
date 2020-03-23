<div>
    <g:each in="${q.answers}" var="a">
        <label  for="mc${a.id}">  <div class="float-left btn btn-primary ui-controlgroup-vertical m-1 text-nowrap">
            <input <g:if test="${q.isRequired}"> required </g:if>  class="custom-radio" type="radio" id="mc${a.id}" name="${q.id}" value="${a.id}" <g:if test="${a.id == a.response}"> checked="checked"</g:if> />
               ${a.text}
            </div>
        </label>
    </g:each>
</div>
<div class="clearfix"> </div>