<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.*" %>
<%
	String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-us">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>Quakd - <tiles:getAsString name="documentTitle"
		ignore="true" /></title>
<c:set var="webapproot" value="${pageContext.request.contextPath}" /> 

<link rel="stylesheet" href="${webapproot}/resources/css/quakd/quakd.min.css" />
<link rel="stylesheet" href="${webapproot}/resources/css/quakd/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="${webapproot}/resources/css/quakd/jquery.mobile.css" />
<link rel="stylesheet" href="${webapproot}/resources/css/MobileSite.css" />

<script type="text/javascript" src="${webapproot}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery.mobile-1.4.0.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery.watermark.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/quakd.js"></script>
 
<script type="text/javascript" language="javascript">
	isMobile = true;
</script>
</head>
<body>
<div data-role="page">
	<div data-role="panel" id="navigation" data-position="right" style="margin-top: 10px;">
	    <!-- panel content goes here -->
	    <img src="${webapproot}/resources/images/foot_logo.png" alt="Logo" />
	    <hr></hr>
		<c:if test="${sessionScope.user_id != null}">
			<label><c:out value="${sessionScope.user_id.username}" /></label>
			<ul data-role="listview" data-inset="true">
				<li><a href="<c:url value="/logout" />" >LOGOUT</a></li>
			</ul>
		</c:if>
		<br />
		<ul data-role="listview" data-inset="true">
			<li><a href="${webapproot}/landing" data-ajax="false">HOME</a></li>							
			<li><a href="${webapproot}/about">ABOUT</a></li>
			<li><a href="${webapproot}/privacy">PRIVACY</a></li>
			<li><a href="${webapproot}/support">SUPPORT</a></li>	
		</ul>	       
	</div><!-- /panel -->
	<div data-role="header">
		<a href="#navigation" data-icon="bars" data-iconpos="notext" value="Icon only" data-rel="close" class="ui-btn-right"></a>
		<!-- 
		<div class="centered" style="width: 250px">
			<img src="${webapproot}/resources/images/quakd_logo.png" alt="Logo" />	
		</div>	
		 -->	
	</div>
	<div data-role="content" class="centered" style="width: 80%">
		<c:if test="${requestScope.messages != null}">
		  	<c:forEach items="${requestScope.messages}" var="item">
				<label><c:out value="${item.message}" /></label>
			</c:forEach>  
			<div style="height: 20px;"></div>
		</c:if>		
		<tiles:insertAttribute name="mainContent" ignore="false" ></tiles:insertAttribute>
		<div style="text-align: center;">
			<label id="copyright">All Images and Content &copy <%=year%> Quakd LLC</label>  
		</div>	
	</div>	
</div>
</body>
</html>