package pl.edu.agh.ki.io.validation;

import pl.edu.agh.ki.io.api.models.CreateUserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        CreateUserRequest userRequest = (CreateUserRequest) o;
        return userRequest.getPassword().equals(userRequest.getMatchingPassword());
    }
}
