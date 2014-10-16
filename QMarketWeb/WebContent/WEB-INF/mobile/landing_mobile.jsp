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
<meta name="viewport" content="width=device-width, initial-scale=1""> 
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
		<div data-role="content" class="centered" style="width: 80%">	
			<div class="centered" style="width: 129px" height="140">
				<img src="${webapproot}/resources/images/quakd_foot.png" width="129" alt="Logo" />
			</div>
			<div class="centered" style="width: 200px">
				<img src="${webapproot}/resources/images/quakd_title.png" width="200" alt="Logo" />	
			</div>							
			<div style="height: 20px;">
			</div>		
				<input data-role="button" class="signup" value="sign in with facebook" type="submit" onclick="$('#fb_signin').submit();" />	 	
				<input data-role="button" class="signup" style="width: 50%;" value="sign up with email" type="submit" onclick="ClickSignUp();" />						    
				<input data-role="button" class="signup" style="width: 50%;" value="login" type="submit" onclick="ClickSignIn();" /> 					
			<div style="text-align: center;">
				<label id="copyright">All Images and Content &copy <%=year%> Quakd LLC</label>  
			</div>
						
		</div> 
				 
	</div>
	<div style="display: none;">
		<form id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST" data-ajax="false">
			<input type="hidden" name="scope"
				value="email,publish_stream,offline_access,read_stream" />
		</form>	
	</div>
</body>
</html>