package business.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import business.externalinterfaces.User;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public User getUser(int id) {
		return userDAO.findObjectById(id);
	}

	public List<User> getAllUsers() {
		return userDAO.findObjects();
	}
	
	
	public void updateUser(User user){
		userDAO.update(user);
	}
	
	public void delete(int id){
		userDAO.delete(id);
	}

}
