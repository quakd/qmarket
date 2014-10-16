<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="webapproot" value="${pageContext.request.contextPath}" /> 

<script type="text/javascript" language="javascript">
	$(document).ready(function() {  
		LoadSearch();             
	}); 
</script>  

<div id="quakdSearch">    
	<div class="height10"></div>  
	<form id="searchForm" action="#" onsubmit="return false;">
		<input  type="search" id="searchAddressField" class="ui-alt-icon"  />
	</form>
	<div class="height10"></div><br />
	<div class="height10"></div><br />
	<ul data-role="listview" data-inset="false" id="vendorList">
	</ul> 
	<div class="height10"></div><br />
	<div class="height10"></div><br />
	<div class="centered" id="search_logo" style="width: 129px" height="140">
		<img src="${webapproot}/resources/images/quakd_foot.png" width="129" alt="Logo" />
	</div>
	<div class="height10"></div><br />
	<div class="height10"></div><br />		
</div>