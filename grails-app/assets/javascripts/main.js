// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery-3.3.1.min
//= require bootstrap
//= require jquery-ui
//= require jstree-min
//= require jquery.contextMenu.min
//= require edittable
//= require_self

$(document).ready(function(){
	function logout(event) {
        event.preventDefault();
        $.ajax({
           url: $("#_logout").attr("href"),
           method: "POST"
        }).done(function(){
            window.location = $("#_afterLogout").attr("href");
        }).fail(function(jqXHR, textStatus, errorThrown){
            alert("Couldn't Logout");
            console.log("Logout error, textStatus: " + textStatus + ", errorThrown: " + errorThrown);
        });
     }

	$("#logout").on('click',logout);
});

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
                if(y++ === 0 && val.id && linkLoc) {
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