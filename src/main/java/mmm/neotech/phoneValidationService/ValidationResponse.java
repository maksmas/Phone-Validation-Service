package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;

import java.util.List;

public class ValidationResponse {
    public boolean valid;
    public List<Country> countries;

    public ValidationResponse(boolean valid, List<Country> countries) {
        this.valid = valid;
        this.countries = countries;
    }
}
