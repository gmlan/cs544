package presentation.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.exceptions.BackendException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.User;
import presentation.cache.CacheConstants;
import presentation.cache.CacheLevel;
import presentation.cache.CacheSettings;

@Service("OrderCacheService")
public class OrderCacheService implements OrderSubsystem {

	@Autowired
	OrderSubsystem orderSubsystem;
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_ORDER_HISTORY, cacheLevel = CacheLevel.Session)
	public List<Order> getOrderHistory(User user) throws BackendException {
		return orderSubsystem.getOrderHistory(user);
	}

	@Override
	@CacheSettings(removeKeys = { CacheConstants.CACHE_ORDER_HISTORY }, cacheLevel = CacheLevel.Session)
	public void submitOrder(Order order) throws BackendException {
		orderSubsystem.submitOrder(order);
	}

	@Override
	public Order createOrder() {
		return orderSubsystem.createOrder();
	}

	@Override
	public OrderItem createOrderItem() {
		return orderSubsystem.createOrderItem();
	}

	@Override
	public Address createAddress() {
		return orderSubsystem.createAddress();
	}

	@Override
	public CreditCard createCreditCard() {
		return orderSubsystem.createCreditCard();
	}

}
