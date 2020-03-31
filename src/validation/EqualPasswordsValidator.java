package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import domain.Registration;
import domain.User;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, User>{
	
	@Override
	public void initialize(EqualPasswords constraint) {
		
	}

	/**
	 * checks if password and confirm password are equal.
	 * @author Anil
	 */
	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		return user.getPassword().equals(user.getConfirmPassword());
	}
}
