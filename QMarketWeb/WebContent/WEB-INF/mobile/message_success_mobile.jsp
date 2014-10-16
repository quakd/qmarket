<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<div style="text-align: center; width: 100%">
	<h1>QPond</h1>
</div>

<c:if test="${success}">
     <div class="share_message" name="post" id="message" style="height: 225px; text-align: center;">
     	<div class="quakd_enjoy">
     		enjoy <span class="quakd_20off">20%</span>  off
     	</div><br />
    	<label class="quakd_code">230000</label><br />
    	<label class="quakd_discount">*please give this number to the attendant to receive your discount</label>
    </div>   
</c:if>
<c:if test="${not success}">
	<label>Unable to share your message with social media sites, please try again later.</label>
	<label><c:out value="${post_error}" /></label>		
</c:if>		