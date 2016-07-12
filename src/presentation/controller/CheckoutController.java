package presentation.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.exceptions.BackendException;
import business.externalinterfaces.Address;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.User;
import business.usersubsystem.UserImpl;
import presentation.data.CartItemData;
import presentation.data.CheckoutModel;
import presentation.util.Constants;

@Controller
public class CheckoutController {
 
	@Autowired
	OrderSubsystem orderSubsystem;

	@RequestMapping(value = { "/mycheckout" }, method = RequestMethod.GET)
	public String checkoutCart(ModelMap modelMap,HttpServletRequest request) throws BackendException{				
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USERINFO);		
		CheckoutModel checkoutModel = new CheckoutModel();
		checkoutModel.setShippingAddress(user.getDefaultShippingAddress());
		checkoutModel.setBillingAddress(user.getDefaultBillingAddress());
		
		modelMap.addAttribute("checkoutmodel", checkoutModel);		
		modelMap.addAttribute("allShipAddresses", user.getShippingAddress());
		modelMap.addAttribute("allBillAddresses", user.getBillingAddress());
		return "checkout_address";
	}
	
	@RequestMapping(value = {"/mypaymentinfo"}, method= RequestMethod.POST)
	public String getPaymentInfo(ModelMap modelMap, @ModelAttribute("checkoutmodel") CheckoutModel checkoutModel, HttpServletRequest request){		 
		//save shipping & billing info here
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USERINFO);
		checkoutModel.setCreditCard(user.getDefaultCreditCard());
		request.getSession().setAttribute("checkoutmodel", checkoutModel);
		return "getPaymentInfo";
	}
	
	@RequestMapping(value={"/myagreement"},method=RequestMethod.POST)
	public String getAgreement(HttpServletRequest request, @ModelAttribute("checkoutmodel") CheckoutModel checkoutModel){
		CheckoutModel checkoutModelInSession = (CheckoutModel) request.getSession().getAttribute("checkoutmodel");
		checkoutModelInSession.setCreditCard(checkoutModel.getCreditCard());
		return "agreement_form";
	}

	@RequestMapping(value={"/myordersummary"})
	public String summaryOrder(HttpSession session){
		//Calculate total
		double total = 0;
		List<CartItemData> cartDataItems = (List<CartItemData>)session.getAttribute("cartDataItems");
		for (CartItemData cartItemData : cartDataItems) {
			total += cartItemData.getTotalPrice();
		}		
		session.setAttribute("total", total);
		return "final_order";
	}
	
	@RequestMapping(value={"/myplaceorder"})
	public String placeOrder(HttpServletRequest request){		
		HttpSession session = request.getSession();
		List<CartItemData> cartDataItems = (List<CartItemData>)session.getAttribute("cartDataItems");
		CheckoutModel checkoutModelInSession = (CheckoutModel) session.getAttribute("checkoutmodel");
		
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItemData itemData : cartDataItems) {
			OrderItem orderItem = orderSubsystem.createOrderItem();
			orderItem.setProductId(itemData.getProductId());
			orderItem.setQuantity(itemData.getQuantity());
			orderItem.setUnitPrice(itemData.getPrice());
			orderItems.add(orderItem);
		}
				
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USERINFO);
		Address shippingAddress = checkoutModelInSession.getShippingAddress();
		if(checkoutModelInSession.isSaveShippingAddress()){
			//bind to user
			user.setDefaultShippingAddress(shippingAddress);
			
		}
		
		Address billingAddress = checkoutModelInSession.getBillingAddress();
		if(checkoutModelInSession.isSaveBillingAddress()){
			//bind to user
			user.setDefaultBillingAddress(billingAddress);
		}
	
		Order order = orderSubsystem.createOrder();
		order.setUser(user);
		order.setBillAddress(billingAddress);
		order.setShipAddress(shippingAddress);
		order.setPaymentInfo(checkoutModelInSession.getCreditCard());		
		order.setDate(LocalDate.now());		
		order.setOrderItems(orderItems);
		
		try {
			orderSubsystem.submitOrder(order);
		} catch (BackendException e) {
			e.printStackTrace();
		}
		
		//Clear shopping cart
		session.setAttribute("cartDataItems", null);
		 
		return "submit_order";
	}
}
