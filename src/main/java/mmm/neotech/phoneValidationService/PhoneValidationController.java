package mmm.neotech.phoneValidationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneValidationController {

    @GetMapping(value = "/validate")
    public ValidationResponse validatePhone(@RequestParam(name = "phone", required = true) String phone) {
        // todo implement
        return new ValidationResponse(phone.equals("1"), "LV");
    }
}
