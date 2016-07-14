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
 $(function () {
     $('#mfg').datepicker();
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
<form:form action="register"  modelAttribute="newUser" class="form-horizontal" enctype="multipart/form-data">
		<fieldset>		

			<form:errors path="*" cssClass="alert alert-danger" element="div"/>
			 
			<form:input id="id" path="id" type="hidden"  />
			 
			 <div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">User Name</span>
			  <form:input id="username" path="username" type="text"  placeholder="User Name" class="form-control" aria-describedby="basic-addon1"/>			  
			</div><form:errors path="username" cssClass="text-danger"/>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon2">Password</span>
			  <form:input id="password" path="password" type="text"  placeholder="Password" class="form-control" aria-describedby="basic-addon2"/>			  
			</div><form:errors path="password" cssClass="text-danger"/>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon3">First Name</span>
			  <form:input id="firstname" path="firstname" type="text"  class="form-control" aria-describedby="basic-addon3"/>
			  <form:errors path="firstname" cssClass="text-danger"/>
			</div>			
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon4">Last Name</span>
			  <form:input id="lastname" path="lastname" type="text"  class="form-control" aria-describedby="basic-addon4"/>
			  <form:errors path="lastname" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon4">CreditCard Details</span>
			</div>
			<br>  
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon5">Card Number</span>
			  <form:input id="cardNum" path="defaultCreditCard.cardNum" type="text" class="form-control" aria-describedby="basic-addon5"/>
			  <form:errors path="defaultCreditCard.cardNum" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon6">Name On Card</span>
			  <form:input id="nameOnCard" path="defaultCreditCard.nameOnCard" type="text" class="form-control" aria-describedby="basic-addon6"/>
			  <form:errors path="defaultCreditCard.nameOnCard" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon7">Expiration Date</span>
			  <form:input id="mfg" path="defaultCreditCard.expirationDate" type="text"  placeholder="MM/DD/YYYY" class="form-control" aria-describedby="basic-addon7"/>
			  <form:errors path="defaultCreditCard.expirationDate" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon8">Card Type</span>
			  <form:input id="cardType" path="defaultCreditCard.cardType" type="text" class="form-control" aria-describedby="basic-addon8"/>
			  <form:errors path="defaultCreditCard.cardType" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon4">Shipping Address</span>
			</div>
			<br>  
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon5">Street</span>
			  <form:input id="shippingStreet" path="defaultShippingAddress.street" type="text" class="form-control" aria-describedby="basic-addon5"/>
			  <form:errors path="defaultShippingAddress.street" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon6">City</span>
			  <form:input id="shippingCity" path="defaultShippingAddress.city" type="text" class="form-control" aria-describedby="basic-addon6"/>
			  <form:errors path="defaultShippingAddress.city" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon7">State</span>
			  <form:input id="shippingState" path="defaultShippingAddress.state" type="text"  class="form-control" aria-describedby="basic-addon7"/>
			  <form:errors path="defaultShippingAddress.state" cssClass="text-danger"/>
			</div>
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon8">Zip</span>
			  <form:input id="shippingZip" path="defaultShippingAddress.zip" type="text" class="form-control" aria-describedby="basic-addon8"/>
			  <form:errors path="defaultShippingAddress.zip" cssClass="text-danger"/>
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
				  <form:input id="billingStreet" path="defaultBillingAddress.street" type="text" class="form-control" aria-describedby="basic-addon9"/>
				  <form:errors path="defaultBillingAddress.street" cssClass="text-danger"/>
				</div>
				<br>
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon10">City</span>
				  <form:input id="billingCity" path="defaultBillingAddress.city" type="text" class="form-control" aria-describedby="basic-addon10"/>
				  <form:errors path="defaultBillingAddress.city" cssClass="text-danger"/>
				</div>
				<br>
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon11">State</span>
				  <form:input id="billingState" path="defaultBillingAddress.state" type="text" class="form-control" aria-describedby="basic-addon11"/>
				  <form:errors path="defaultBillingAddress.state" cssClass="text-danger"/>
				</div>
				<br>
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon12">Zip</span>
				  <form:input id="billingZip" path="defaultBillingAddress.zip" type="text" class="form-control" aria-describedby="basic-addon12"/>
				  <form:errors path="defaultBillingAddress.zip" cssClass="text-danger"/>
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


