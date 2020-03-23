package service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;

import dao.UserDAO;
import model.Login;
import model.User;

public class UserServiceImpl implements UserService {

		@Autowired
		public UserDAO userdao;
		
		public int register(User user) throws Exception {
			int result = -1;
			try {
				userdao.register(user);
				return 0;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 1;
			}
		}
		
		public User validateUser(Login login) {
			return userdao.validateUser(login);
		}
}
