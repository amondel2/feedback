<div>
    <table id="liker${q.id}" class="table table-bordered table-primary">
        <thead>
            <tr>
                <g:each in="${q.answers}" var="a">
                    <th><label for="a${a.id}">${a.text}</label></th>
                </g:each>
            </tr>
        </thead>
        <tbody>
        <tr class="centered align-content-center">
        <g:each in="${q.answers}" var="a">
            <td class="centered"> <input id="a${a.id}" <g:if test="${q.isRequired}"> required </g:if>  class="custom-radio" type="radio" name="${q.id}" value="${a.id}" <g:if test="${a.id == a.response}"> selected="selected"</g:if> /></td>
        </g:each>
        </tr>
        </tbody>
    </table>
</div>