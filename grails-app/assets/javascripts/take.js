$('#issueModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Button that triggered the modal
    $("#issueType").val(button.data('type'));
    $("#islbl").text(button.data('label'));
    $("#issue-text").val("");
    $("#issue-text").attr('placeholder',button.data('label'))
    $(this).find('.modal-title').text(button.data('title'));
});

$("#issueCreateBtn").on('click',function () {
    if($("#newIssueFrm")[0].reportValidity()) {
        $.ajax({
            url: window.fmBaseDir + 'createNewIssue',
            method: "POST",
            data: $("#newIssueFrm").serialize() ,
            cache: false
        }).done(function (data) {
            if (data.msg == "success") {
                var headId,bodyId,frmn;
                if( $("#issueType").val() == "Problem") {
                    bodyId = "ip";
                    headId = "iphead";
                    frmn = "issuefrm";
                } else {
                    bodyId = "iq";
                    headId = "iqhead";
                    frmn = "questfrm";
                }
                createIPQ(data.id,$("#issue-text").val(),bodyId + data.id,headId + data.id,frmn);
                $('#issueModal').modal('hide');
            } else {
                alert(data.msg);
            }
        });
    }
});

function createIPQ(id,text,bodyId,headId,frm) {
    let elm = `<div class="card">
            <div class="card-header" id="${headId}">
                 <h2 class="mb-0">
                    <button class="btn btn-link collapsed text-truncate" type="button" data-toggle="collapse" data-target="#${bodyId}" aria-expanded="false" aria-controls="${bodyId}">
                         ${text}
                        </button>
                </h2>
            </div>
            <div id="${bodyId}" class="collapse" aria-labelledby="${headId}" data-parent="#questfrm">
                <div class="card-body">
                </div>
            </div>
        </div>`;
    $("#" + frm).append(elm);
}