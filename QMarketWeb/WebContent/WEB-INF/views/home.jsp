<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<c:url var="splashScreen" value="/secure/splash" />

<script type="text/javascript" language="javascript">

	$(document).ready(function() {

		LoadSearch();  
             
	});
</script>

<c:if test="${!sessionScope.user_id.connectedToSocial && sessionScope.user_id.splashScreen}">

</c:if>

<%-- 
<!--  
<div id="socialFeeds" class="centered" style="width: 200px;">
	<c:forEach var="feed" items="${feeds}">
	<div class="feedItem clearFix" style="border-top: 1px solid navy; margin-left: 10px; margin-right: 10px; width: 500px;">
		<div class="feedUser" style="float: left; width: 120px;">
			<img src="<c:url value="${feed.imageUrl}" />" /><br />
			<label><c:out value="${feed.name}" /></label><br />
		</div>
		<div class="feedMessage clearFix" style="float: left; width: 380px;">
			<c:out value="${feed.message}"/><br /> 
		</div>
	</div>
	<div class="clearFix" style="height: 10px">
	</div>
	</c:forEach>
</div>
-->
--%>
<!-- modal content -->
<div id="socialmedia-dlg" style="display: none;">
	<h3>Connecting to Social Media</h3>
	<p>In order to share your coupons with your friends and promote local businesses, please 
	connect to one or more of the social media like Facebook or Twitter.</p>
	<p class="instructions">
		1) From your home page, click on one of the connection icons below to connect to
		either facebook or twitter, like those pictured below.
	</p>
	<p class="instructions">
		2) Search for a vendor or business that is Quakd partner and share your experience
		to others along with savings.
	</p>	
	<p class="instructions">
		3) Visit your QPONDS section and enjoy the savings.
	</p>		
</div>

<div id="quakdSearch">

	<h2>Search for Quakd Business Partners</h2>
	
	<div class="height10"></div>
	
	<div class="formField float" style="min-width : 225px;">
		<input id="zipCode" type="text" style="width : 200px;" />
	</div>
	<div class="formField float padLeft10">
		<input id="map_address_check" type="checkbox" />	
		<select id="milesVal">
		  <option value="10">10 mi</option>
		  <option value="15">15 mi</option>
		  <option value="20">20 mi</option>
		  <option value="25">25 mi</option>
		</select> 
	</div>		
	<div class="formField float" >
		<button class="quakd_btn" style="width : 80px;" onclick="javascript:ClickFindAddress();">Search</button>
	</div>			
	<div class="formField float">
		 <button id="map-slideout-trig" class="quakd_btn" style="width : 170px;">Show / Hide Results</button>
	</div>	
	<div class="height10 clearFix"></div>		
	
	<div id="map-canvas" style="height: 500px; width: 650px;">
	</div>
	
	<div class="height20 clearFix"></div>
	<div class="height20"></div>	

</div>
