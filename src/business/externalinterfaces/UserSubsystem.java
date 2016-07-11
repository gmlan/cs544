
package business.externalinterfaces;
import java.util.List;

import business.exceptions.BackendException;

public interface UserSubsystem {
	
	/****************** User *********************/
	public int readQuantityAvailable(User user) throws BackendException;
	
	/** update User */
	public void updateUser(User user) throws BackendException;
	    
    /** reads the User from the userid */
	public Product getUserFromId(Integer userId) throws BackendException;
	
		
	/** gets a list of users from the database, based on catalog */
	public List<Product> getUserList(Catalog catalog) throws BackendException;
	
	/** gets a list of all users*/
	public List<User> getUserList() throws BackendException;

	/** saves a new user */
	public int saveNewUser(User user) throws BackendException;

	/** deletes a user */
	public void deleteUser(User user) throws BackendException;
	
	
	
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