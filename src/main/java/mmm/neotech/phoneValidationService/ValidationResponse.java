package mmm.neotech.phoneValidationService;

public class ValidationResponse {
    public boolean valid;
    public String countryCode;

    public ValidationResponse(boolean valid, String countryCode) {
        this.valid = valid;
        this.countryCode = countryCode;
    }
}
