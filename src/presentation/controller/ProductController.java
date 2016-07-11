package presentation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import presentation.cache.CacheConstants;
import presentation.cache.CacheService;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("ProductCacheService")
	ProductSubsystem productSubsystem;

	/** do Online purchase */
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		List<Catalog> catalogs = CacheService.execute(request, productSubsystem, "getCatalogList");
		if(catalogs.size() > 0){
			List<Product> products = CacheService.execute(request, productSubsystem, "getProductList", new Object[] { catalogs.get(0) });
			request.getServletContext().setAttribute(CacheConstants.CACHE_PRODUCTS_HOT, products);
		}
		
		return "home";
	}

	/** View Product List */
	@RequestMapping(value = "/catalog/{catalogId}", method = RequestMethod.GET)
	public String viewProductListHandler(HttpServletRequest request, @PathVariable int catalogId, ModelMap modelMap) {
		List<Product> products = null;
		Catalog catalog = CacheService.execute(request, productSubsystem, "getCatalogFromId", new Object[] { catalogId });
		products = CacheService.execute(request, productSubsystem, "getProductList", new Object[] { catalog });
		modelMap.addAttribute("products", products);

		return "product_list";
	}

	/** View Product Details */
	@RequestMapping("/product/{id}")
	public String viewProductDetailHandler(HttpServletRequest request, @PathVariable int id, ModelMap modelMap) {
		Product product = CacheService.execute(request, productSubsystem, "getProductFromId", new Object[] { id });
		modelMap.addAttribute("product", product);
		
		return "product_details";
	}

}
