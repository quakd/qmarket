<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
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
<!-- stylesheet will go here -->
<link href="${webapproot}/resources/css/jquery.qtip.min.css" rel="stylesheet" />
<link href="${webapproot}/resources/css/ui-lightness/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link rel="stylesheet" href="${webapproot}/resources/css/flexslider.css" type="text/css">
<link href="${webapproot}/resources/css/Site.css" rel="stylesheet" />
<link href="${webapproot}/resources/css/SiteShared.css" rel="stylesheet" />
<link href="${webapproot}/resources/css/modal/basic.css" rel="stylesheet" />

<!-- IE6 "fix" for the close png image -->
<!--[if lt IE 7]>
<link type='text/css' href='${webapproot}/resources/css/modal/basic_ie.css' rel='stylesheet' media='screen' />
<![endif]-->



<script type="text/javascript" src="${webapproot}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery.watermark.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/modal/jquery.simplemodal.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/modal/basic.js"></script>

<script type="text/javascript" src="${webapproot}/resources/js/jquery.qtip2.min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="${webapproot}/resources/js/quakd.js"></script>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBXtyHZNABzZNhchmRFPrwErbmdkeR8Ls4&sensor=true"></script>
<script type="text/javascript" src="${webapproot}/resources/js/map.js"></script>


</head>

<body>
	<script type="text/javascript" language="Javascript">
	
	$(window).on('resize', function () {	
		ResizeMainArea();
	});	
	
	$(document).ready(function() {
		LoadHeader();
	});
	
	</script>
	<div id="container">
		<div id="header">		
			<div id="subheader" class="centered">
				<div class="float">			
							<img
			src="${webapproot}/resources/images/quakd_logo_sm.png" alt="Logo" height="50" />
			
				</div>
				<div id="trade">
				&trade;
				</div>
				<div id="searchArea">
					<input id="searchTxt" type="text" class="search rounded" size="30" style="height: 12px;" />
					<!--   <button id="searchBtn">Search</button> -->
				</div>					
				<div id="userinfo">				
					<c:if test="${sessionScope.user_id != null}">
						<div class="dropDownMenu" id="quick_nav_btn">
							<a href="javascript://nop;">
								<img src="${webapproot}/secure/profileimage" alt="Logo" height="30" />
								<c:out value="${sessionScope.user_id.username}" />
							</a> 
						</div>
						<div class="logon-title">
							Welcome, <c:out value="${sessionScope.user_id.username}" />
						</div>	
						<div class="logon-content">
							<ul id="sm_nav_menu">
								<li><a href="${webapproot}/landing">Home</a></li>
								<li><a href="<c:url value="/logout" />" >Logout</a></li>
							</ul>
						</div>																							
					</c:if>	
					<c:if test="${sessionScope.user_id == null}">
						<div id="login_screen">
							<button class="signin" ><img src="${webapproot}/resources/images/foot_sm.png" height="18" /> Sign In &gt;</button> <span
								class="padLeft10" /> or <span
								class="padLeft10" /> <button 
								class="signup" ><img src="${webapproot}/resources/images/foot_sm.png" height="18" /> Sign Up &lt;</button>							
						</div>	
					</c:if>											
				</div>						
				<div class="clearFix" ></div>		
				</div>									
		</div>
		<div id="main">
			<tiles:insertAttribute name="navigation" ignore="false" ></tiles:insertAttribute>		
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


<c:if test="${requestScope.forceMessage != null}">
   <script language="javascript">
       $(document).ready(function () {
           $('#dialog-message').dialog({ modal: false,
               buttons: {
                   Ok: function () {
                       $(this).dialog("close");
                       $('#dialog-message').remove();
                   }
               }
           });               
       });     
   </script>      
</c:if>

<div id="dialog-message" title="Application Message" class="noDisplay">     
  	<c:forEach items="${requestScope.messages}" var="item">
		<div class="${item.css}" style="width:250px; min-height: 35px;">
			<span class="${item.type}"></span>
			<c:out value="${item.message}" />
		</div><br />
	</c:forEach>        
</div>
</html>