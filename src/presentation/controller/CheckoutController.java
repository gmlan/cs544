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
import presentation.data.CartItemData;
import presentation.data.CheckoutModel;

@Controller
public class CheckoutController {
 
	@Autowired
	OrderSubsystem orderSubsystem;

	@RequestMapping(value = { "/mycheckout" }, method = RequestMethod.GET)
	public String checkoutCart(ModelMap modelMap,HttpServletRequest request) throws BackendException{				
		//Update live shopping cart with session data		
		Address defaultShipAddress = orderSubsystem.createAddress(); // checkoutData.getDefaultShippingData();
		Address defaultBillAddress = orderSubsystem.createAddress();// checkoutData.getDefaultBillingData();
		
		CheckoutModel checkoutModel = new CheckoutModel();
		checkoutModel.setShippingAddress(defaultShipAddress);
		checkoutModel.setBillingAddress(defaultBillAddress);
		
		modelMap.addAttribute("checkoutmodel", checkoutModel);
		
		
		List<Address> addresses = new ArrayList<>();
		addresses.add(orderSubsystem.createAddress());
		addresses.add(orderSubsystem.createAddress());
		
		
		modelMap.addAttribute("allShipAddresses", addresses);
		modelMap.addAttribute("allBillAddresses", addresses);
		return "checkout_address";
	}
	
	@RequestMapping(value = {"/mypaymentinfo"}, method= RequestMethod.POST)
	public String getPaymentInfo(ModelMap modelMap, @ModelAttribute("checkoutmodel") CheckoutModel checkoutModel, HttpServletRequest request){		 
		//save shipping & billing info here
		checkoutModel.setCreditCard(orderSubsystem.createCreditCard());
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
		
		Address shippingAddress = checkoutModelInSession.getShippingAddress();
		if(checkoutModelInSession.isSaveShippingAddress()){
			//bind to user
		}
		
		Address billingAddress = checkoutModelInSession.getBillingAddress();
		if(checkoutModelInSession.isSaveBillingAddress()){
			//bind to user
		}
		
		Order order = orderSubsystem.createOrder();
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
		
		//Clear shopping car
		session.setAttribute("cartDataItems", null);
		 
		return "submit_order";
	}
}
