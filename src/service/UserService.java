package service;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import domain.Address;
import domain.Billing;
import domain.Book;
import domain.Login;
import domain.User;

public interface UserService {

		public static final String ROLE_ADMIN="Admin";
		public static final String ROLE_USER="Customer";
		public static final String ROLE_PARTNER="Partner";
		
		void register(User user) throws Exception;
		User validateUser(Login login);
		public void createAddress(Address a);
		public void updateAddress(Address a);
		public void deleteAddress(int addressid);
		public Address findById(int addressid);
		public List<Address> findAllAddress();
		public List<Address> findByPropertyAddress(String propName, Object propValue);
		public void createBilling(Billing b);
		public void updateBilling(Billing b);
		public void deleteBilling(Integer cardid);
		public Billing findByBillingId(int id);
		public List<Billing> findAllBilling();
		public List<Billing> findByPropertyBilling(String propName, Object propValue);
		
}
