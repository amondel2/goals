<%@ page import="java.util.Calendar"%>
<%@ page import="com.amondel2.techtalk.GoalStatus"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="basic"/>
                <script>
                    fmBaseDir = '${request.contextPath}/${controllerName}/';
                    var fmGoalsDir = '${request.contextPath}/goals/';
                    fmCompanyName = '${companyName}';
                    var fmBossId = '${boss?.id}';
                    var fmBossName = '${boss.toString()}';
                    fmSuccessMsg = '<g:message code="page.message.success" encodeAs="JavaScript" />';
                    fmErrorMsg = '<g:message code="page.message.error" encodeAs="JavaScript" />';
                    fmConfirmMsg= '<g:message code="page.button.confirm" encodeAs="JavaScript" />';
                    fmCancelMsg = '<g:message code="page.button.cancel" encodeAs="JavaScript" />';
                    fmProblemMsg = '<g:message code="page.error.FatalError" encodeAs="JavaScript" />';
                    fmLoadingMsg = '<g:message code="page.status.loading" encodeAs="JavaScript" />';
                    fmHasWritePermission = true;
                    fmHasImportExportPermission = false;
                    fmSaveMsg = '<g:message code="page.button.Save" encodeAs="JavaScript" />';
                    var workingYear = parseInt('${date.get(Calendar.YEAR)}');
                </script>
                <asset:javascript src="jstree-min.js" />
                <asset:javascript src="hierarchyUI.js" />
                <asset:stylesheet src="franchiseMapperUI.css" />
                <asset:stylesheet src="themes/default/style.min.css" />
	</head>
	<body>
		<h1>Project Management & Tracking</h1>
    <form method="POST" id="YearChangeFrm">
        <div>Select Year: <g:datePicker name="myDate" default="${date.getTime()}" precision="year" relativeYears="[-3..2]"/> </div>
    </form>
       <div class="fm-content">
        <div class="fm-message"></div>
        <div class="fm-container fm-border">
            <div class="fm-left-panel fm-border">
                <div class="fm-left-tree-container">
                    <div id="jstree"></div>
                </div>
            </div>
            <div class="fm-right-panel fm-border">
                <div class="fm-right-header">
                    <div class="fm-field-container">
                        <span class="fm-field-label"><g:message code="page.label.name" default="Name" encodeAs="HTML" />:</span>
                        <span class="fm-field" id="fm-name-field"></span>
                    </div>
                    <div class="fm-field-container">
                        <span class="fm-field-label"><g:message code="page.label.employeeId" default="Employee Id" encodeAs="HTML" />:</span>
                        <span class="fm-field" id="fm-portfolio-id-field"></span>
                    </div>
                </div>
                <div class="fm-right-info">
                        <div class="fm-right-info-buttonset">
                            <input type="button"
                                   id="edit-person-goals-btn"
                                   onclick="editpersongoal(this)"
                                   class="ui-button ui-widget ui-state-default ui-corner-all fm-only-on-select"
                                   value="<g:message code='page.button.editGoals' default="Edit Person Goals" encodeAs='HTML' />">
                            <input type="button"
                                   id="mark-as-leave-btn"
                                   onclick="markLeave(this)"
                                   class="ui-button ui-widget ui-state-default ui-corner-all fm-only-on-select"
                                   value="<g:message code='page.button.markleave' default="Mark Person Leave" encodeAs='HTML' />">
                        </div>
                        <div class="fm-right-form-container">
                            <form class="fm-right-form">
                                <div class="fm-form-field-set">
                                    <div class="fm-form-field">
                                        <label for="fm-name-input" class="fm-field-label">
                                            <span class="asterisk">*</span>Name:
                                        </label>
                                        <input id="fm-name-input" class="fm-form-input" type="text" name="name">
                                        <span class="fm-validation-msg" id="fm-name-validation-msg"></span>
                                    </div>
                                    <div class="fm-form-field" id="fm-portfolioUnitId-form-field">
                                        <label for="fm-portfolioUnitId-input" class="fm-field-label">
                                            <span class="asterisk">*</span>Employee Id:
                                        </label>
                                        <input id="fm-portfolioUnitId-input" class="fm-form-input" type="text" name="employeeId">
                                        <span class="fm-validation-msg" id="fm-portfolioUnitId-validation-msg"></span>
                                    </div>
                                </div>
                                <div class="fm-form-footer">
                                    <input type="submit"
                                           id="fm-submit-btn"
                                           class="ui-button ui-widget ui-state-default ui-corner-all"
                                           value="<g:message code='page.button.save' encodeAs='HTML' />">
                                </div>
                            </form>
                        </div>
                </div>
            </div>
        </div>
    </div>
    <div class="fm-hidden">
        <div id="dialog-confirm" title="Delete Row?">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Row <span id="empRowId"></span> will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>
        <div id="fm-delete-confirm">
            <p><g:message code="page.orphan.msg" encodeAs="HTML" default="Are You Sure you want to delete?"/></p>
        </div>
        <div id="fm-editpersongoals">
            <div>
                <table id="editpersongoalstable">
                    <thead>
                    <tr><th>Title</th><th>Status</th><th>Action</th></tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>

        <div id="fm-markleave">
            <div>
                Enter Leave Date: <g:datePicker name="leaveDate" default="${date.getTime()}" precision="day" relativeYears="[-3..2]"/>
                <input type="hidden" name="leaveEmpId" id="leaveEmpId" value="" />
            </div>
        </div>
        <div id="goalTypes">
            <g:each  in="${goalTypes}" var="gt">
                <input type="checkbox" name="goalTypes[]" id="gen_${gt.id}" value="${gt.id}" /><label for="gen_${gt.id}">${gt.title}</label>
            </g:each>
        </div>
        <div id="gs_select">
            <g:select name="goalStatus" from="${GoalStatus}" />

        </div>
    </div>
	</body>
</html>