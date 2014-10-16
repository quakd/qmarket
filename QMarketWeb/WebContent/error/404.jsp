<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="java.util.*" %>
<%
	String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>Quakd - Error</title>
<c:set var="webapproot" value="${pageContext.request.contextPath}" />
<!-- stylesheet will go here -->
<link href="${webapproot}/resources/css/ErrorSite.css" rel="stylesheet" />
</head>
<body >
	<div id="container" class="quakdbg">
		<div id="header">
		</div>
		<div id="body">
			<div>
				<img src="${webapproot}/resources/images/superq.png" />
			</div>
			<div id="message">
				<div class="auto">
				404 <br />
				I'M SORRY, <br />
				THE PAGE YOU SEEK <br />
				DOES NOT EXIST, <br />
				BUT HOLD FAST <br />
				I, SUPER Q <br />
				WILL GET YOU HOME. <br />
				<a href="${webapproot}/landing">CLICK HERE</a> 
				</div>
			</div>
		</div>
		<div id="footer">	
			<span id="copyright">&copy;<%=year%> QUAKD LLC</span>	
		</div>
	</div>	
</body>
</html>