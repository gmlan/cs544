
package business.externalinterfaces;
import java.util.List;

import business.exceptions.BackendException;
import core.domain.QueryHelper;

public interface ProductSubsystem {
	
	/****************** Product *********************/
	public int readQuantityAvailable(Product product) throws BackendException;
	
	/** customized query **/
	public List<Product> findProducts(QueryHelper queryHelper);
	
	/** update Product */
	public void updateProduct(Product product) throws BackendException;
	    
    /** reads the product from the productid */
	public Product getProductFromId(Integer prodId) throws BackendException;
	
		
	/** gets a list of products from the database, based on catalog */
	public List<Product> getProductList(Catalog catalog) throws BackendException;
	
	/** gets a list of all products*/
	public List<Product> getProductList() throws BackendException;

	/** saves a new product obtained from user input */
	public int saveNewProduct(Product product) throws BackendException;

	/** deletes a product obtained from user input */
	public void deleteProduct(Product product) throws BackendException;
	
	
	
	/******************** Catalog *************************/
	
	/** retrieves catalog from database based on catalog id  */
    public Catalog getCatalogFromId(Integer catId) throws BackendException;

    /** retrieves all catalog from database */
    public List<Catalog> getCatalogList() throws BackendException;
    
	/** saves newly created catalog */
	public int saveNewCatalog(String catName) throws BackendException;
	
	/** deletes a catalog obtained from user input */
	public void deleteCatalog(Catalog catalog) throws BackendException;
	
	/** update Catalog */
	public void updateCatalog(Catalog catalog) throws BackendException;
	
}