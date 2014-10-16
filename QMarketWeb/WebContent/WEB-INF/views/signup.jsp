
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<script type="text/javascript" language="Javascript">
	$(document).ready(function() {
		LoadSignUp();
	});
</script>



<div id="signuparea" class="centered">
	<div id="innersignin" style="width: 400x;" class="float">
		<form:form id="signupForm" method="post" action="${webapproot}/createUser" commandName="member">			
			<div class="formField" >
				<label>Username</label><br />
				<form:errors path="username" cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false">
				</form:errors>					
				<form:input id="username" path="username" size="25" maxlength="100"></form:input>
			</div>		
			<div class="height10"></div>
			<div class="formField" >
				<label>Password</label><br />	
				<form:errors code="Pattern.member.passMatch"  cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false"></form:errors>
				<form:errors path="passText" cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false" ></form:errors>
				<form:input id="password" path="passText" size="20" maxlength="20" type="password"></form:input>
			</div>
			<div class="height10"></div>					
			<div class="formField" >
				<label>Confirm Password</label><br />	
				<form:errors path="repassText" cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false"></form:errors>
				<form:input id="repassword" path="repassText" size="20" maxlength="20" type="password"></form:input>
			</div>	
			<div class="height10"></div>
			<div class="formField">
				<input type="submit" class="quakd_btn" value="Sign Up" onclick="this.form.submit();" style="width : 250px;" />
			</div>
		</form:form>
	</div>
	<div id="quakd_signin_story" class="floatr">
		<img src="${webapproot}/resources/images/social/story1.png" /><br />	
		<img src="${webapproot}/resources/images/social/story2.png" /><br />	
		<img src="${webapproot}/resources/images/social/story3.png" /><br />	
		<img src="${webapproot}/resources/images/social/story4.png" /><br />	
	</div>	

</div>

<div class="clearFix"></div>

