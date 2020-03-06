<div>
    <g:each in="${q.answers}" var="a">
        <textarea <g:if test="${q.isRequired}"> required </g:if> class="resize"  rows="20" cols="100" spellcheck="true" name="${q.id}" id="open${q.id}" placeholder="${a.text}">${a.response}</textarea>
    </g:each>
</div>