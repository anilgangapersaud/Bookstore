package domain;

import javax.validation.Valid;

/**
 * Domain object to collect data from Checkout form.
 *
 */
public class Checkout {

	@Valid
	private Billing billing;
	
	@Valid
	private Address address;
	
	public String getCardType() {
		return billing.getCardType();
	}
	public void setCardType(String cardType) {
		this.billing.setCardType(cardType);
	}
	public String getCardNumber() {
		return billing.getCardNumber();
	}
	public void setCardNumber(String cardNumber) {
		this.billing.setCardNumber(cardNumber);
	}
	public String getExpDate() {
		return billing.getExpDate();
	}
	public void setExpDate(String expDate) {
		this.billing.setExpDate(expDate);
	}
	public String getCardholderName() {
		return billing.getCardholderName();
	}
	public void setCardholderName(String cardholderName) {
		this.billing.setCardholderName(cardholderName);
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
	public String getPostalCode() {
		return address.getPostalCode();
	}

	public void setPostalCode(String postalCode) {
		this.setPostalCode(postalCode);
	}

	public String getPhone() {
		return address.getPhone();
	}

	public void setPhone(String phone) {
		this.address.setPhone(phone);
	}

}
