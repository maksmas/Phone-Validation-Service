package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class PhoneCodeStorageTest {
    @Test
    public void storesAndReturns() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addPhoneCode("+1");
        storage.addCountry("TEST", "TESTTITLE");

        storage.addPhoneCode("+2");
        storage.addCountry("TEST2", "Test 2 Title");
        storage.addCountry("TEST3", "Test 3 Title");

        storage.flush();

        List<Country> countries = storage.get("+1");
        assertEquals(1, countries.size());
        assertEquals("TEST", countries.get(0).code);
        assertEquals("TESTTITLE", countries.get(0).title);

        countries = storage.get("+2");
        assertEquals(2, countries.size());
        assertEquals("TEST2", countries.get(0).code);
        assertEquals("Test 2 Title", countries.get(0).title);

        assertEquals("TEST3", countries.get(1).code);
        assertEquals("Test 3 Title", countries.get(1).title);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void extractsCountryCode() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addPhoneCode("+1");
        storage.addCountry("MW", "Morrowind");

        storage.addPhoneCode("+11");
        storage.addCountry("VV", "Vvardenfell");

        storage.addPhoneCode("+111");
        storage.addCountry("SH", "Sheogorad");

        storage.addPhoneCode("+112");
        storage.addCountry("AL", "The Ashlands");

        storage.addPhoneCode("+2");
        storage.addCountry("DF", "Daggerfall");

        storage.flush();

        Optional<String> foundCode = storage.extractCountryCode("+12345678");
        assertEquals("+1", foundCode.get());

        foundCode = storage.extractCountryCode("+11345678");
        assertEquals("+11", foundCode.get());

        foundCode = storage.extractCountryCode("+11123");
        // +1 because minimum number (without phone code) len is 4. So 1123 goes for number and +1 goes for code
        assertEquals("+1", foundCode.get());

        foundCode = storage.extractCountryCode("+11145678");
        assertEquals("+111", foundCode.get());

        foundCode = storage.extractCountryCode("+11245678");
        assertEquals("+112", foundCode.get());

        foundCode = storage.extractCountryCode("+21345678");
        assertEquals("+2", foundCode.get());

        foundCode = storage.extractCountryCode("+31111111111");
        assertFalse(foundCode.isPresent());
    }

    @Test
    public void phoneWriteFlushes() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addPhoneCode("+1");
        storage.addCountry("TEST", "TESTTITLE");

        storage.addPhoneCode("+2");

        List<Country> countries = storage.get("+1");
        assertEquals(1, countries.size());
        assertEquals("TEST", countries.get(0).code);
        assertEquals("TESTTITLE", countries.get(0).title);
    }

    @Test
    public void removesSpacesFromPhoneCode() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addPhoneCode("  +1  23   4    ");
        storage.addCountry("TEST", "TEST TITLE");
        storage.flush();

        List<Country> countries = storage.get("+1234");
        assertEquals(1, countries.size());
        assertEquals("TEST", countries.get(0).code);
        assertEquals("TEST TITLE", countries.get(0).title);
    }

    @Test
    public void returnsEmptyListForNonExistingKeys() {
        assertTrue(new PhoneCodeStorage().get("+1").isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void doesNotAllowToOverrideCodes() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addPhoneCode("+1");
        storage.flush();
        storage.addPhoneCode("+1");
        storage.flush();
    }
}