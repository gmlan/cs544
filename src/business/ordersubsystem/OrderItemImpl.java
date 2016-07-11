package business.ordersubsystem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import business.externalinterfaces.OrderItem;


@Entity
class OrderItemImpl implements OrderItem {
	@Id
	@GeneratedValue
	private int orderItemId;	

	private int orderId;
	private String productName;
	private int productId;
	private int quantity;
	private double unitPrice;
	
	public OrderItemImpl(String name, int quantity, double price) {
		productName = name;
		this.quantity = quantity;
		this.unitPrice = price;
	}
	
	public OrderItemImpl(){
		
	}
	
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int itemID) {
		this.orderItemId = itemID;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderID) {
		this.orderId = orderID;
	}


	public String getProductName() {
		return productName;
	}
	public void setProductName(String n) {
		productName = n;
	}


	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int q) {
		quantity = q;
	}


	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double price) {
		unitPrice = price;
	}

	public double getTotalPrice() {
		return unitPrice * quantity;
	}


	@Override
	public int getProductId() {
		return productId;
	}

	@Override
	public void setProductId(int id) {
		productId = id;
	}
	
	public static OrderItemImpl clone(OrderItem orderItem) {
		OrderItemImpl orderItemImpl = new OrderItemImpl();
		orderItemImpl.setOrderId(orderItem.getOrderId());
		orderItemImpl.setOrderItemId(orderItem.getOrderItemId());
		orderItemImpl.setProductId(orderItem.getProductId());
		orderItemImpl.setProductName(orderItem.getProductName());
		orderItemImpl.setQuantity(orderItem.getQuantity());
		orderItemImpl.setUnitPrice(orderItem.getUnitPrice());
		return orderItemImpl;
	}
}
