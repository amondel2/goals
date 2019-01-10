<%@ page import="com.amondel2.techtalk.GoalStatus"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic"/>
    <script>
        fmBaseDir = '${request.contextPath}/${controllerName}/';
        var workingYear = parseInt('${date.get(Calendar.YEAR)}');
    </script>
    <asset:stylesheet src="franchiseMapperUI.css" />
    <asset:stylesheet src="themes/default/style.min.css" />
</head>
<body>
<h1>Goal Set for ${emp.toString()}</h1>
<form method="POST" id="YearChangeFrm">
    <input type="hidden" name="mid" value="${emp.id}">
    <div>Select Year: <g:datePicker name="myDate" default="${date.getTime()}" precision="year" relativeYears="[-3..2]"/> </div>
</form>

<div class="accordion" id="accordionExample">
<g:each var="goal" in="${goalSet}" >
  <div class="card">
    <div class="card-header" id="headingOne">
      <h5 class="mb-0">
        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
            ${goal.title}
        </button>
      </h5>
    </div>

    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
      <div class="card-body">
          <ul style="list-style: none;"><li>${goal.description}</li>
           <li>${goal.status}</li>
           <li>${goal.goalType}</li>

          </ul>

      </div>
    </div>
  </div>
</g:each>
</div>
