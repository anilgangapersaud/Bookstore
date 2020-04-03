package domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import validation.EqualPasswords;

/**
 * Domain object to collect data from Registration form.
 *
 */
public class Registration {

	@Valid
	private User registrationUser;
	@Valid
	private Billing billing;
	@Valid
	private Address address;
	
	public User getRegistrationUser() {
		return registrationUser;
	}
	
	@NotNull
	public String getUsername() {
		return registrationUser.getUsername();
	}
	
	public void setUsername(String username) {
		this.registrationUser.setUsername(username);
	}
	
	public String getPassword() {
		return registrationUser.getPassword();
	}
	
	public void setPassword(String password) {
		registrationUser.setPassword(password);
	}
	
	public String getConfirmPassword() {
		return registrationUser.getConfirmPassword();
	}
	
	public void setConfirmPassword(String s) {
		this.registrationUser.setConfirmPassword(s);
	}
	
	public String getFirstName() {
		return registrationUser.getFirstname();
	}
	
	public void setFirstName(String firstname) {
		this.registrationUser.setFirstname(firstname);
	}
	
	public String getLastName() {
		return registrationUser.getLastname();
	}
	
	public void setLastName(String lastname) {
		this.registrationUser.setLastname(lastname);
	}
	
	public String getEmail() {
		return registrationUser.getEmail();
	}
	
	public void setEmail(String email) {
		this.registrationUser.setEmail(email);
	}
	
	public String getRole() {
		return registrationUser.getRole();
	}
	
	public void setRole(String role) {
		this.registrationUser.setRole(role);
	}
	
	public String getStreet() {
		return address.getStreet();
	}
	
	public void setStreet(String street) {
		this.address.setStreet(street);
	}
	
	public String getProvince() {
		return address.getProvince();
	}
	
	public void setProvince(String province) {
		this.address.setProvince(province);
	}
	
	public String getCountry() {
		return address.getCountry();
	}
	
	public void setCountry(String country) {
		this.address.setCountry(country);
	}
	
	public String getPostalCode() {
		return address.getPostalCode();
	}
	
	public void setPostalCode(String zip) {
		this.address.setPostalCode(zip);
	}
	
	public String getPhone() {
		return address.getPhone();
	}
	
	public void setPhone(String phone) {
		this.address.setPhone(phone);
	}
	
	public void setRegistrationUser(User registrationUser) {
		this.registrationUser = registrationUser;
	}
	
	public String getCardholder() {
		return billing.getCardholderName();
	}
	
	public void setCardholder(String cardholder) {
		this.billing.setCardholderName(cardholder);
	}
	
	public String getCardNumber() {
		return billing.getCardNumber();
	}
	
	public void setCardNumber(String cardNumber) {
		this.billing.setCardNumber(cardNumber);
	}
	
	public String getCardType() {
		return billing.getCardType();
	}
	
	public void setCardType(String cardtype) {
		this.billing.setCardType(cardtype);
	}
	
	public String getExpDate() {
		return billing.getExpDate();
	}
	
	public void setExpDate(String exp) {
		this.billing.setExpDate(exp);
	}
	
	
	public Billing getBilling() {
		return billing;
	}
	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
