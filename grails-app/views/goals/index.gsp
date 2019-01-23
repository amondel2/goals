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

    <ckeditor:resources/>
    <ckeditor:config removeButtons="Underline,JustifyCenter,Source,Flash,Image,Templates,Select,Iframe,HiddenField,TextField,Textarea,Checkbox,Button,ImageButton,Radio,Form" />
</head>
<body>
<h1>Goal Set for ${emp.toString()}</h1>
<form method="POST" id="YearChangeFrm">
    <input type="hidden" name="mid" id="emp_id" value="${emp.id}">
    <div>Select Year: <g:datePicker name="myDate" default="${date.getTime()}" precision="year" relativeYears="[-3..2]"/> </div>
</form>



<button type="button" id="saveBtn" class="btn btn-primary">Save</button> <button type="button" id="addBtn" class="btn btn-secondary">Add</button>
<form method="POST" id="gaolFrm" onsubmit="return false;">
<div class="accordion md-accordion" id="accordionEx" role="tablist" aria-multiselectable="true">
<g:each var="goal" in="${goalSet}" status="idx"  >
  <div class="card">
      <div class="card-header" role="tab" id="headingTwo${idx}">
          <a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseTwo${idx}"
             aria-expanded="false" aria-controls="collapseTwo${idx}">
              <h5 class="mb-0 ${goal.id}">
                ${ps.generateTitle([goal:goal])}
              </h5>
          </a>
    </div>
          <div id="collapseTwo${idx}" class="collapse" role="tabpanel" aria-labelledby="headingTwo${idx}" data-parent="#accordionEx">
              <div class="card-body">
                <ul style="list-style: none;">
                      <li>
                          <label for="${goal.id}_title">Goal Title: </label>
                          <input type="text" class="form-control" value="${goal.title}" minlength="3" name="${goal.id}_title" required="required" aria-required="true"  aria-label="title" /></li>
                      <li>
                          <label for="${goal.id}_descript">Goal Description: </label>
                          <ckeditor:editor name="${goal.id}_descript" width="80%" toolbar="Basic">
                        ${goal.description}
                      </ckeditor:editor></li>

                       <li>
                           <div class="form-group">
                           <label for="${goal.id}_status">Goal Status: </label>
                           ${ps.goalStatusDropDown([value:goal.status,name:goal.id + "_status"])}
                           </div>
                       </li>

                       <li>
                           <div class="form-group">
                           <label for="${goal.id}_types">Goal Types: </label>
                           ${ps.goalTypeDropDown([value:goal.goalType,goalTypes:goalTypes,name:goal.id + "_types"])}
                           </div>
                       </li>
                       <li>
                               <div id="${goal.id}_targetDiv" style="display: <g:if test="${goal.status in [GoalStatus.NotStarted,GoalStatus.Ongoing,GoalStatus.Behind,GoalStatus.OnTrack]}">block</g:if><g:else>none</g:else>;">
                               <div class="form-group">
                               <label for="${goal.id}_targetDate">Target Completed Date: </label>
                               <g:datePicker class="form-control" name="${goal.id}_targetDate" value="${goal.targetCompletDate}" precision="day" noSelection="['':'-Choose-']"   default="${new Date().plus(7)}"/>
                               </div>
                               </div>
                               <div id="${goal.id}_completeDiv" style="display: <g:if test="${goal.status in [GoalStatus.NotStarted,GoalStatus.Ongoing,GoalStatus.Behind,GoalStatus.OnTrack]}">none</g:if><g:else>block</g:else>;">
                                   <label>Completed Date: </label> ${(goal.actualCompletedDate ?: new Date()).format('MM-dd-YYYY')}
                               </div>
                       </li>
                </ul>
              </div>
        </div>
    </div>
</g:each>
</div>
</form>
<div id="hiddenCard" style="display: none;">
    ${ps.goalStatusDropDown([value:'NotStarted',name:"tmp_statusdrp"])}
    ${ps.goalTypeDropDown([value:null,goalTypes:goalTypes,name:"tmp_typesdrp"])}
    <div id="goalId_targetDiv">
        <g:datePicker class="form-control" name="targetDate" value="" precision="day" noSelection="['':'-Choose-']"   default="${new Date().plus(7)}"/>
    </div>
    <div id="goalId_completeDiv" >
        ${new Date().format('MM-dd-YYYY')}
    </div>
</div>
<asset:javascript src="goalEditor.js" />