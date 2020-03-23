package service;

import java.util.List;

import domain.Login;
import domain.User;

public interface UserService {

		public static final Integer ROLE_ADMIN=1;
		public static final Integer ROLE_USER=2;
		public static final Integer ROLE_PARTNER=3;
		
		int register(User user) throws Exception;
		
		User validateUser(Login login);
		
		/**
		 * 
		 *Call this method to get list of all users.
		 * @return
		 */
		List<User> getUserList();
}
