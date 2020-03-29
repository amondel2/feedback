$(document).ready(function(){
    $("#searchBrn").on("click",search);
});

function search(){
    $.ajax({
        url: window.fmBaseDir + 'findUATs',
        method: "POST",
        data: $("#searchfrm").serialize(),
        cache: false
    }).done(function (data) {
        if (!data.msg) {
            processdate($("#uatstatus"),data.info,['Name','Program','Start Date','End Date'],['title','program','startDate','endDate'],false)
        } else {
            alert(data.msg);
        }
    });
}