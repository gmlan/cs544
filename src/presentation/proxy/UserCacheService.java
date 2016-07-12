package presentation.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.exceptions.BackendException;
import business.externalinterfaces.User;
import business.externalinterfaces.UserSubsystem;
import presentation.cache.CacheConstants;
import presentation.cache.CacheLevel;
import presentation.cache.CacheSettings;

@Service("UserCacheService")
public class UserCacheService implements UserSubsystem {

	@Autowired
	private UserSubsystem userSubsystem;
	
	public UserCacheService() {
	}

	@Override
	public void updateUser(User user) throws BackendException {
		userSubsystem.updateUser(user);
	}

	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_USERS_BY_ID, cacheLevel = CacheLevel.Application)
	public User getUserFromId(Integer userId) throws BackendException {
		return userSubsystem.getUserFromId(userId);
	}

	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_USERS, cacheLevel = CacheLevel.Application)
	public List<User> getUserList() throws BackendException {
		return userSubsystem.getUserList();
	}

	@Override
	public int saveNewUser(User user) throws BackendException {
		return userSubsystem.saveNewUser(user);
	}

	@Override
	public void deleteUser(User user) throws BackendException {
		userSubsystem.deleteUser(user);
	}

	@Override
	@CacheSettings(addKey = CacheConstants.CACHE_USERS_BY_ID, cacheLevel = CacheLevel.Application)
	public User getUserFromUsernameAndPassword(String username, String password) {
		return userSubsystem.getUserFromUsernameAndPassword(username, password);
	}
	
}
