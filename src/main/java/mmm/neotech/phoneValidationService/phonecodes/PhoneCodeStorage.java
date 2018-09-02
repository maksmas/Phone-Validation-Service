package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;

import java.util.*;

import static mmm.neotech.phoneValidationService.PhoneConstants.MAX_COUNTRY_CODE_LENGTH;
import static mmm.neotech.phoneValidationService.PhoneConstants.MIN_PHONE_LENGTH_WITHOUT_COUNTRY_CODE;

public class PhoneCodeStorage {
    private HashMap<String, List<Country>> state = new HashMap<>();

    private String bufferedPhoneCode = null;
    private List<Country> bufferedCountries = null;

    public List<Country> get(String phoneCode) {
        List<Country> countries = state.get(phoneCode);
        return countries == null ? Collections.emptyList() : countries;
    }

    public Optional<String> extractCountryCode(String phoneNumber) {
        int maxPossibleCodeLength = phoneNumber.length() - MIN_PHONE_LENGTH_WITHOUT_COUNTRY_CODE;

        if (maxPossibleCodeLength > MAX_COUNTRY_CODE_LENGTH) {
            maxPossibleCodeLength = MAX_COUNTRY_CODE_LENGTH;
        }

        for (int i = maxPossibleCodeLength; i > 1; --i) {
            String possibleCode = phoneNumber.substring(0, i);

            if (state.containsKey(possibleCode)) {
                return Optional.of(possibleCode);
            }
        }

        return Optional.empty();
    }

    public void addPhoneCode(String phoneCode) {
        flush();
        phoneCode = phoneCode.replace(" ", "");
        bufferedPhoneCode = phoneCode;
    }

    public void addCountry(String countryCode, String countryTitle) {
        bufferedCountries.add(new Country(countryCode, countryTitle));
    }

    public void flush() {
        if (bufferedPhoneCode != null) {
            if (state.containsKey(bufferedPhoneCode)) {
                throw new IllegalStateException("Code " + bufferedPhoneCode + " already stored");
            }

            state.put(bufferedPhoneCode, bufferedCountries);
        }

        bufferedPhoneCode = null;
        bufferedCountries = new ArrayList<>();
    }
}
