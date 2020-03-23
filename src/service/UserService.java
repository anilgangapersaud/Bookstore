package service;

import model.Login;
import model.User;

public interface UserService {

		int register(User user) throws Exception;
		
		User validateUser(Login login);
}
