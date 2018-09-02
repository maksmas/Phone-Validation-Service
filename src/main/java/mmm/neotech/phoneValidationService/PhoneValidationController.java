package mmm.neotech.phoneValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneValidationController {
    private final PhoneValidationService service;

    @Autowired
    public PhoneValidationController(PhoneValidationService service) {
        this.service = service;
    }

    @GetMapping(value = "/validate")
    public ValidationResponse validatePhone(@RequestParam(name = "phone") String phone) {
        return service.validatePhoneNumber(phone);
    }
}
