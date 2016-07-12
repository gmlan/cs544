package business.usersubsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import business.exceptions.BackendException;
import business.externalinterfaces.User;
import business.externalinterfaces.UserSubsystem;

@Service
@Transactional
public class UserSubsystemFacade implements UserSubsystem {

	@Autowired
	private UserDAO userDAO;

	@Override
	public void updateUser(User user) throws BackendException {
		userDAO.update(UserImpl.clone(user));
	}

	@Override
	public List<User> getUserList() throws BackendException {
		List<Object> params = new ArrayList<>();
		return new ArrayList<>(userDAO.findObjects("from UserImpl", params));
	}

	@Override
	public int saveNewUser(User user) throws BackendException {
		UserImpl userImpl = UserImpl.clone(user);
		userDAO.save(userImpl);
		user.setId(userImpl.getId());
		return userImpl.getId();
	}

	@Override
	public void deleteUser(User user) throws BackendException {
		userDAO.delete(user.getId());
	}

	@Override
	public User getUserFromId(Integer userId) throws BackendException {
		return userDAO.findObjectById(userId);
	}

	@Override
	public User getUserFromUsernameAndPassword(String username, String password) {
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(password);
		List<UserImpl> users = userDAO.findObjects("from UserImpl u where u.username=? and u.password=?", params);
		return users.size() > 0 ? users.get(0) : null;
	}

}
