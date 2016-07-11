package business.productsubsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;


@Service
@Transactional
public class ProductSubsystemFacade implements ProductSubsystem {
	
	@Autowired 
	ProductDAO productDAO;
	
	@Autowired
	CatalogDAO catalogDAO;
	/*
	public static Catalog createCatalog(int id, String name) {
		return new CatalogImpl(id, name); 
	}
 
	public static Product createProduct(Catalog c, String name, 
			LocalDate date, int numAvail, double price) {
		return new ProductImpl(c, name, date, numAvail, price);
	}
	public static Product createProduct(Catalog c, Integer pi, String pn, int qa, 
			double up, LocalDate md, String desc) {
		return new ProductImpl(c, pi, pn, qa, up, md, desc);
	}
*/	
	@Override
	public int readQuantityAvailable(Product product) throws BackendException {
		Product p  = productDAO.findObjectById(product.getProductId());
		return p.getQuantityAvail();
    }
	
	@Override
    public Product getProductFromId(Integer prodId) throws BackendException {
		return productDAO.findObjectById(prodId);
	}
	@Override
    public List<Catalog> getCatalogList() throws BackendException {
		return new ArrayList<>(catalogDAO.findObjects());
    }
	 
	
	@Override
    public List<Product> getProductList(Catalog catalog) throws BackendException {
		List<Object> params = new ArrayList<>();
		params.add(catalog.getId());
		return new ArrayList<>(productDAO.findObjects("from ProductImpl p where p.catalog.id=?", params));
    }
	
	@Override
    public List<Product> getProductList() throws BackendException {
		List<Object> params = new ArrayList<>();
		return new ArrayList<>(productDAO.findObjects("from ProductImpl", params));
    }
		
	@Override
	public Catalog getCatalogFromId(Integer catId) throws BackendException {
		return catalogDAO.findObjectById(catId);	
	}
	
	@Override
	public int saveNewCatalog(String catName) throws BackendException {
		CatalogImpl catalog = new CatalogImpl();
		catalog.setName(catName);
		catalogDAO.save(catalog);
		return catalog.getId();
	}
	
	@Override
	public int saveNewProduct(Product product) throws BackendException {
		ProductImpl productImpl = ProductImpl.Clone(product);
		productDAO.save(productImpl);
		product.setProductId(productImpl.getProductId());
		return productImpl.getProductId();
	}

	@Override
	public void deleteProduct(Product product) throws BackendException {
		productDAO.delete(product.getProductId());
	}
	@Override
	public void deleteCatalog(Catalog catalog) throws BackendException {
		catalogDAO.delete(catalog.getId());
	}
	
	@Override
	public void updateCatalog(Catalog catalog) throws BackendException {
		catalogDAO.update(CatalogImpl.Clone(catalog));
	}
	
	@Override
	public void updateProduct(Product product) throws BackendException {
		productDAO.update(ProductImpl.Clone(product));
	}
}
