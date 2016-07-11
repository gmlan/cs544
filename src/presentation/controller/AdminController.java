package presentation.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import presentation.cache.CacheConstants;
import presentation.cache.CacheLevel;
import presentation.cache.CacheService;
import presentation.data.ProductPres;
import presentation.data.UserPres;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	@Qualifier("ProductCacheService")
	ProductSubsystem productSubsystem;

	@RequestMapping
	public String index(HttpServletRequest request) {
		return "admin_home";
	}

	@RequestMapping("/catalog")
	public String catalog(HttpServletRequest request){
		CacheService.execute(request, productSubsystem, "getCatalogList");
		return "admin_catalog";
	}

	@RequestMapping(value = "/catalog", method = RequestMethod.POST)
	public String addCatalog(HttpServletRequest request, ModelMap modelMap){
		CacheService.execute(request, productSubsystem, "saveNewCatalog", new Object[]{ request.getParameter("name")});
		return "redirect:/admin/catalog";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/catalog/{id}")
	public String deleteCatalog(@PathVariable int id, HttpServletRequest request) throws BackendException {
		List<Catalog> catalogs = (List<Catalog>) request.getServletContext().getAttribute(CacheConstants.CACHE_CATALOGS);
		for (Catalog catalog : catalogs) {
			if (catalog.getId() == id) {
				CacheService.execute(request, productSubsystem, "deleteCatalog", new Object[]{catalog});
				break;
			}
		}
		return "redirect:/admin/catalog";
	}

	@RequestMapping("/products")
	public String getAllProducts(HttpServletRequest request, ModelMap modelMap){
		List<Product> products = CacheService.execute(request, productSubsystem, "getProductList");		
		modelMap.addAttribute("products", products);
		return "admin_product_list";
	}

	@RequestMapping("/product")
	public String product(ModelMap modelMap) {
		modelMap.addAttribute("newProduct", new ProductPres());
		return "admin_product_add";
	}
	
	@RequestMapping("/users")
	public String getAllUsers(HttpServletRequest request, ModelMap modelMap){
		List<Product> products = CacheService.execute(request, productSubsystem, "getProductList");		
		modelMap.addAttribute("products", products);
		return "admin_user_list";
	}

	@RequestMapping("/user")
	public String user(ModelMap modelMap) {
		modelMap.addAttribute("newUser", new UserPres());
		return "admin_user_add";
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String processProductForm(@ModelAttribute("newProduct") @Valid ProductPres productToBeAdded,
			BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "admin_product_add";
		}

		String[] suppressedFields = result.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: "
					+ StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}

		int productId = 0;

		List<Catalog> catalogs = CacheService.queryCache(request, CacheLevel.Application,
				CacheConstants.CACHE_CATALOGS);
		Optional<Catalog> catalog = catalogs.stream()
				.filter(item -> productToBeAdded.getCatalogId() == item.getId()).findFirst();

		productToBeAdded.setCatalog(catalog.orElse(null));

		if (productToBeAdded.getProductId() != 0) {
			CacheService.execute(request, productSubsystem, "updateProduct", new Object[]{productToBeAdded});
		} else {
			productId = CacheService.execute(request, productSubsystem, "saveNewProduct", new Object[]{productToBeAdded});
		}

		MultipartFile productImage = productToBeAdded.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(rootDirectory + "resources\\images\\" + productId + ".png"));
			} catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		} 
		
		//TODO: need to remove CACHE_PRODUCTS_BY_CATALOG cache
		
		return "redirect:/admin/products";
	}

	@RequestMapping(value = "/products/edit/{id}")
	public String editProduct(@PathVariable int id, HttpServletRequest request, ModelMap modelMap) {
		Product product = null;
		product = CacheService.execute(request, productSubsystem, "getProductFromId", new Object[]{id});
		modelMap.addAttribute("newProduct", ProductPres.Clone(product));
		modelMap.addAttribute("isEdit", true);

		return "admin_product_add";
	}

	@RequestMapping(value = "/products/delete/{id}")
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) {
		ProductPres product = new ProductPres(id);			
		CacheService.execute(request, productSubsystem, "deleteProduct", new Object[]{ product });
		
		//TODO: need to remove CACHE_PRODUCTS_BY_CATALOG cache
		
		return "redirect:/admin/products";
	}

}
