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
		List<CartItemData> cartDataItems = (List<CartItemData>)request.getSession().getAttribute("cartDataItems");				
		/*List<CartItemPres> cartItemPresList = cartDataItems
				.stream()
				.map(d -> {CartItemPres p = new CartItemPres(); p.setCartItem(d); return p;})
				.collect(Collectors.toList());
				
		browseAndSelectController.updateShoppingCartItems(Util.cartItemPresToCartItemList(cartItemPresList));		
		 */
		
		
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

	/*	try{
			//check rules
			checkoutController.runAddressRules(checkoutModel.getShippingAddress());
			checkoutController.runAddressRules(checkoutModel.getBillingAddress());
		}
		catch(BusinessException e){
			request.setAttribute("exception", e.getMessage());
			return "checkout_address";
		}
		*/
		
		//Update live shopping cart address, should be completed in Checkout Controller
	//	checkoutController.setShippingAddress(checkoutModel.getShippingAddress());
	//	checkoutController.setBillingAddress(checkoutModel.getBillingAddress());
				
		//save shipping & billing info here
		checkoutModel.setCreditCard(orderSubsystem.createCreditCard());
		request.getSession().setAttribute("checkoutmodel", checkoutModel);
		return "getPaymentInfo";
	}
	
	@RequestMapping(value={"/myagreement"},method=RequestMethod.POST)
	public String getAgreement(HttpServletRequest request, @ModelAttribute("checkoutmodel") CheckoutModel checkoutModel){
		
		CheckoutModel checkoutModelInSession = (CheckoutModel) request.getSession().getAttribute("checkoutmodel");
//		try{
//			//For verify 
//			checkoutController.setPaymentInfo(checkoutModel.getCreditCard());
//			checkoutController.verifyCreditCard();
//			
//			//check rules							
//			checkoutController.runPaymentRules(checkoutModelInSession.getBillingAddress(), checkoutModel.getCreditCard());
//		}
//		catch(BusinessException e){
//			request.setAttribute("exception", e.getMessage());
//			return "getPaymentInfo";
//		}
		
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
		//Save order here
		/*try{
			//check rules
			checkoutController.runFinalOrderRules(null);				
		}
		catch(BusinessException e){
			request.setAttribute("exception", e.getMessage());
			return "final_order";
		}*/
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
		
		Order order = orderSubsystem.createOrder();
		order.setBillAddress(checkoutModelInSession.getBillingAddress());
		order.setShipAddress(checkoutModelInSession.getShippingAddress());
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
		
		/*try {
			
			//save order which will read all data from 
			orderSubsystem.submitOrder(new Orderrder);
			
			//Save address
			if(checkoutModelInSession.isSaveShippingAddress()){
				checkoutController.saveNewAddress(checkoutModelInSession.getShippingAddress());
			}
			
			if(checkoutModelInSession.isSaveBillingAddress()){
				checkoutController.saveNewAddress(checkoutModelInSession.getBillingAddress());
			}

			//refresh order history
			//viewOrdersData.refreshAfterSubmit();
			
			//Clear shopping car
			session.setAttribute("cartDataItems", null);
		} catch (BackendException e) {
			e.printStackTrace();
		}
		catch (BusinessException e) {
			e.printStackTrace();
		}*/
		
		return "submit_order";
	}
}
