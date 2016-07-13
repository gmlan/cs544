package presentation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.externalinterfaces.Order;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.User;
import presentation.cache.CacheService;
import presentation.util.Constants;

@Controller
public class OrderController {

	@Autowired
	@Qualifier("OrderCacheService")
	OrderSubsystem orderSubsystem;
	 
	
	@RequestMapping(value = "/myorder", method = {RequestMethod.GET})
	public String index(Model model, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USERINFO);
		List<Order> orders = CacheService.execute(request, orderSubsystem, "getOrderHistory", new Object[]{ user });
		model.addAttribute("orders", orders);	
		return "myorder";
	}
	
	@RequestMapping(value = "/myorder/{id}", method = {RequestMethod.GET})
	public String getOrder(@PathVariable int id, Model model, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USERINFO);
		List<Order> orders = CacheService.execute(request, orderSubsystem, "getOrderHistory", new Object[]{ user });
		model.addAttribute("orderItems",orders
				.stream()
				.filter(item->item.getOrderId() == id)
				.findFirst()
				.orElseGet(null)
				.getOrderItems());
		
		return "myorder_details";
	}
	

}
