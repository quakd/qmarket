<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<div class="header_border">
     Your Favorites
</div>  

<div class="height20"></div>

<c:if test="${empty favorites }">
	<h2 style="text-align:center;">You have no favorites, commence the quaking!&trade;</h2>	
</c:if>

<c:if test="${not empty favorites }">
	<c:forEach var="favorite" items="${favorites}"> 
		<div class="favs_cntr centered">
			<div class="float favs_logo">	
				 <img src='${webapproot}/secure/vendor/image?id=<c:out value="${favorite.companyLocation.companyInformation.companyCd}" />' height='100' width='100' alt='Logo' />	
			</div>			
			<div class="float favs_company_name">
				<h3><c:out value="${favorite.companyLocation.companyInformation.companyName}" /><br />
				<c:out value="${favorite.companyLocation.address}" /><br />
				<c:out value="${favorite.companyLocation.city}" /> <c:out value="${favorite.companyLocation.usStates.code}" />  <c:out value="${favorite.companyLocation.zip}" />	
			</div>
			<div class="clearFix height10"></div>
			<hr></hr>
			<div style="text-align: center;">
				<a href='/market/secure/quakd?id=<c:out value="${favorite.companyLocation.locId}" />'>
					<img src='/market/resources/images/quakd_go_sm.png' height='75' width='75'/>
				</a>
			</div>
		</div>	
		<div class="clearFix height20"></div>			
	</c:forEach>
</c:if>