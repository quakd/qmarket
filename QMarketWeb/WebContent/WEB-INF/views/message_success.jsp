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


<div class="centered" style="width: 500px;"> 							
	<div class="float" style="min-height: 100px;">		
		<c:if test="${success}">
			<div class="ui-state-highlight ui-corner-all" style="min-height: 50px; width: 500px;">
				<span class="success"></span>	
				Thanks for sharing, enjoy your discount.
			</div>	
			<div class="ui-state-highlight ui-corner-all">
			</div>
		</c:if>
		<c:if test="${not success}">
			<div class="ui-state-error ui-corner-all" style="min-height: 50px; width: 500px;">
				<span class="error"></span>	
				Unable to share your message with social media sites, please try again later. <br />
				<c:out value="${post_error}" />
			</div>			
		</c:if>			
	</div>
</div>	

<div class="height20 clearFix"></div>

<c:if test="${not empty qpond }">	
	<div class="qpond_cntr centered">
		<div class="float qpond_company_name">
			<h3><c:out value="${qpond.companyLocation.companyInformation.companyName}" /><br />
			<c:out value="${qpond.companyLocation.address}" /><br />
			<c:out value="${qpond.companyLocation.city}" /> <c:out value="${qpond.companyLocation.usStates.code}" />	
		</div>
		<div class="floatr qpond_company_logo">	
			 <img src='${webapproot}/secure/vendor/image?id=<c:out value="${qpond.companyLocation.companyInformation.companyCd}" />' height='100' width='100' alt='Logo' />	
		</div>		
		<div class="clearFix height10"></div>
		<div>
			<h2 style="text-align:center;">Please give the code  ${qpond.quakid}</h2>				
		</div>			
		<div class="qpond_company_msg">
			<img src='${webapproot}/secure/vendor/qpond?id=<c:out value="${qpond.companyQponds.qid}" />' height='300' width='500' alt='QPond' />	
		</div>
	</div>	
</c:if>

<div class="height20 clearFix"></div>
<div class="height20 clearFix"></div>
<div class="height20 clearFix"></div>
<div class="height20 clearFix"></div>