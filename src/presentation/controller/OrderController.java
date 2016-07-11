package presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {

	 
	
	@RequestMapping(value = "/myorder", method = {RequestMethod.GET})
	public String index(Model model) {
		//model.addAttribute("orders",viewOrdersData.getOrders());
		return "myorder";
	}
	
	@RequestMapping(value = "/myorder/{id}", method = {RequestMethod.GET})
	public String getOrder(@PathVariable int id, Model model) {
		/*model.addAttribute("orderItems",viewOrdersData
				.getOrders()
				.stream()
				.filter(item->item.getOrderId() == id)
				.findFirst()
				.orElseGet(null)
				.getOrderItems());*/
		return "myorder_details";
	}
	

}
