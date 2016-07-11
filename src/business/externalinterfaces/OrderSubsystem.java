
package business.externalinterfaces;

import java.util.List;

import business.exceptions.BackendException;


public interface OrderSubsystem {
    List<Order> getOrderHistory() throws BackendException;

    void submitOrder(Order order) throws BackendException;
    
    Order createOrder();
    
    OrderItem createOrderItem();
    
    Address createAddress();
    
    CreditCard createCreditCard();
}
