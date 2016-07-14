<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="presentation.cache.CacheConstants" %>

<br>

<h3>Users List</h3>
<hr>
<table id="table">
	<thead>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Shipping Address</th>
			<th>Billing Address</th>
			<th>CreditCard Details</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.firstname} ${user.lastname}</td>
				<td>${user.defaultShippingAddress.state} ${user.defaultShippingAddress.city} ${user.defaultShippingAddress.street} 
				${user.defaultShippingAddress.zip}</td>
				<td>${user.defaultBillingAddress.state} ${user.defaultBillingAddress.city} ${user.defaultBillingAddress.street} 
				${user.defaultBillingAddress.zip}</td>
				<td>${user.defaultCreditCard.nameOnCard} ${user.defaultCreditCard.cardType} ${user.defaultCreditCard.cardNum} 
				${user.defaultCreditCard.expirationDate}</td>
				<td><a href="user/${user.id}" class="action">View</a>
				<a href="admin/user/edit/${user.id}"class="action">Edit</a>
				<a href="admin/user/delete/${user.id}" class="action">Delete</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
