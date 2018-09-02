package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.phonecodes.PhoneCodeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static mmm.neotech.phoneValidationService.PhoneConstants.*;

@Component
public class PhoneValidationService {
    private final PhoneCodeStorage phoneCodeStorage;

    @Autowired
    public PhoneValidationService(PhoneCodeStorage phoneCodeStorage) {
        this.phoneCodeStorage = phoneCodeStorage;
    }

    public ValidationResponse validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return new ValidationResponse(ErrorMessages.NO_PHONE.message);
        }
        phoneNumber = removeAllowedSymbols(phoneNumber);

        if (!phoneNumber.matches("^\\+\\d+")) {
            return new ValidationResponse(ErrorMessages.WRONG_FORMAT.message);
        }

        if (phoneNumber.length() > MAX_PHONE_LENGTH + 1) {
            return new ValidationResponse(ErrorMessages.TOO_LONG.message);
        }

        if (phoneNumber.length() < MIN_PHONE_LENGTH) {
            return new ValidationResponse(ErrorMessages.TOO_SHORT.message);
        }

        Optional<String> phoneCode = phoneCodeStorage.extractCountryCode(phoneNumber);

        if (phoneCode.isPresent()) {
            return new ValidationResponse(phoneCodeStorage.get(phoneCode.get()));
        } else {
            return new ValidationResponse(ErrorMessages.WRONG_COUNTRY_CODE.message);
        }
    }

    private String removeAllowedSymbols(String phoneNumber) {
        for (String allowedSymbol : ALLOWED_SPECIAL_SYMBOLS) {
            phoneNumber = phoneNumber.replace(allowedSymbol, "");
        }
        return phoneNumber;
    }
}
