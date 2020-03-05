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
            processdate($("#iandq"), data,['Issue','UAT','Type','User'],['issue','uat','type','user'],"../issue/show/");

        } else {
            alert(data[1]);
        }
    });
}

function processdate(elm,msg,head,elms,linkLoc) {
    if(msg.error) {
        elm.html(msg.error)
    } else {
        var id="tbl_" + elm.attr('id');
        var tbl = '<table id="' +id +'"  class="table table-bordered table-striped"><thead class="thead-light">';
        let x = "";
        for(x of head) {
            tbl +=  '<th data-sortable="true">' + x + '</th>';
        }
        tbl +=  '</thead><tbody>';
        $.each(msg,function(index,val){
            tbl += "<tr>";
            let y = 0;
            for(x of elms) {
                tbl += "<td>";
                if(y++ === 0 && val.id) {
                    tbl += "<a href='"+ window.fmBaseDir + linkLoc + val.id + "'>"+  val[x] + "</a>";
                } else {
                    tbl +=  val[x]
                }

                tbl +=  "</td>";
            }
            tbl += "</tr>";
        });
        tbl += "</tbody></table>";
    }
    elm.html(tbl);

    $("#" + id).tablesorter({
        theme : "bootstrap",

        widthFixed: true,
        widgetOptions : {
            // using the default zebra striping class name, so it actually isn't included in the theme variable above
            // this is ONLY needed for bootstrap theming if you are using the filter widget, because rows are hidden
            zebra : ["even", "odd"],

            // class names added to columns when sorted
            columns: [ "primary", "secondary", "tertiary" ],

            // reset filters button
            filter_reset : ".reset",

            // extra css class name (string or array) added to the filter element (input or select)
            filter_cssFilter: [
                'form-control',
                'form-control',
                'form-control custom-select', // select needs custom class names :(
                'form-control',
                'form-control',
                'form-control',
                'form-control'
            ]
        }

    });
}