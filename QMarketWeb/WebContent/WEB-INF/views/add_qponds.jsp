<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />



<div class="centered" style="width: 500px;">
<h2>Would you like to add this Qpond?</h2>
<form id="qpondAdd" method="POST" action="${webapproot}/secure/vendor/add">
	<input name="qid" type="hidden" value="<c:out value="${qpond.quakid}" />" />
	<input name="resp" type="submit" value="Yes" style="width: 200px;" />
	<input name="resp" type="submit" value="No" style="width: 200px;" />
</form>
</div>

<div class="clearFix height20"></div>
	
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
	<div class="qpond_company_msg">
		<img src='${webapproot}/secure/vendor/qpond?id=<c:out value="${qpond.companyQponds.qid}" />' height='400' width='500' alt='QPond' />	
	</div>
</div>


