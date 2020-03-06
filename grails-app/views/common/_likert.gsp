<div>
    <table id="liker${q.id}" class="table table-bordered table-primary">
        <thead>
            <tr>
                <th></th>
                <g:each in="${q.answers}" var="a">
                <th>${a.text}</th>
                </g:each>
            </tr>
        </thead>
        <tbody>
        <tr class="centered align-content-center">
        <td class="text-capitalize font-weight-bold">${q.text}</td>
        <g:each in="${q.answers}" var="a">
            <td class="centered"> <input <g:if test="${q.isRequired}"> required </g:if>  class="custom-radio" type="radio" name="${q.id}" value="${a.id}" <g:if test="${a.id == a.response}"> selected="selected"</g:if> /></td>
        </g:each>
        </tr>
        </tbody>
    </table>
</div>