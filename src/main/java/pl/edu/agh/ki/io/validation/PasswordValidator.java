package pl.edu.agh.ki.io.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator  implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return validatePassword(password);
    }

    private boolean validatePassword(String password) {
        boolean hasNumeric = false;
        boolean hasSpecialCharacter = false;
        boolean hasCapitalLetter = false;
        for(char character : password.toCharArray()) {
            if(isNumeric(character)) hasNumeric = true;
            if(isSpecialCharacter(character)) hasSpecialCharacter = true;
            if(isCapitalLetter(character)) hasCapitalLetter = true;
        }

        return hasNumeric && hasCapitalLetter && hasSpecialCharacter;
    }

    public static boolean isNumeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    public static boolean isSpecialCharacter(char ch) {
        return (ch >= '!' && ch <= '/') || (ch >= ':' && ch <= '@')
                || (ch >= '[' && ch <= '`') || (ch >= '{' && ch <= '~');
    }

    public static boolean isCapitalLetter(char ch) {
        return (ch >= 'A' && ch <= 'Z');
    }
}
