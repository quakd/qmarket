<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<div class="header_border">
     Your Qponds
</div>  

<div class="height20"></div>

<c:if test="${empty qponds }">
	<h2 style="text-align:center;">You have no qponds&trade;, commence the quaking!&trade;</h2>	
</c:if>

<c:if test="${not empty qponds }">
	<c:forEach var="qpond" items="${qponds}"> 
		<div class="qpond_cntr centered">
			<div class="float qpond_company_name">
				<h3><c:out value="${qpond.memberQuaks.companyLocation.companyInformation.companyName}" /><br />
				<c:out value="${qpond.memberQuaks.companyLocation.address}" /><br />
				<c:out value="${qpond.memberQuaks.companyLocation.city}" /> <c:out value="${qpond.memberQuaks.companyLocation.usStates.code}" />  <c:out value="${qpond.memberQuaks.companyLocation.zip}" />	
			</div>
			<div class="floatr qpond_company_logo">	
				 <img src='${webapproot}/secure/vendor/image?id=<c:out value="${qpond.memberQuaks.companyLocation.companyInformation.companyCd}" />' height='100' width='100' alt='Logo' />	
			</div>		
			<div class="clearFix height10"></div>
			<div>
				<h2 style="text-align:center;">Please give the code  ${qpond.memberQuaks.quakid}</h2>				
			</div>			
			<div class="qpond_company_msg">
				<img src='${webapproot}/secure/vendor/qpond?id=<c:out value="${qpond.memberQuaks.companyQponds.qid}" />' height='300' width='500' alt='QPond' />	
			</div>
		</div>	
		<div class="clearFix height20"></div>			
	</c:forEach>
</c:if>