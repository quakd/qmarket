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
<meta name="viewport" content="width=device-width" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>Quakd - <tiles:getAsString name="documentTitle"
		ignore="true" /></title>
<c:set var="webapproot" value="${pageContext.request.contextPath}" />
<link href="${webapproot}/resources/css/LandingSite.css" rel="stylesheet" />
<link href="${webapproot}/resources/css/SiteShared.css" rel="stylesheet" />
<link href="${webapproot}/resources/css/jquery.qtip.min.css" rel="stylesheet" />
<link href="${webapproot}/resources/css/ui-lightness/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link rel="stylesheet" href="${webapproot}/resources/css/flexslider.css" type="text/css">

<script type="text/javascript" src="${webapproot}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery.watermark.min.js"></script>

<script type="text/javascript" src="${webapproot}/resources/js/jquery.qtip2.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/quakd.js"></script>

</head>
<body>
	<script type="text/javascript" language="Javascript">
	$(window).on('resize', function () {	
		ResizeMainArea(true);
	});	
	
	$(document).ready(function() {
		LoadHeader(true);
	});
	</script>
	<div id="container">
	<div id="header">
		<div class="float">
			<img
				src="${webapproot}/resources/images/foot_logo.png" alt="Logo" />
			<img
				src="${webapproot}/resources/images/quakd_logo.png" alt="Logo" />
		</div>
		<div id="trade" class="float">
		&trade;
		</div>
		<div id="login_screen">
			<button class="signin" ><img src="${webapproot}/resources/images/foot_sm.png" height="18" /> Sign In &gt;</button> <span
				class="padLeft10" /> or <span
				class="padLeft10" /> <button 
				class="signup" ><img src="${webapproot}/resources/images/foot_sm.png" height="18" /> Sign Up &lt;</button>							
		</div>					
		<div class="clearFix"></div>												
	</div>
	<div id="main">	
		<div class="bgimage">
			<ul class="slides">
				<li>
					<img src="${webapproot}/resources/images/slider/main2_800.png" alt="" />
					<p class="flex-caption">Sign up today and enjoy the savings</p>
				</li>
				<li>
					<img src="${webapproot}/resources/images/slider/main3_800.png" alt="" />
					<p class="flex-caption">Sign up today and enjoy the savings</p>
				</li>			
				<li>
					<img src="${webapproot}/resources/images/slider/main4_800.png" alt="" />
					<p class="flex-caption">Sign up today and enjoy the savings</p>
				</li>													
			</ul>		
		</div>
	</div>
	<div id="footer">
		<div class="centered" style="width: 600px; height: 120px;">
			<div style="height:50px;">
				<div class="float">
					<ul class="main_nav" style="margin: 0px;">
						<li><a href="${webapproot}/landing"><img src="${webapproot}/resources/images/foot_sm.png" /></a></li>
						<li><a href="${webapproot}/landing">HOME</a></li>							
						<li><a href="${webapproot}/about">ABOUT</a></li>
						<li><a href="${webapproot}/privacy">PRIVACY</a></li>
						<li><a href="${webapproot}/support">SUPPORT</a></li>
					</ul>
				</div>	
				<div class="floatr" style="padding-top: 18px;">
					<a href="https://www.facebook.com/sloopdesigns" target="_new"><img src="${webapproot}/resources/images/facebook.png" height="25" alt="Ad" /> </a>
					<a href="https://twitter.com/quakd" target="_new"><img src="${webapproot}/resources/images/twitter.png" height="25" alt="Ad" /></a>					
				</div>					
			</div>
			<div style="height:70px; width: 600px; text-align: center;">
				<a href="${webapproot}/signup" >
					<img id="signup_banner"  src="${webapproot}/resources/images/ad_banner.png" width="550" alt="Ad" />
				</a>
				<br />
				<span id="copyright">&copy;<%=year%> QUAKD LLC</span>
			</div>
		</div>
	</div>
</body>
</html>