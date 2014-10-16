<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" language="Javascript">
	$(document).ready(function() {
		LoadSignUp();
	});
</script>

<form:form id="signupForm" method="post" action="createUser" commandName="member">				

    <label>Username</label>
    <form:errors path="username" element="div" cssClass="ui-bar ui-bar-a ui-corner-all" htmlEscape="false" />
    <form:input id="username" path="username" size="25" maxlength="100" type="email"></form:input>
    
	<label>Password</label>
	<form:errors code="Pattern.member.passMatch"  cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false"></form:errors>
	<form:errors path="passText" element="div"  cssClass="ui-bar ui-bar-a ui-corner-all" htmlEscape="false" />
	<form:input id="password" path="passText" size="20" maxlength="20" type="password"></form:input>

	<label>Confirm Password</label>	
	<form:errors path="repassText" element="div"  cssClass="ui-bar ui-bar-a ui-corner-all" htmlEscape="false" />
	<form:input id="repassword" path="repassText" size="20" maxlength="20" type="password"></form:input>


	<div style="height: 20px;">
	</div>	
	
	<input  name="submit" value="sign up" type="submit" />

</form:form>