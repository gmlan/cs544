package presentation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import presentation.cache.CacheService;
import presentation.data.CartItemData;
import presentation.data.ProductPres;

@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

	@Autowired
	@Qualifier("ProductCacheService")
	ProductSubsystem productSubsystem;
	
	@RequestMapping()
	public String viewShoppingCart(HttpSession session, ModelMap modelMap) {	
		return "shopping_cart";
	}
	
	@RequestMapping(value = { "/{id}" })
	public String removeCartItem(@PathVariable int id, HttpSession session){		
		List<CartItemData> cartDataItems = getCartDataItems(session);
		cartDataItems.remove(id);
		return "redirect:/shoppingcart";
	}
	
	@RequestMapping(value = "/{index}/{val}")
	public @ResponseBody String updateShoppingCart(HttpServletRequest request, @PathVariable int index, @PathVariable int val, HttpSession session) {
		String result = "true";
		
		List<CartItemData> cartDataItems = getCartDataItems(session);		
		
		int availQuantity = 1;
		try {
			int productId = cartDataItems.get(index).getProductId();			
			Product product = CacheService.execute(request, productSubsystem, "getProductFromId", new Object[] { productId });
			availQuantity = product.getQuantityAvail();
					
			 if(val > availQuantity){
				 throw new BusinessException("What you order excess the available quantity");
			 }
			
			//Pass rule check
			cartDataItems.get(index).setQuantity(val);
			
		} 
		catch (BusinessException e) {
			result = String.valueOf(availQuantity);
		}		
		
		return result;
	}
	
	/** Input Quantity Rule --> run Quantity Rules */
	@RequestMapping(value = "/check/{productId}/{quantity}")
	public @ResponseBody String checkAvailable(@PathVariable int productId, @PathVariable int quantity) {		
		String result = "true";
		/*try {
			ProductPres prodPres = browseSelectData.getSelectedProduct();
			
			if(prodPres.getId() != productId) 
				prodPres = browseSelectData.getProductById(productId);	
			
			browseAndSelectController.runQuantityRules(prodPres.getProduct(), String.valueOf(quantity));			
		} catch (BackendException e) {
			e.printStackTrace();
			result = "false";
		}	
		catch (BusinessException e) {
			e.printStackTrace();
			result = "false";
		}*/
		
		return result;
	}
	
	/** Add to Cart */
	@RequestMapping(value = "/add/{productId}")
	public String addToCartHandler(@PathVariable int productId, HttpServletRequest request) {
		
		List<CartItemData> cartDataItems = getCartDataItems(request.getSession());

		Product product = CacheService.execute(request, productSubsystem, "getProductFromId", new Object[] { productId });
		CartItemData itemData = new CartItemData();
		itemData.setItemName(product.getProductName());
		itemData.setPrice(product.getUnitPrice());
		itemData.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		itemData.setProductId(productId);
		cartDataItems.add(itemData);
		 
		return "shopping_cart";

	}
	
	@RequestMapping("/mycart")
	public String getSavedShoppingCart(HttpSession session, ModelMap modelMap) {
		
	/*	try {
			ShoppingCart cart = browseSelectData.retrieveSavedCart();
			List<CartItem> cartItems = cart.getCartItems();
			List<CartItemData> cartDataItems = getCartDataItems(session);
			cartDataItems.clear();
			cartDataItems.addAll(Util.cartItemsToCartItemData(cartItems));
		} catch (BackendException e) {
			e.printStackTrace();
		}
		*/
		return "shopping_cart";
	}
	
	
	
	@RequestMapping("/mysave")
	public String saveShoppingCart(HttpSession session, ModelMap modelMap) {		 
		/*try {
			List<CartItemData> cartDataItems = getCartDataItems(session);
			
			List<CartItemPres> cartItemPresList = cartDataItems
					.stream()
					.map(d -> {CartItemPres p = new CartItemPres(); p.setCartItem(d); return p;})
					.collect(Collectors.toList());
					
			browseAndSelectController.updateShoppingCartItems(Util.cartItemPresToCartItemList(cartItemPresList));
			browseAndSelectController.saveLiveCart();
		} catch (BackendException e) {
			e.printStackTrace();
		}		
		modelMap.addAttribute("savecompleted", true);		*/
		return "shopping_cart";
	}
	


	private List<CartItemData> getCartDataItems(HttpSession session){
		List<CartItemData> cartDataItems = (List<CartItemData>)session.getAttribute("cartDataItems");
		if(cartDataItems == null){
			cartDataItems =  new ArrayList<CartItemData>();
			session.setAttribute("cartDataItems", cartDataItems);	
		}
		
		return cartDataItems;
	}
}
