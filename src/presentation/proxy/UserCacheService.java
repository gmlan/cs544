package presentation.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.User;
import business.externalinterfaces.UserSubsystem;
import presentation.cache.CacheConstants;
import presentation.cache.CacheLevel;
import presentation.cache.CacheSettings;

@Service("ProductCacheService")
public class UserCacheService implements UserSubsystem {

	@Autowired
	private UserSubsystem userSubsystem;
	
	public UserCacheService() {
	}

	@Override
	public int readQuantityAvailable(User user) throws BackendException {
		return userSubsystem.readQuantityAvailable(user);
	}

	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_PRODUCTS_ITEM, cacheLevel = CacheLevel.Application)
	public Product getUserFromId(Integer userId) throws BackendException {
		return userSubsystem.getUserFromId(userId);
	}
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_PRODUCTS_BY_CATALOG, cacheLevel = CacheLevel.Application)
	public List<Product> getUserList(Catalog catalog) throws BackendException {
		return userSubsystem.getUserList(catalog);
	}

	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_PRODUCTS, cacheLevel = CacheLevel.Application)
	public List<User> getUserList() throws BackendException {
		return userSubsystem.getUserList();
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_PRODUCTS}, cacheLevel = CacheLevel.Application)
	public int saveNewUser(User user) throws BackendException {
		return userSubsystem.saveNewUser(user);
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_PRODUCTS}, cacheLevel = CacheLevel.Application)
	public void deleteUser(User user) throws BackendException {
		userSubsystem.deleteUser(user);
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_PRODUCTS}, cacheLevel = CacheLevel.Application)
	public void updateUser(User user) throws BackendException {
		userSubsystem.updateUser(user);
	}
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_CATALOGS, cacheLevel = CacheLevel.Application)
	public List<Catalog> getCatalogList() throws BackendException {
		return userSubsystem.getCatalogList();
	}
	
	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_CATALOGS_ITEM, cacheLevel = CacheLevel.Application)
	public Catalog getCatalogFromId(Integer catId) throws BackendException {
		return userSubsystem.getCatalogFromId(catId);
	}
	
	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_CATALOGS}, cacheLevel = CacheLevel.Application)
	public int saveNewCatalog(String catName) throws BackendException {
		return userSubsystem.saveNewCatalog(catName);
	}
	
	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_CATALOGS}, cacheLevel = CacheLevel.Application)
	public void deleteCatalog(Catalog catalog) throws BackendException {
		userSubsystem.deleteCatalog(catalog);
	}

	@Override
	@CacheSettings(removeKeys = {CacheConstants.CACHE_CATALOGS}, cacheLevel = CacheLevel.Application)
	public void updateCatalog(Catalog catalog) throws BackendException {
		userSubsystem.updateCatalog(catalog);
	}
}
