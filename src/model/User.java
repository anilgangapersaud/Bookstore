package model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@EqualPasswords(message="Passwords do not match.")
public class User {

	@NotBlank(message="Username can't be empty")
	private String username;
	
	@Size(min=8, message="Password must be at least 8 characters.")
	private String password;
	
	@NotBlank(message="Must enter a first name.")
	private String firstname;
	
	@NotBlank(message="Must enter a last name.")
	private String lastname;
	
	@Email(message="Enter a valid email address")
	private String email;
	
	private String address;
	
	@Pattern(regexp="^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$", message="Enter a valid phone number.")
	private String phone;
	
	private String city;
	
	@Pattern(regexp="Canada", message="We only deliver in Canada.")
	private String country;
	
	@Pattern(regexp="^[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]$", message="Enter a valid postal code.")
	private String postalCode;
	
	private String cardType;
	
	@NotBlank(message="Enter the cardholder name.")
	private String cardholder;
	
	@NotBlank(message="Enter the cardnumber.")
	private String cardnumber;
	
	@Pattern(regexp="^[0-1][0-9][0-9][0-9]$", message="Invalid Expiration Format")
	private String expDate;
	
	private String province;
	
	
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	} 
	
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	@NotBlank(message="Must re-type password.")
	private String confirmPassword;
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
