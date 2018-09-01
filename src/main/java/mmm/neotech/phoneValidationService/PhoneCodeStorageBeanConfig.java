package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.phonecodes.PhoneCodeStorageFactory;
import mmm.neotech.phoneValidationService.phonecodes.PhoneCodeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhoneCodeStorageBeanConfig {
    private final PhoneCodeStorageFactory service;

    @Autowired
    public PhoneCodeStorageBeanConfig(PhoneCodeStorageFactory service) {
        this.service = service;
    }

    @Bean
    public PhoneCodeStorage initCountryCodeStorage() {
        return service.createStorage();
    }
}
