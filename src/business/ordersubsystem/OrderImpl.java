package business.ordersubsystem;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.users.AddressImpl;
import business.users.CreditCardImpl;

@Entity
class OrderImpl implements Order {
	@Id
	@GeneratedValue
	private int orderId;
	
	@OneToMany	
	private List<OrderItemImpl> orderItems;	
	
	private LocalDate date;	
	private double totalPriceAmount;
	
	@OneToOne
	private AddressImpl shipAddr;
	
	@OneToOne
	private AddressImpl billAddr;
	
	@OneToOne
	private CreditCardImpl creditCard;
	
	public double getTotalPriceAmount() {
		return totalPriceAmount;
	}

	public void setTotalPriceAmount(double totalPriceAmount) {
		this.totalPriceAmount = totalPriceAmount;
	}

	public OrderImpl() {
		date = LocalDate.now();
	}

	public List<? extends OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<? extends OrderItem> orderItems) {
		this.orderItems = orderItems
				.stream()
				.map(item -> OrderItemImpl.clone(item))
				.collect(Collectors.toList());
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public double getTotalPrice() {
		if(orderItems == null) {
			return 0.0;
		} else {
			 DoubleSummaryStatistics summary 
			    = orderItems.stream().collect(
				    Collectors.summarizingDouble(
					   (OrderItem item) -> item.getUnitPrice() * item.getQuantity()));
			 return summary.getSum();
		}
	}
	
	public LocalDate getOrderDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	

	@Override
	public Address getShipAddress() {
		return shipAddr;
	}

	@Override
	public Address getBillAddress() {
		return billAddr;
	}

	@Override
	public CreditCard getPaymentInfo() {
		return creditCard;
	}

	public void setShipAddress(Address add) {
		shipAddr = AddressImpl.clone(add);		
	}

	public void setBillAddress(Address add) {
		billAddr = AddressImpl.clone(add);		
	}

	public void setPaymentInfo(CreditCard cc) {
		creditCard = CreditCardImpl.clone(cc);		
	}
	
	public static OrderImpl clone(Order order) {
		OrderImpl orderImpl = new OrderImpl();
		orderImpl.setDate(order.getOrderDate());;
		orderImpl.setOrderItems(order.getOrderItems());
		orderImpl.setPaymentInfo(order.getPaymentInfo());
		orderImpl.setBillAddress(order.getBillAddress());
		orderImpl.setShipAddress(order.getShipAddress());
		orderImpl.setTotalPriceAmount(order.getTotalPrice());
		return orderImpl;
	}
}
