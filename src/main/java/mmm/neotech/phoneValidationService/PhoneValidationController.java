package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.phonecodes.PhoneCodeStorage;
import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneValidationController {
    private final PhoneCodeStorage storage;

    @Autowired
    public PhoneValidationController(PhoneCodeStorage storage) {
        this.storage = storage;
    }

    @GetMapping(value = "/validate")
    public ValidationResponse validatePhone(@RequestParam(name = "phone") String phone) {
        // todo validate phone
        List<Country> countries = storage.get(phone);
        return new ValidationResponse(!countries.isEmpty(), countries);
    }
}
