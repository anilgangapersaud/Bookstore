package service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AddressDAO;
import dao.BillingDAO;
import dao.UserDAO;
import domain.Address;
import domain.Billing;
import domain.Login;
import domain.User;

@Service
public class UserServiceImpl implements UserService {

		@Autowired
		public UserDAO userdao;
		
		@Autowired 
		public AddressDAO addressdao;
		
		@Autowired
		public BillingDAO billingdao;
		
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
		public void createAddress(Address a) {
			addressdao.save(a);
		}

		@Override
		public void updateAddress(Address a) {
			addressdao.update(a);
		}

		@Override
		public void deleteAddress(int addressid) {
			addressdao.delete(addressid);
		}

		@Override
		public Address findById(int addressid) {
			return addressdao.findById(addressid);
		}

		@Override
		public List<Address> findAllAddress() {
			return addressdao.findAll();
		}

		@Override
		public List<Address> findByPropertyAddress(String propName, Object propValue) {
			return addressdao.findByProperty(propName, propValue);
		}


		@Override
		public void createBilling(Billing b) {
			billingdao.save(b);
			
		}

		@Override
		public void updateBilling(Billing b) {
			billingdao.update(b);
		}

		@Override
		public void deleteBilling(Integer cardid) {
			billingdao.delete(cardid);
		}

		@Override
		public Billing findById(String id) {
			return billingdao.findById(id);
		}

		@Override
		public List<Billing> findAllBilling() {
			return billingdao.findAll();
		}

		@Override
		public List<Billing> findByPropertyBilling(String propName, Object propValue) {
			return billingdao.findByProperty(propName, propValue);
		}

		

	

}
