<%@ page import="com.amondel2.techtalk.GoalStatus"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic"/>
    <script>
        fmBaseDir = '${request.contextPath}/${controllerName}/';
        var workingYear = parseInt('${date.get(Calendar.YEAR)}');
    </script>
    <asset:stylesheet src="mondelMapperUI.css" />
    <asset:stylesheet src="themes/default/style.min.css" />
    <script src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
%{--    <ckeditor:config removeButtons="Underline,JustifyCenter,Source,Flash,Image,Templates,Select,Iframe,HiddenField,TextField,Textarea,Checkbox,Button,ImageButton,Radio,Form" />--}%
</head>
<body>
<h1>Goal Set for ${emp.toString()}</h1>
<form method="POST" id="YearChangeFrm">
    <input type="hidden" name="mid" id="emp_id" value="${emp.id}">
    <div>Select Year: <g:datePicker name="myDate" default="${date.getTime()}" precision="year" relativeYears="[-3..2]"/> </div>
</form>


${ps.dirEmployeeDropDown([year:date.get(Calendar.YEAR),empId:uid,selId:emp?.id])}


<input type="hidden" value="${uid}" id="uid" />
<input type="checkbox" name="showhiddenBox" id="showhiddenBox" aria-label="show hidden items" value="true" <g:if test="${emp.showHidden}">checked="checked" aria-checked="true"</g:if> />
<label for="showhiddenBox">Show Closed Goals</label>


<button type="button" id="saveBtn" class="btn btn-primary">Save</button> <button type="button" id="addBtn" class="btn btn-secondary">Add</button>
<button type="button" id="gobtn" class="btn btn-secondary">Generate KPO Report</button>

<div id="main_error" style="display: none;" class="fm-error-msg error-details ui-state-error"></div>
<div id="main_save_done" style="display: none;" class="fm-success-msg success alert-success">Save Completed with No Errors</div>
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
                    <li><div id="${goal.id}_errorsDiv" style="display: none;" class="fm-error-msg error-details ui-state-error hide" error_field="true"></div></li>
                      <li class="clearfix">
                          <label for="${goal.id}_orginTargetDate" >Orginal Completed Date: </label>
                          <span id="${goal.id}_orginTargetDate" >${goal.orginTargetDate ? new SimpleDateFormat("MM-dd-yyyy").format(goal.orginTargetDate): ''}</span>
                          <div id="${goal.id}_targetDiv" style="float: right;display: <g:if test="${goal.status in [GoalStatus.NotStarted,GoalStatus.Ongoing,GoalStatus.Behind,GoalStatus.OnTrack]}">inline-block</g:if><g:else>none</g:else>;">


                              <label for="${goal.id}_targetDate">Target Completed Date: </label>
                                  <g:datePicker class="form-control" name="${goal.id}_targetDate" value="${goal.targetCompletDate}" precision="day" noSelection="['':'-Choose-']"   default="${new Date()}"/>

                          </div>
                          <div id="${goal.id}_completeDiv" style="float: right;display: <g:if test="${goal.status in [GoalStatus.NotStarted,GoalStatus.Ongoing,GoalStatus.Behind,GoalStatus.OnTrack]}">none</g:if><g:else>inline-block</g:else>;">
                              <label>Completed Date: </label> ${new SimpleDateFormat("MM-dd-yyyy").format(goal.actualCompletedDate ?: new Date())}
                          </div>
                      </li>
                        <li>
                            <div class="form-group" >
                                <label for="${goal.id}_title">Goal Title: </label>
                                <input type="text" class="form-check-inline" maxlength="100" style="width:300px;" value="${goal.title}" minlength="3" name="${goal.id}_title" required="required" aria-required="true"  aria-label="title" />

                                <label for="${goal.id}_status">Goal Status: </label>
                                ${ps.goalStatusDropDown([value:goal.status,name:goal.id + "_status"])}
                                <label for="${goal.id}_types">KPO Types: </label>
                                ${ps.goalTypeDropDown([value:goal?.goalType,goalTypes:goalTypes,name:goal.id + "_types"])}
                            </div>
                        </li>
                      <li>
                          <label for="${goal.id}_descript">Goal Description: </label>
                          <textarea name="${goal.id}_descript" width="80%" toolbar="Basic">
                        ${goal.description}
                      </textarea>
                          <script>

                              CKEDITOR.replace( '${goal.id}_descript', {
                                  removePlugins: "elementspath,save",
                                  removeButtons:"Underline,JustifyCenter,Source,Flash,Image,Templates,Select,Iframe,HiddenField,TextField,Textarea,Checkbox,Button,ImageButton,Radio,Form"
                              } );
                          </script>
                      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#commentsModel" data-whatever="${goal.id}">Comments</button>

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
        <g:datePicker class="form-control" name="targetDate" value="" precision="day" noSelection="['':'-Choose-']"   default="${new Date()}"/>
    </div>
    <div id="goalId_completeDiv" >
        ${new SimpleDateFormat("MM-dd-yyyy").format(new Date())}
    </div>

</div>
<div class="modal fade bd-example-modal-xl" id="commentsModel" tabindex="-1" role="dialog" aria-labelledby="commentsModelLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="commentsModelLabel">View Comments</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="commentForm">
                <input type="hidden" name="goalId" id="goalId">
                <label for="newComment">New Comment</label>
                <input type="text" maxlength="490" class="newCommentsBoxSize" name="newComment" id="newComment">
                </form>
                <div id="prevComments"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary">Save changes</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<asset:javascript src="goalEditor.js" />