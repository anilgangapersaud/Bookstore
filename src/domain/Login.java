package domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Domain object to collect data from Login form.
 *
 */
public class Login {

	@NotBlank(message="Username can't be empty")
	private String username;
	@Size(min=8, message="Password must be at least 8 characters")
	private String password;
	
	
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
	
	
}
