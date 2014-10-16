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

    <label>Username</label>
    <input id="j_username" name="j_username" size="40" value="${SPRING_SECURITY_LAST_USERNAME}" maxlength="100" />
    
	<label>Password</label>
	<input id="j_password" name="j_password" size="40" maxlength="20" type="password" />
			
	<label><input type="checkbox" name="_spring_security_remember_me" checked="checked" />	Remember Me</label>

	<div style="height: 20px;">
	</div>	
	
	<input  name="submit" value="Sign In" type="submit" />
</form>
