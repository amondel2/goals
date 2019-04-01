if($("#getPeopleUnder").length > 0 ) {
    $.ajax({
        url: window.fmBaseDir + 'getPeopleUnder',
        method: "GET",
        cache: false
    }).done(function (data) {
        if (data && data.msg) {
            processdate($("#getPeopleUnder"),data.msg);

        } else {
            alert(data[1]);
        }
    });

    $.ajax({
        url: window.fmBaseDir + 'getPeopleUnder?fut=60',
        method: "GET",
        cache: false
    }).done(function (data) {
        if (data && data.msg) {
            processdate($("#getPeopleUnder1"),data.msg);

        } else {
            alert(data[1]);
        }
    });
}

$.ajax({
    url: window.fmBaseDir + 'getMyGoals',
    method: "GET",
    cache: false
}).done(function (data) {
    if (data && data.msg) {
        processdate($("#myGoalsOver"),data.msg);

    } else {
        alert(data[1]);
    }
});

$.ajax({
    url: window.fmBaseDir + 'getMyGoals?fut=60',
    method: "GET",
    cache: false
}).done(function (data) {
    if (data && data.msg) {
        processdate($("#myGoalsComming"),data.msg);

    } else {
        alert(data[1]);
    }
});


function processdate(elm,msg) {
    if(msg.error) {
        elm.html(msg.error)
    } else {
        var id="tbl_" + elm.attr('id');
        var tbl = '<table id="' +id +'"  class="table table-bordered table-striped"><thead class="thead-light"><th data-sortable="true">Employee</th><th data-sortable="true">Goal</th><th data-sortable="true">Due Date</th></thead><tbody>';
        $.each(msg,function(index,value){
            $.each(value,function(index1,val) {
                tbl += "<tr><td>" + index + "</td><td>" + val.goal + "</td><td>" + val.due + "</td></tr>";
            });
        });
        tbl += "</tbody></table>";
    }
    elm.html(tbl)

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