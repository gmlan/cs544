package business.ordersubsystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import business.exceptions.BackendException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.externalinterfaces.OrderSubsystem;
import business.users.AddressImpl;
import business.users.CreditCardImpl;


@Service
@Transactional
public class OrderSubsystemFacade implements OrderSubsystem {
 
	@Autowired
	OrderDAO orderDAO;
	
	@Override
	public List<Order> getOrderHistory() throws BackendException {
		return null;
	}
	
	@Override
	public Order createOrder() {
		return new OrderImpl();
	};

	@Override
	public void submitOrder(Order order) throws BackendException {
		orderDAO.save(OrderImpl.clone(order));
	}

	@Override
	public OrderItem createOrderItem() {
		return new OrderItemImpl();
	}

	@Override
	public Address createAddress() {
		Address address =  new AddressImpl();
		address.setCity("Test City");
		address.setState("Test State");
		address.setStreet("Test street");
		address.setZip("Test zip");
		address.setId(1);
		return address;
	}

	@Override
	public CreditCard createCreditCard() {
		return new CreditCardImpl(1, "Name on credit","Expiration Date","cardNumber","Card Type");
	}
}
