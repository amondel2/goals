<g:set var='securityConfig' value='${applicationContext.springSecurityService.securityConfig}'/>
<html>
	<head>
		<meta name="layout" content="basic"/>
		<s2ui:title messageCode='spring.security.ui.login.title'/>
		<asset:stylesheet src='spring-security-ui-auth.css'/>
	</head>
	<body>
		<p/>
		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>
			<h1>Login</h1>
			<s2ui:form type='login' focus='username'>
			<fieldset class="form">
					<div class="form-group required">
						<label for="username">Username<span  class="required-indicator">*</span></label>
						<input type="text" name="${securityConfig.apf.usernameParameter}" id="username" class="form-control" required="${username}"/>
					</div>
					<div class="form-group required"><label for="password">Password <span  class="required-indicator">*</span></label>
						<input type="password" name="${securityConfig.apf.passwordParameter}" id="password" class="form-control" required=""/>
					</div>
			</fieldset>
			<fieldset class="buttons">
						
							<span class="forgot-link">
								<a href="#" id="forgot-linkhref"><g:message code='spring.security.ui.login.forgotPassword'/></a>
							</span>
							<!-- s2ui:linkButton elementId='register' controller='register' messageCode='spring.security.ui.login.register'/> -->
							<input id="loginButton_submit" class="btn btn-primary btn-sm active" type="submit" value="<g:message code='spring.security.ui.login.login'/>" /> 
						
				</fieldset>
			</s2ui:form>
			<div style="display:none">
				<g:form controller='register' action='showChanallage' useToken="true" name='forgotFrm'>
					<input type="hidden" name="usernameForgot" id="usernameForgot" value=""/>
				</g:form>
			</div>

		<script type="text/javascript">
			$(document).ready(function(){
				$("#forgot-linkhref").on("click",function(e){
					e.preventDefault();
					$("#usernameForgot").val($("#username").val());
					$("#forgotFrm").submit();
				});
			});
		</script>
	</body>
</html>
