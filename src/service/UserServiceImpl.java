package service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDAO;
import domain.Login;
import domain.User;

@Service
public class UserServiceImpl implements UserService {

		@Autowired
		public UserDAO userdao;
		
		public int register(User user) throws Exception {
			int result = -1;
			try {
				userdao.register(user);
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
				return 1;
			}
		}
		
		public User validateUser(Login login) {
			return userdao.validateUser(login);
		}

		@Override
		public List<User> getUserList() {
			// TODO Auto-generated method stub
			return null;
		}

}
