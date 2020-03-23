package dao;

import java.util.List;

import domain.Login;
import domain.User;

public interface UserDAO {
		void register(User u) throws Exception;
		User validateUser(Login login);
}
