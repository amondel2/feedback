const sleep = (milliseconds) => {
    return new Promise(resolve => setTimeout(resolve, milliseconds))
};

$(window).on('beforeunload',function(){
    return "Are you sure you want to leave this page? Any unsaved information will be lost!";
});

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

const cstatus = "Complete";

$("#save-btn").on("click",{type:"Save" },subanswers);
$("#complete-btn").on("click",{type:cstatus },subanswers);

function getFormKeys() {
    let myName ={};
    $("#ansFrm").find("input,textarea,select").map(function() {
        myName[this.name] = 1;
    });
    return Object.keys(myName);
}

function sendAns(status){
    $.ajax({
        url: window.fmBaseDir + 'saveQandRes?st=' + status,
        method: "POST",
        data: $("#ansFrm").serialize(),
        cache: false
    }).done(function (data) {

        if (!data.msg) {
            if(cstatus == status) {
                //go home
                $(window).off('beforeunload');
                window.location.href = window.fmBaseDir + "../home/index";
            }
            $('#msg').html("You made a great Save!");
        } else {
            alert(data.msg);
        }
    });
}


function subanswers(event) {
    let status = event.data.type;
    try {
        $('#msg').html("");
        if($("#ansFrm")[0].checkValidity()) {
            if(status === "Save" || confirm("Once You Confirm this dialog You Will not be able to go back. Are you sure you want to continue?")) {
                sendAns(status);
            }
        } else {
            let j = 0;
            let ourKeys = getFormKeys();
            let ksize = ourKeys.length;
            let found = false;
            while(j < ksize && !found) {
                let v = ourKeys[j++];
                if( !$("[name='"+ v +"']")[0].validity.valid) {
                    let p =  $("[name='"+ v +"']").parentsUntil("[data-parent='#mainfrm']").parent();
                    $(p).on("shown.bs.collapse",function() {
                        $("[name='"+ v +"']")[0].reportValidity();
                        $(p).off("shown.bs.collapse");
                    });
                    $("[name='"+ v +"']")[0].reportValidity();
                    $(p).collapse('show');
                    found = true;
                }
            }
        }
    } catch (err) {

    }
}