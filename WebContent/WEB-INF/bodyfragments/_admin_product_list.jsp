<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="presentation.cache.CacheConstants" %>

<br>

<h3>Products List</h3>
<hr>
<table id="table">
	<thead>
		<tr>
			<th>Id</th>
			<th>Catalog</th>
			<th>Name</th>
			<th>Quantity</th>
			<th>Price</th>
			<th>MFG Date</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${products}" var="product">
			<tr>
				<td>${product.productId}</td>
				<td>${product.catalog.name}</td>
				<td>${product.productName}</td>
				<td>${product.quantityAvail}</td>
				<td>${product.unitPrice}</td>
				<td>${product.mfgDate}</td>
				<td><a href="product/${product.productId}" class="action">View</a>
				<a href="admin/products/edit/${product.productId}"class="action">Edit</a>
				<a href="admin/products/delete/${product.productId}" class="action">Delete</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
