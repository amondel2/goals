<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="basic"/>
        <script>
            fmBaseDir = '/${controllerName}/';
        </script>
        <asset:javascript src="jquery.tablesorter.min.js" />
        <asset:javascript src="jquery.tablesorter.widgets.min.js" />
        <asset:stylesheet src="theme.bootstrap_4.css" />



	</head>
	<body>
		<h1>Project Management & Tracking</h1>
        <p class="lead">
				<g:if test='${flash.message}'>
					<div class='login_message'>${flash.message}</div>
				</g:if>
				
				<sec:ifNotLoggedIn>
		        	Please register (via login link)
				</sec:ifNotLoggedIn>
				<sec:ifLoggedIn>
					<h2>Dashboard</h2>
					<div>
                        <h3>People with past due goals</h3>
                        <div id="getPeopleUnder">Loading .. <asset:image src="spinner.gif" /></div>
						<h3>People with Goals due in the next 60 days</h3>
						<div id="getPeopleUnder1">Loading .. <asset:image src="spinner.gif" /></div>
                    </div>
				</sec:ifLoggedIn>
		        <br>
	       
		</p>
	
		 <g:if test="${param?.autologout}">
		       <script type="text/javascript">
					$(document).ready(function(){
						$("#logout").trigger("click");
					});
				</script> 	
		  </g:if>
<sec:ifLoggedIn>
            <script type="text/javascript">
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
                    url: window.fmBaseDir + 'getPeopleUnder?fut=1',
                    method: "GET",
                    cache: false
                }).done(function (data) {
                    if (data && data.msg) {
                        processdate($("#getPeopleUnder1"),data.msg);

                    } else {
                        alert(data[1]);
                    }
                });

                $("#downReport").on('click',function(){

                    window.open(window.fmBaseDir + "generateEmpReport",'downReportWin');
                });

                $("#alloreport").on('click',function(){

                    window.open(window.fmBaseDir + "generateReport",'alloreport');
                });



                function processdate(elm,msg) {
					if(msg.error) {
					    elm.html(msg.error)
					} else {
					    var id="tbl_" + elm.attr('id');
					    var tbl = '<table id="' +id +'"  class="table table-bordered table-striped"><thead class="thead-light"><th data-sortable="true">Boss</th><th data-sortable="true">Employee</th></thead><tbody>';
                        $.each(msg,function(index,value){
                            $.each(value,function(index1,val) {
									tbl += "<tr><td>" + index + "</td><td>" + val + "</td></tr>";
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

            </script>
</sec:ifLoggedIn>
	</body>
</html>
