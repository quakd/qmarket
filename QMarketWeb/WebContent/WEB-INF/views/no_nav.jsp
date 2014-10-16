<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   

<div class="clearFix height20">
</div> 

<c:if test="${requestScope.messages != null}">
	<div style="width: 100%">   
		<div  class="centered"  style="width:400px">  
	  	<c:forEach items="${requestScope.messages}" var="item">
			<div class="${item.css}" style="width:400px; min-height: 35px;">
				<span class="${item.type}"></span>
				<c:out value="${item.message}" />
			</div><br />
		</c:forEach>  
		</div>
	</div>
</c:if>

<div class="clearFix height10" >
</div>	

<div style="width: 100%">
	<tiles:insertAttribute name="mainContent" ignore="true" />	
</div>

<div class="clearFix height20" >
</div>	