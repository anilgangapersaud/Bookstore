package dao;

import model.Login;
import model.User;

public interface UserDAO {
		void register(User user) throws Exception;
		User validateUser(Login login);
}
