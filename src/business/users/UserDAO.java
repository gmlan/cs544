package business.users;

import org.springframework.stereotype.Repository;

import business.externalinterfaces.User;
import core.dao.impl.BaseDaoImpl;

@Repository
public class UserDAO extends BaseDaoImpl<User> {

}
