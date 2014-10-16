<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<script type="text/javascript" language="Javascript">

var navigation = '${requestScope.navigation}';

$(document).ready(function() {
	$('#nav_menu').menu();		
	$('#' +navigation).addClass('nav_selected');
});

</script>

<div id="user_dashboard" class="centered" >
	<div id="user_nav">
		<div class="user_nav_header">
			<h2>Quakd User</h2>
		</div>
		
		<div class="side_user_nav">
			<div>
				<div class="height10">
				</div>		
				<img src="${webapproot}/secure/profileimage" alt="Logo" height="100" width="150" />
				<c:out value="${sessionScope.user_id.username}" />
				<div class="height10">
				</div>				
			</div>
			<div class="height10">
			</div>	
			<ul class="side_user_nav">
				<li><a id="home_nav" href="<c:url value="/secure/home" />" >Home</a></li>
				<li><a id="user_info_nav" href="<c:url value="/secure/updateinfo" />" >Your Information</a></li>
				<li><a id="favorites_nav" href="<c:url value="/secure/favorites" />">Your Favorites</a></li>
				<li><a id="qponds_nav" href="<c:url value="/secure/qponds" />">Your Qponds</a></li>
			</ul>
		</div>

		<div class="height20"></div>
		
		<div class="user_nav_header">
			<h2>Social Media</h2>
		</div>
		
		<div class="social_media_connections">
			<div class="height10">
			</div>				
			<div id="facebook_conn">
				<c:choose>
					<c:when test="${sessionScope.user_id.facebookImageUrl == null}">
						<form action="<c:url value="/connect/facebook" />" method="POST">
							<input type="image" src="${webapproot}/resources/images/social/connect-with-facebook.gif" alt="Submit Form" />
							<input type="hidden" name="scope"
								value="email,publish_stream,offline_access,read_stream" />
						</form>					
						<br />
					</c:when>
					<c:otherwise>
						<div>
							<img src="<c:url value="${sessionScope.user_id.facebookImageUrl}" />"  height="30" />
						</div>
						<div>
							<label><c:url value="${sessionScope.user_id.facebookName}" /></label><br />
							<c:url var="facebookLogout" value="/secure/social/logout">
								<c:param name="providerId" value="facebook"></c:param>
							</c:url>
							<a href="${facebookLogout}">Disconnect from Facebook</a>						
						 </div>
						 <div class="clearFix">
						 </div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="height10">
			</div>			
			<div id="twitter_conn" >
				<c:choose>
					<c:when test="${sessionScope.user_id.twitterImageUrl == null}">
						<form action="<c:url value="/connect/twitter" />" method="POST">
							<input type="image" src="${webapproot}/resources/images/social/connect-with-twitter.png" alt="Submit Form" />
							<input type="hidden" name="scope"
								value="email,publish_stream,offline_access,update_with_media" />							
						</form>
						<br />
					</c:when>
					<c:otherwise>
						<div>			
							<img src="<c:url value="${sessionScope.user_id.twitterImageUrl}" />" height="30" />
						</div>
						<div>	
							<label><c:url value="${sessionScope.user_id.twitterName}" /></label><br />	
							<c:url var="twitterLogout" value="/secure/social/logout">
								<c:param name="providerId" value="twitter"></c:param>
							</c:url>
							<a href="${twitterLogout}">Disconnect from Twitter</a>			
						 </div>
						 <div class="noDisplay">
						 </div>																	
					</c:otherwise>
				</c:choose>
			</div>	
		</div>	
		<div class="height10">
		</div>					
	</div>
	<div id="user_content">
			
		<c:if test="${requestScope.messages != null}">
			<div style="width: 100%">   
				<div class="centered" style="width:500px;">  
			  	<c:forEach items="${requestScope.messages}" var="item">
					<div class="${item.css}" style="width:500px; min-height: 35px;">
						<span class="${item.type}"></span>
						<c:out value="${item.message}" />
					</div><br />
				</c:forEach>  
				</div>
			</div>
		</c:if>
		
		<div class="clearFix"></div>
		
		<tiles:insertAttribute name="mainContent" ignore="true" />
	</div>
	<div class="clearFix"></div>
</div>	

