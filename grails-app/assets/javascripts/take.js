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
                if( $("#issueType").val() == "Problem")
                    createIP(data.id,$("#issue-text").val());
                else
                    createIQ(data.id,$("#issue-text").val());
                $('#issueModal').modal('hide');
            } else {
                alert(data.msg);
            }
        });
    }
});

function createIQ(id,text) {
    let elm = `<div class="card">
            <div class="card-header" id="iqhead${id}">
                 <h2 class="mb-0">
                    <button class="btn btn-link collapsed text-truncate" type="button" data-toggle="collapse" data-target="#iq${id}" aria-expanded="false" aria-controls="iq${id}">
                         ${text}
                        </button>
                </h2>
            </div>
            <div id="iq${id}" class="collapse" aria-labelledby="iqhead${id}" data-parent="#questfrm">
                <div class="card-body">
                </div>
            </div>
        </div>`;
    $("#questfrm").append(elm);
}

function createIP(id,text) {
    let elm = `<div class="card">
            <div class="card-header" id="iphead${id}">
                 <h2 class="mb-0">
                    <button class="btn btn-link collapsed text-truncate" type="button" data-toggle="collapse" data-target="#ip${id}" aria-expanded="false" aria-controls="ip${id}">
                         ${text}
                        </button>
                </h2>
            </div>
            <div id="ip${id}" class="collapse" aria-labelledby="iphead${id}" data-parent="#issuefrm">
                <div class="card-body">
                </div>
            </div>
        </div>`;
    $("#issuefrm").append(elm);
}