package presentation.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import core.domain.QueryHelper;
import presentation.cache.CacheConstants;
import presentation.cache.CacheLevel;
import presentation.cache.CacheSettings;

@Service("ProductCacheService")
public class ProductCacheService implements ProductSubsystem {

	@Autowired
	private ProductSubsystem productSubsystem;
	
	public ProductCacheService() {
	}
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_PRODUCTS_ITEM, cacheLevel = CacheLevel.Application)
	public List<Product> findProducts(QueryHelper queryHelper) {
		return productSubsystem.findProducts(queryHelper);
	}

	@Override
	public int readQuantityAvailable(Product product) throws BackendException {
		return productSubsystem.readQuantityAvailable(product);
	}

	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_PRODUCTS_ITEM, cacheLevel = CacheLevel.Application)
	public Product getProductFromId(Integer prodId) throws BackendException {
		return productSubsystem.getProductFromId(prodId);
	}
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_PRODUCTS_BY_CATALOG, cacheLevel = CacheLevel.Application)
	public List<Product> getProductList(Catalog catalog) throws BackendException {
		return productSubsystem.getProductList(catalog);
	}

	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_PRODUCTS, cacheLevel = CacheLevel.Application)
	public List<Product> getProductList() throws BackendException {
		return productSubsystem.getProductList();
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_PRODUCTS}, cacheLevel = CacheLevel.Application)
	public int saveNewProduct(Product product) throws BackendException {
		return productSubsystem.saveNewProduct(product);
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_PRODUCTS}, cacheLevel = CacheLevel.Application)
	public void deleteProduct(Product product) throws BackendException {
		productSubsystem.deleteProduct(product);
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_PRODUCTS}, cacheLevel = CacheLevel.Application)
	public void updateProduct(Product product) throws BackendException {
		productSubsystem.updateProduct(product);
	}
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_CATALOGS, cacheLevel = CacheLevel.Application)
	public List<Catalog> getCatalogList() throws BackendException {
		return productSubsystem.getCatalogList();
	}
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_CATALOGS_ITEM, cacheLevel = CacheLevel.Application)
	public Catalog getCatalogFromId(Integer catId) throws BackendException {
		return productSubsystem.getCatalogFromId(catId);
	}
	
	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_CATALOGS}, cacheLevel = CacheLevel.Application)
	public int saveNewCatalog(String catName) throws BackendException {
		return productSubsystem.saveNewCatalog(catName);
	}
	
	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_CATALOGS}, cacheLevel = CacheLevel.Application)
	public void deleteCatalog(Catalog catalog) throws BackendException {
		productSubsystem.deleteCatalog(catalog);
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_CATALOGS}, cacheLevel = CacheLevel.Application)
	public void updateCatalog(Catalog catalog) throws BackendException {
		productSubsystem.updateCatalog(catalog);
	}
}
