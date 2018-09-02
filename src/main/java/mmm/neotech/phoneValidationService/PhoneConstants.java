package mmm.neotech.phoneValidationService;

public interface PhoneConstants {
    // 1 for '+', 3 for country code, 3 for area code
    Integer MAX_COUNTRY_CODE_LENGTH = 7;

    // 1 for '+', 1 for country code
    Integer MIN_COUNTRY_CODE_LENGTH = 2;

    // https://en.wikipedia.org/wiki/E.164
    Integer MAX_PHONE_LENGTH = 15;

    // https://en.wikipedia.org/wiki/List_of_mobile_telephone_prefixes_by_country
    Integer MIN_PHONE_LENGTH_WITHOUT_COUNTRY_CODE = 4;

    Integer MIN_PHONE_LENGTH = MIN_PHONE_LENGTH_WITHOUT_COUNTRY_CODE + MIN_COUNTRY_CODE_LENGTH;

    String[] ALLOWED_SPECIAL_SYMBOLS = new String[] { "-", "(", ")", "_", " ", "," };
}
