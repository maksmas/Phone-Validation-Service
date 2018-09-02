package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;

import java.util.Collections;
import java.util.List;

public class ValidationResponse {
    public boolean valid;
    public String errorMessage;
    public List<Country> countries;

    // for json deserialization
    public ValidationResponse() {}

    public ValidationResponse(List<Country> countries) {
        this.valid = true;
        this.countries = countries;
    }

    public ValidationResponse(String errorMessage) {
        this.valid = false;
        this.countries = Collections.emptyList();
        this.errorMessage = errorMessage;
    }
}
