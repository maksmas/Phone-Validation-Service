package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.countrycodes.CountryCodeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class PhoneValidationServiceApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext springContext = SpringApplication.run(
		        PhoneValidationServiceApplication.class,
                args
        );
	}
}
