package pl.edu.agh.ki.io.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlProtectionValidator implements ConstraintValidator<SimpleSqlProtected, String> {

    private static final String MALICIOUS_PATTERN = "(SELECT|REMOVE|DROP|INSERT|<--|-->|;) ";

    @Override
    public void initialize(SimpleSqlProtected constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return validateString(s);
    }

    private boolean validateString(String s){
        Pattern pattern = Pattern.compile(MALICIOUS_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.matches();
    }
}
