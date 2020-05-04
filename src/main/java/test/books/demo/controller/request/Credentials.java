package test.books.demo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.books.demo.controller.validation.annotation.ValidCredentials;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ValidCredentials
public class Credentials {
    private String email;
    private String password;
}
