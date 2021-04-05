package pl.edu.agh.ki.io.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = SqlProtectionValidator.class)
@Documented
public @interface SimpleSqlProtected {
    String message() default "Look's like SQL Injection";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
