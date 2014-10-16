<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="webapproot" value="${pageContext.request.contextPath}" />

<c:url var="splashScreen" value="/secure/splash" />


<script type="text/javascript">

/*
	$(document).ready(function() {
		$("#socialmedia-dlg").modal({onClose: function (dialog) {
			jQuery.ajax({
			    type: 'POST',
			    url: "${splashScreen}",
			    data: {},
			    contentType: "text/html"
			});			
			$.modal.close(); 
		}});			
	});
*/

</script>

     <div class="header_border">
        Upload Picture
    </div>  
	
	<div class="height20"></div>


	<div id="uploadphoto" style="width: 500x;">
		<form:form method="POST" action="${webapproot}/secure/uploadphoto" commandName="uploadPhoto"
			enctype="multipart/form-data">
			<form:errors path="*" cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false" cssStyle="width: 400px;"></form:errors>			
			<div class="formField" >
				 <label>Select file to upload (Please less than 100 KB)...</label><br />	
				 <form:input id="fileupload" type="file" path="file"></form:input>
			</div>
			<div class="height10 clearFix"></div>
			<input type="submit" class="quakd_btn" value="Upload Picture" onclick="this.form.submit();" style="width : 250px;" />
		</form:form>		
	</div>

	<div class="height20"></div>
	<div class="height20"></div>
	<div class="height20"></div>
	<div class="height20"></div>

     <div class="header_border">
        Update Contact Information
    </div>  
	
	<div class="height20"></div>


	<div id="updateinfo" style="width: 500x;">
		<form:form id="updateInfoForm" method="post" action="${webapproot}/secure/updatemember" commandName="member">			
			<form:errors path="*" cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false" cssStyle="width: 400px;"></form:errors>		
			<div class="height20"></div>	
			<input type="hidden" name="username" value="test@quakd.com" />
			<input type="hidden" name="passText" value="testNotReal1#" />
			<input type="hidden" name="repassText" value="testNotReal1#" />					
			<div class="formField float" >
				<label>First Name</label><br />	
				<form:input id="firstName" path="firstName" size="20"></form:input>
			</div>				
			<div class="formField float" >
				<label>Last Name</label><br />			
				<form:input id="lastName" path="lastName" size="20" maxlength="45"></form:input>
			</div>
			<div class="height10 clearFix"></div>			
			<div class="formField">
				<label>Address</label><br />	
				<form:input id="address" path="address" size="40" maxlength="200"></form:input>
			</div>
			<div class="height10"></div>
			<div class="formField float" >
				<label>City</label><br />			
				<form:input id="city" path="city" size="20"></form:input>
			</div>							
			<div class="formField float">
				<label>State</label><br />		
				<form:select id="usStates" path="usStates.id" items="${applicationScope.PL_STATES}" itemLabel="label"  itemValue="value"></form:select>
			</div>
			<div class="height10 clearFix"></div>					
			<div class="formField float">
				<label>Country</label><br />			
				<form:select id="country" path="country.id" items="${applicationScope.PL_COUNTRY}" itemLabel="label"  itemValue="value"></form:select>				
			</div>					
			<div class="formField float">
				<label>Zip</label><br />	
				<form:input id="zip" path="zip" for="zip" size="10" maxlength="5"></form:input>
			</div>
			<div class="formField float">	
				<label>Phone</label><br />	
				<form:input id="phone1" path="phone1"  size="12" maxlength="11"></form:input>
			</div>
			<div class="height10 clearFix"></div>
			<div class="formField">
				<input type="submit" class="quakd_btn" value="Update Info" onclick="this.form.submit();" style="width : 250px;" />
			</div>
		</form:form>
	</div>
	
	<div class="height20 clearFix"></div>
	<div class="height20 clearFix"></div>
	<div class="height20 clearFix"></div>
	<div class="height20 clearFix"></div>
			
	<c:if test="${empty member.provider}">			
		<div class="header_border">
	        Update Password
	    </div>  
	    <div id="updatepassword" style="width: 500x;">
			<div class="height20 clearFix"></div>
			<form:form id="updatePassForm" method="post" action="${webapproot}/secure/updatepassword" commandName="password">	
				<form:errors path="*" cssClass="ui-state-error ui-corner-all" element="div" htmlEscape="false" cssStyle="width: 400px;"></form:errors>		 
				<div class="formField float" >
					<label>Password</label><br />	
					<form:input id="passText" path="passText" type="password" size="20" />
				</div>				
				<div class="formField float" >
					<label>Confirm Password</label><br />			
					<form:input id="repassText" path="repassText" type="password" size="20" maxlength="45" />
				</div>    	
				<div class="height20 clearFix"></div>
				<div class="formField">
					<input type="submit" class="quakd_btn" value="Change Password" onclick="this.form.submit();" style="width : 250px;" />
				</div>			
	    	</form:form>
	    </div>
    </c:if>
    
    <div class="height20"></div>
    <div class="height20"></div>