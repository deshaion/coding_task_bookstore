package test.books.demo.controller.validation.annotation;

import test.books.demo.controller.validation.ValidCredentialsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidCredentialsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCredentials {
    String message() default "Credentials are not valid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
