package test.books.demo.settings;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Settings {
    private double simLevel = 0.5;
}
