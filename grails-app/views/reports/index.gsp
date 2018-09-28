<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="basic"/>
		<asset:javascript src="FileSaver.js" />

	</head>
	<body>
		<h1>Reports</h1>
		<div>
			Select a year for a Portfolio Report : <g:datePicker name="year" default="${new Date()}" precision="year"/>
			<button id="gobtn">GO!</button>
		</div>
	<div>
		Select a year for an Employee Report : <g:datePicker name="ryear" default="${new Date()}" precision="year"/>
		<button id="gobtnemp">GO!</button>
	</div>

	<asset:javascript src="report.js" />
	<script>
        var fmBaseDir = '/${controllerName}/';
	</script>
	</body>
</html>
