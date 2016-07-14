package presentation.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.User;
import business.externalinterfaces.UserSubsystem;
import middleware.creditverifcation.CreditVerificationFacade;
import middleware.exceptions.MiddlewareException;
import middleware.externalinterfaces.CreditVerification;
import middleware.externalinterfaces.CreditVerificationProfile;
import presentation.cache.CacheService;
import presentation.data.CartItemData;
import presentation.data.CheckoutModel;
import presentation.util.Constants;
import presentation.util.MailSender;

@Controller
public class CheckoutController {

	@Autowired
	@Qualifier("OrderCacheService")
	OrderSubsystem orderSubsystem;	

	@Autowired
	@Qualifier("UserCacheService")
	UserSubsystem userSubsystem;

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
		try{
			//For verify 
			User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USERINFO);
			checkoutModel.setBillingAddress(checkoutModelInSession.getBillingAddress());
			checkCreditCard(user, checkoutModel, getTotal(request.getSession()));
			
			//check rules							
			//checkoutController.runPaymentRules(checkoutModelInSession.getBillingAddress(), checkoutModel.getCreditCard());
		}
		catch(BusinessException e){
			request.setAttribute("exception", e.getMessage());
			return "getPaymentInfo";
		}
		
				
		checkoutModelInSession.setCreditCard(checkoutModel.getCreditCard());
		return "agreement_form";
	}

	@RequestMapping(value={"/myordersummary"})
	public String summaryOrder(HttpSession session){
		//Calculate total		
		session.setAttribute("total", getTotal(session));
		return "final_order";
	}
	
	@RequestMapping(value={"/myplaceorder"})
	public String placeOrder(HttpServletRequest request){		
		HttpSession session = request.getSession();
		List<CartItemData> cartDataItems = (List<CartItemData>)session.getAttribute("cartDataItems");
		CheckoutModel checkoutModelInSession = (CheckoutModel) session.getAttribute("checkoutmodel");
		
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USERINFO);
		Address shippingAddress = checkoutModelInSession.getShippingAddress();
		if(checkoutModelInSession.isSaveShippingAddress()){	
			//set new default shipping address
			user.setDefaultShippingAddress(shippingAddress);			
		}
		
		Address billingAddress = checkoutModelInSession.getBillingAddress();
		if(checkoutModelInSession.isSaveBillingAddress()){
			//set new default shipping address
			user.setDefaultBillingAddress(billingAddress);
		}
		
		user.addShippingAddress(user.getDefaultShippingAddress());
		user.addBillingAddress(user.getDefaultBillingAddress());
		
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItemData itemData : cartDataItems) {
			OrderItem orderItem = orderSubsystem.createOrderItem();
			orderItem.setProductName(itemData.getItemName());
			orderItem.setProductId(itemData.getProductId());
			orderItem.setQuantity(itemData.getQuantity());
			orderItem.setUnitPrice(itemData.getPrice());
			orderItems.add(orderItem);
		}
		
		Order order = orderSubsystem.createOrder();
		order.setUser(user);		
		order.setPaymentInfo(checkoutModelInSession.getCreditCard());		
		order.setDate(LocalDate.now());		
		order.setOrderItems(orderItems);
		order.setShipAddress(shippingAddress);
		order.setBillAddress(billingAddress);
				
		int orderId = CacheService.execute(request, orderSubsystem, "submitOrder", new Object[]{order});
		
		//Notification
		MailSender.send(user.getUsername(),String.format("Order %s confirmed", orderId), "You order has been confirmed! Please goto http://eshopping.cfapps.io/ for details.");
		
		
		//Clear shopping cart
		session.setAttribute("cartDataItems", null);
		 
		return "submit_order";
	}
	
	private double getTotal(HttpSession session){
		double total = 0;
		List<CartItemData> cartDataItems = (List<CartItemData>)session.getAttribute("cartDataItems");
		for (CartItemData cartItemData : cartDataItems) {
			total += cartItemData.getTotalPrice();
		}
		return total;
	}
	
	public void checkCreditCard(User user, CheckoutModel model, double total) throws BusinessException {
		Address billAddr = model.getBillingAddress();
		CreditCard cc = model.getCreditCard();
		double amount = total;
		CreditVerification creditVerif = new CreditVerificationFacade();
		try {
			CreditVerificationProfile profile = CreditVerificationFacade.getCreditProfileShell();
			profile.setFirstName(user.getFirstname());
			profile.setLastName(user.getLastname());
			profile.setAmount(amount);
			profile.setStreet(billAddr.getStreet());
			profile.setCity(billAddr.getCity());
			profile.setState(billAddr.getState());
			profile.setZip(billAddr.getZip());
			profile.setCardNum(cc.getCardNum());
			profile.setExpirationDate(cc.getExpirationDate());
			creditVerif.checkCreditCard(profile);
		} catch (MiddlewareException e) {
			throw new BusinessException(e);
		}
	}
}
