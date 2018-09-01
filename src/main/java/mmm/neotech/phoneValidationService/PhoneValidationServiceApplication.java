package mmm.neotech.phoneValidationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PhoneValidationServiceApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext springContext = SpringApplication.run(
		        PhoneValidationServiceApplication.class,
                args
        );
	}
}
