<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="basic"/>
        <script>
            fmBaseDir = '${request.contextPath}/${controllerName}/';
        </script>
        <asset:javascript src="jquery.tablesorter.min.js" />
        <asset:javascript src="jquery.tablesorter.widgets.min.js" />
        <asset:stylesheet src="theme.bootstrap_4.css" />



	</head>
	<body>
		<h1>Goal Management</h1>
        <p class="lead">
				<g:if test='${flash.message}'>
					<div class='login_message'>${flash.message}</div>
				</g:if>
				
				<sec:ifNotLoggedIn>
		        	Please login
				</sec:ifNotLoggedIn>
				<sec:ifLoggedIn>
					<h2>Dashboard</h2>
					<div>
						<g:if test="${has_directs}">
                        <h3>People with past due goals</h3>
                        <div id="getPeopleUnder">Loading .. <asset:image src="spinner.gif" /></div>
						<h3>People with Goals due in the next 60 days</h3>
						<div id="getPeopleUnder1">Loading .. <asset:image src="spinner.gif" /></div>
						</g:if>
						<h3>My Goals that are overdue</h3>
						<div id="myGoalsOver">Loading .. <asset:image src="spinner.gif" /></div>
						<h3>My Goals that are due in the next 60 days</h3>
						<div id="myGoalsComming">Loading .. <asset:image src="spinner.gif" /></div>
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
                <asset:javascript src="home.js" />
            </sec:ifLoggedIn>
	</body>
</html>
