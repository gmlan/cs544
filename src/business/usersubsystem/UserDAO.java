package business.usersubsystem;

import org.springframework.stereotype.Repository;

import core.dao.impl.BaseDaoImpl;

@Repository
public class UserDAO extends BaseDaoImpl<UserImpl> {
	
	public UserDAO() {
	}
}
