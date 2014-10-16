<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />
<div class="centered" style="text-align: center;">
	<h3 >Share</h3>
</div>
<form:form id="shareForm" method="POST" action="${webapproot}/secure/social/posts" commandName="post" >
	<form:input path="locId" type="hidden"></form:input>
	<form:input path="post" type="hidden"></form:input>	
    <div class="share_message" name="post" id="message" >
    	<c:out value="${post.post}" escapeXml="true" />
    </div>    
    <div data-role="fieldcontain" style="width: 100%">
        <div style="float: left;">
        	<label for="facebook" style="float: left;">FACEBOOK</label>
        </div>
        <div style="float: right;">
	        <form:select path="facebook" id="facebook" data-role="slider" data-mini="true">
	            <form:option value="on">ON</form:option>
	            <form:option value="off">OFF</form:option>
	        </form:select>
        </div>     
    </div>
    <div data-role="fieldcontain" style="width: 100%;">
    	<div style="float: left;">
        	<label for="twitter" style="float: left;">TWITTER</label>
        </div>
        <div style="float: right;">
	        <form:select path="twitter" id="twitter" data-role="slider" data-mini="true">
	            <form:option value="on">ON</form:option>
	            <form:option value="off">OFF</form:option>
	        </form:select>   
        </div>
    </div>
    <div style="clear:both;">
    </div>
	<input data-role="button" type="submit" value="Share" />
</form:form>  
    