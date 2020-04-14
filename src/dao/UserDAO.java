package dao;

import java.util.List;

import domain.Login;
import domain.User;


/**
 * An interface to register a user and login a user.
 * implemented in UserDAOImpl
 * @author Anil
 *
 */
public interface UserDAO {
		void register(User u) throws Exception;
		User validateUser(Login login);
		User validateUser(String username, String password);
}
