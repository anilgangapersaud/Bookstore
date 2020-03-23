package model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, User>{
	
	@Override
	public void initialize(EqualPasswords constraint) {
		
	}

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		return user.getPassword().equals(user.getConfirmPassword());
	}
}
