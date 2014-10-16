<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<div class="centered" style="width: 600px;">
	<div style="width: 600px;" class="ui-state-highlight ui-corner-all">
		<span class="success"></span>
		The account ${member.username} has been created successfully.<br />
		An email will be sent to ${member.username} to verify your email address shortly. Thank you
		for joining us and welcome to Quakd.com!	
	</div>

	<img src="${webapproot}/resources/images/social/story5.png" />
</div>