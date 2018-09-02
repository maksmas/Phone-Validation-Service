package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneValidationServiceTest {
    @Autowired
    private PhoneValidationService phoneValidationService;

    @Test
    public void doesNotAllowNulls() {
        ValidationResponse response = phoneValidationService.validatePhoneNumber(null);
        assertValidationResponseWrong(response, ErrorMessages.NO_PHONE);
    }

    @Test
    public void doesNotAllowTooLongPhones() {
        ValidationResponse response = phoneValidationService.validatePhoneNumber("+3712900000000000");
        assertValidationResponseWrong(response, ErrorMessages.TOO_LONG);
    }

    @Test
    public void doesNotAllowTooShortPhones() {
        ValidationResponse response = phoneValidationService.validatePhoneNumber("+1234");
        assertValidationResponseWrong(response, ErrorMessages.TOO_SHORT);
    }

    @Test
    public void allowsOnlyInternationalPhones() {
        ValidationResponse response = phoneValidationService.validatePhoneNumber("3712900000");
        assertValidationResponseWrong(response, ErrorMessages.WRONG_FORMAT);

        response = phoneValidationService.validatePhoneNumber("371+2900000");
        assertValidationResponseWrong(response, ErrorMessages.WRONG_FORMAT);
    }

    @Test
    public void doesNotAllowLetters() {
        ValidationResponse response = phoneValidationService.validatePhoneNumber("+371290000a");
        assertValidationResponseWrong(response, ErrorMessages.WRONG_FORMAT);
    }

    @Test
    public void doesNotAllowUnassignedCountryCodes() {
        ValidationResponse response = phoneValidationService.validatePhoneNumber("+28123456");
        assertValidationResponseWrong(response, ErrorMessages.WRONG_COUNTRY_CODE);
    }

    @Test
    public void returnsValidResponseForCorrectNumbers() {
        ValidationResponse response = phoneValidationService.validatePhoneNumber("+3712900000");

        assertTrue(response.valid);
        assertNull(response.errorMessage);
        assertEquals(response.countries.size(), 1);
        assertEquals(response.countries.get(0), new Country("LV", "Latvia"));
    }

    private void assertValidationResponseWrong(ValidationResponse response, ErrorMessages error) {
        assertFalse(response.valid);
        assertEquals(response.countries, Collections.emptyList());
        assertEquals(response.errorMessage, error.message);
    }
}
