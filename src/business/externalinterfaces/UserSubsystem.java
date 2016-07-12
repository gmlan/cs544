
package business.externalinterfaces;
import java.util.List;

import business.exceptions.BackendException;

public interface UserSubsystem {
	
	/** update User */
	public void updateUser(User user) throws BackendException;
	    
    /** reads the User from the userid */
	public User getUserFromId(Integer userId) throws BackendException;
	
	/** gets a list of all users*/
	public List<User> getUserList() throws BackendException;

	/** saves a new user */
	public int saveNewUser(User user) throws BackendException;

	/** deletes a user */
	public void deleteUser(User user) throws BackendException;
	
	
}