package test.books.demo.controller.validation;

import test.books.demo.controller.request.Credentials;
import test.books.demo.controller.validation.annotation.ValidCredentials;
import test.books.demo.util.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCredentialsValidator implements ConstraintValidator<ValidCredentials, Credentials> {
    @Override
    public void initialize(ValidCredentials constraintAnnotation) {

    }

    @Override
    public boolean isValid(Credentials credentials, ConstraintValidatorContext constraintContext) {
        boolean isValid = true;

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("${{");

        EmailValidator emailValidator = new EmailValidator(credentials.getEmail());
        if (!emailValidator.validate()) {
            isValid = false;
            errorMessage.append(emailValidator.getValidationMessage());
        }

        if (credentials.getPassword().isBlank()) {
            if (!isValid) {
                errorMessage.append(' ');
            }
            errorMessage.append("Password is empty.");
            isValid = false;
        }

        if (!isValid) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(errorMessage.append("}}").toString()).addConstraintViolation();
        }

        return isValid;
    }
}
