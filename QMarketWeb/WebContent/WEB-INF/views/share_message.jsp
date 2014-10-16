<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<script type="text/javascript" language="javascript">

	$(document).ready(function() {

             
	});
</script>

<!-- modal content -->
<div id="socialmedia-dlg">
	<h3>Connecting to Social Media</h3>
	<p>In order to share your coupons with your friends and promote local businesses, please 
	connect to one or more of the social media like Facebook or Twitter.</p>
	<p class="instructions">
		From your home page, click on one of the connection icons to connect to
		either facebook or twitter, like those pictured below.
	</p>	
</div>


<div id="socialmsg-dlg" style="dipslay: none;">
	<p>Your message cannot be greater than 140 characters in length.</p>
</div>




<div class="centered" style="width: 400px;"> 
	<div class="float" style="min-height: 100px;">
		<h3><c:out value="${companyLoc.companyInformation.companyName}" /><br />
		<c:out value="${companyLoc.address}" /><br />
		<c:out value="${companyLoc.city}" /> <c:out value="${companyLoc.usStates.code}" />
		</h3>
	</div>
	<div class="floatr">
	 <img src='${webapproot}/secure/vendor/image?id=<c:out value="${companyLoc.companyInformation.companyCd}" />' height='100' width='100' alt='Logo' />
	</div>		
</div>

<div class="height20 clearFix"></div>

<div class="centered" style="width: 415px;">
	<div class="dark_header">
		<c:if test="${not empty sessionScope.user_id.facebookImageUrl}">
				<img src="${webapproot}/resources/images/facebook.png" height="25" alt="Ad" />
			</c:if>
			<c:if  test="${not empty  sessionScope.user_id.twitterImageUrl}">
				<img src="${webapproot}/resources/images/twitter.png" height="25" alt="Ad" />
			</c:if> 
	</div>
	<form id="shareForm" method="POST" action="${webapproot}/secure/social/posts">
		<textarea id="socialMessage" name="post" rows="4" cols="50" maxlength="140"></textarea><br />
		<input name="locId" type="hidden" value="<c:out value="${companyLoc.locId}" />" />
		<div style="text-align: center;">
			<a href="javascript://nop;" onclick="SubmitMessage();">
				<img src="${webapproot}/resources/images/quakd_go_sm.png" />
			</a>
		</div>
	</form>
</div>	

<div class="height20 clearFix"></div>

