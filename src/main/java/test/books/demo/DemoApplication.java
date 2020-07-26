package test.books.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
			}

			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/{spring:\\w+}")
						.setViewName("forward:/");
				registry.addViewController("/**/{spring:\\w+}")
						.setViewName("forward:/");
				registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
						.setViewName("forward:/");
			}
		};
	}
}
