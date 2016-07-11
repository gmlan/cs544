package business.externalinterfaces;

import java.time.LocalDate;
import java.util.List;

public interface Order {
    public List<? extends OrderItem> getOrderItems();    
	public LocalDate getOrderDate();		
	public int getOrderId();		
	public double getTotalPrice();
    public void setOrderItems(List<? extends OrderItem> orderItems);
	public void setOrderId(int orderId);
	public Address getShipAddress();
    public Address getBillAddress();
    public CreditCard getPaymentInfo();
	public User getUser();
	public void setUser(User user);
    public void setDate(LocalDate date);
	public void setShipAddress(Address add);
	public void setBillAddress(Address add);
	public void setPaymentInfo(CreditCard cc);
}

    