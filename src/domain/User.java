package domain;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import validation.EqualPasswords;


/**
 * Domain object to represent a User Entity.
 *
 */
@EqualPasswords(message="Passwords do not match.")
public class User {

	private Integer userId;
	
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
	
	@NotBlank(message="Must re-type password.")
	private String confirmPassword;
	

	private String role;
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	
}
