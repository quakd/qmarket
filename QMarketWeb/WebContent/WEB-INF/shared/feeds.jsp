<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:forEach var="feed" items="${feeds}">
<div class="feedItem clearFix"  style="border-top: 1px solid navy; margin-left: 10px; margin-right: 10px; width: 500px;">
	<div class="feedUser" style="float: left; width: 120px;">
		<img src="<c:url value="${feed.imageUrl}" />" /><br />
		<label><c:out value="${feed.name}" /></label><br />
	</div>
	<div class="feedMessage" style="float: left; width: 380px;">
		<c:out value="${feed.message}"/><br /> 
	</div>
</div>
<div class="clearFix" style="height: 10px">
</div>
</c:forEach>