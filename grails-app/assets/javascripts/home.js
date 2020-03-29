$.ajax({
    url: window.fmBaseDir + 'getMyUats',
    method: "GET",
    cache: false
}).done(function (data) {
    if (data) {
        processdate($("#UATS"),data,['Title','Program','Due Date'],['title','program','endDate'],"../UAT/initialize/");

    } else {
        alert(data[1]);
    }
});

if($("#iandq").length > 0 ) {
    $.ajax({
        url: window.fmBaseDir + 'getIssues?fut=10',
        method: "GET",
        cache: false
    }).done(function (data) {
        if (data) {
            processdate($("#iandq"), data,['Issue','UAT','Type','User'],['issue','uat','type','user'],"../issue/edit/");

        } else {
            alert(data[1]);
        }
    });
}

if($("#uatstatus").length > 0 ) {
    $.ajax({
        url: window.fmBaseDir + 'getUatReport',
        method: "GET",
        cache: false
    }).done(function (data) {
        if (data) {
            processdate($("#uatstatus"), data,['Name','Total','Not Stated','Active','Completed'],['title','total','notStartedtotal','activetotal','completedTotal'],"");

        } else {
            alert(data[1]);
        }
    });
}

