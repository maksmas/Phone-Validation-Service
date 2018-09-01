package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PhoneCodeStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneCodeStorage.class);
    private HashMap<String, List<Country>> state = new HashMap<>();

    private String bufferedPhoneCode = null;
    private List<Country> bufferedCountries = null;

    public List<Country> get(String phoneCode) {
        List<Country> countries = state.get(phoneCode);
        return countries == null ? Collections.emptyList() : countries;
    }

    public void addItemToBuffer(String code, String title) {
        if (code != null && !code.equals("+")) {
            code = code.replaceAll(" ","");

            if (isPhoneCode(code)) {
                flush();
                bufferedPhoneCode = code;
            } else if (code.length() > 0) {
                bufferedCountries.add(new Country(code, title));
            }
        } else {
            LOGGER.warn("Wrong inputs to addItemToBuffer code: '" + code + "', title: '" + title + "'");
        }
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

    private boolean isPhoneCode(String code) {
        return code.startsWith("+");
    }
}
