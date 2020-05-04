package test.books.demo.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {

    @Test
    void validate() {
        List<String> validEmails = List.of(
                "user@domain.com",
                "user@domain.co.in",
                "user1@domain.com",
                "user.name@domain.com",
                "user_name@domain.co.in",
                "user#@domain.co.in",
                "username@yahoo.corporate.in"
        );
        List<String> inValidEmails = List.of(
                "user#domain.com",
                "@yahoo.com",
                ".username@yahoo.com",
                "username@yahoo.com.",
                "username@yahoo..com",
                "username@yahoo.c",
                "username@yahoo.corporate",
                "user@domaincom"
        );

        for (String email : validEmails) {
            assertTrue(new EmailValidator(email).validate(), email);
        }
        for (String email : inValidEmails) {
            assertFalse(new EmailValidator(email).validate(), email);
        }
    }
}