<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="presentation.cache.CacheConstants" %>

 <script type="text/javascript">  
 $(document).ready(function() {
	 $("#billingCheckbox").on("click", function() {
		 if(this.checked==true){
			 $("#billingAddress").hide();
		    }else{
		    	$("#billingAddress").show();
		    }			 
	  });
	});
 
 </script>
<br>
<c:choose>
	<c:when test="${empty isEdit}">
		<h3>Register</h3>
	</c:when>
	<c:otherwise>
		<h3>Edit Profile</h3>
	</c:otherwise>
</c:choose>
<hr>
<form:form action="admin/user"  modelAttribute="newUser" class="form-horizontal" enctype="multipart/form-data">
		<fieldset>		

			<form:errors path="*" cssClass="alert alert-danger" element="div"/>
			 
			<form:input id="userId" path="userId" type="hidden"  />
			 
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">User Name</span>
			  <form:input id="userName" path="userName" type="text"  placeholder="User Name" class="form-control" aria-describedby="basic-addon1"/>			  
			</div><form:errors path="userName" cssClass="text-danger"/>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon2">Password</span>
			  <form:input id="password" path="password" type="text"  placeholder="Password" class="form-control" aria-describedby="basic-addon2"/>			  
			</div><form:errors path="password" cssClass="text-danger"/>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon3">First Name</span>
			  <form:input id="firstName" path="firstName" type="text"  class="form-control" aria-describedby="basic-addon3"/>
			  <form:errors path="firstName" cssClass="text-danger"/>
			</div>			
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon4">Last Name</span>
			  <form:input id="lastName" path="lastName" type="text"  class="form-control" aria-describedby="basic-addon4"/>
			  <form:errors path="lastName" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon4">Shipping Address</span>
			</div>
			<br>  
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon5">Street</span>
			  <form:input id="shippingStreet" path="shippingStreet" type="text" class="form-control" aria-describedby="basic-addon5"/>
			  <form:errors path="shippingStreet" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon6">City</span>
			  <form:input id="shippingCity" path="shippingCity" type="text" class="form-control" aria-describedby="basic-addon6"/>
			  <form:errors path="shippingCity" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon7">State</span>
			  <form:input id="shippingState" path="shippingState" type="text"  class="form-control" aria-describedby="basic-addon7"/>
			  <form:errors path="shippingState" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon8">Zip</span>
			  <form:input id="shippingZip" path="shippingZip" type="text" class="form-control" aria-describedby="basic-addon8"/>
			  <form:errors path="shippingZip" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon13">Billing Address</span>			  
			</div>
			<div class="checkbox">
  				<label><input type="checkbox" id="billingCheckbox" >Same as Shipping Address</label>
			</div> 
			<br>
			<div id="billingAddress">
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon9">Street</span>
				  <form:input id="billingStreet" path="billingStreet" type="text" class="form-control" aria-describedby="basic-addon9"/>
				  <form:errors path="billingStreet" cssClass="text-danger"/>
				</div>
				<br>
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon10">City</span>
				  <form:input id="billingCity" path="billingCity" type="text" class="form-control" aria-describedby="basic-addon10"/>
				  <form:errors path="billingCity" cssClass="text-danger"/>
				</div>
				<br>
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon11">State</span>
				  <form:input id="billingState" path="billingState" type="text" class="form-control" aria-describedby="basic-addon11"/>
				  <form:errors path="billingState" cssClass="text-danger"/>
				</div>
				<br>
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon12">Zip</span>
				  <form:input id="billingZip" path="billingZip" type="text" class="form-control" aria-describedby="basic-addon12"/>
				  <form:errors path="billingZip" cssClass="text-danger"/>
				</div>
				<br>
				<br>
			</div>
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<input type="submit" id="btnAdd" class="btn btn-secondary" value ="Add"/>
					</div>
				</div>
				<br> 
			
		</fieldset>
	</form:form>


