package test.books.demo.util;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class EmailValidator {
    private String email;
    private String validationMessage;

    public EmailValidator(String email) {
        this.email = email;
    }

    public boolean validate() {
        email = email.toLowerCase();

        if (email.isBlank()) {
            validationMessage = "Email is empty.";
            return false;
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            validationMessage = "E-Mail is not valid. Check the format email@mail.com";
            return false;
        }

        if (email.endsWith("test.com")) {
            validationMessage = "E-Mails from test.com are invalid.";
            return false;
        }

        return true;
    }
}
