package domain;

import javax.validation.constraints.Pattern;

public class Address {

	private Integer addressid;
	private Integer userid;
	private String street;
	private String province;
	
	@Pattern(regexp="Canada", message="We only deliver in Canada.")
	private String country;
	
	@Pattern(regexp="^[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]$", message="Enter a valid postal code.")
	private String postalCode;
	
	@Pattern(regexp="^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$", message="Enter a valid phone number.")
	private String phone;

	public Integer getAddressid() {
		return addressid;
	}

	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}
