package mmm.neotech.phoneValidationService;

public enum ErrorMessages {
    NO_PHONE ("No phone provided"),
    WRONG_FORMAT ("Wrong phone format. Only international numbers allowed"),
    TOO_LONG ("Too long"),
    TOO_SHORT("Too short"),
    WRONG_COUNTRY_CODE ("Wrong county code");

    public final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
