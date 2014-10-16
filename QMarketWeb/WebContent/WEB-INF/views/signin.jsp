<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />
 
<script type="text/javascript" language="Javascript">
	$(document).ready(function() {
		$('#j_username').watermark("Quakd Username");
		$('#j_password').watermark("Quakd Password");
	});
</script>

<form id="signinForm" name="f" action="${webapproot}/login" method="POST">		
<div class="signin_area centered" style="width: 300px;">
	<div class="light_header">
		 <img src="${webapproot}/resources/images/quakd_logo.png" height="35" /><br />
	</div>
	<div class="signin_area2">
			<div class="formField">
				<br />
				<input id="j_username" name="j_username" size="40" value="${SPRING_SECURITY_LAST_USERNAME}" maxlength="100" />
			</div>
			<div class="formField">
				<br />
				<input id="j_password" name="j_password" size="40" maxlength="20" type="password" />
			</div>			
			<input type="checkbox" name="_spring_security_remember_me" checked="checked" />					
			<label>Remember Me</label>

			<br />
			<br />
			<input type="submit" class="quakd_btn" value="Log In" onclick="$('#signinForm').submit();" style="width: 265px;" />
			<br />
			<br />
	</div>
	<div class="signin_area3">
		<div class="float padLeft10" style="padding-top: 10px;">
			SIGN IN
		</div>
		<div class="floatr">
			<a href="javascript://nop;" onclick="$('#fb_signin').submit();"> 
				<img src="${webapproot}/resources/images/social-icon-white-facebook.png" height="30" width="30" />
			</a>
			<%--  
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript://nop;" onclick="$('#tw_signin').submit();"> 
				<img src="${webapproot}/resources/images/social-icon-white-twitter.png" height="30" width="30" />	
			</a>
			--%>
		</div>
	</div>	
	<div class="clearFix height20"></div>		
</div>
</form>

<div style="display: none;">
	<form id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
		<input type="hidden" name="scope"
			value="email,publish_stream,offline_access,read_stream" />
	</form>
	<form id="tw_signin" action="<c:url value="/signin/twitter"/>" method="POST">
		<input type="hidden" name="scope"
		value="email,publish_stream,offline_access,update_with_media" />	
	</form>
</div>


