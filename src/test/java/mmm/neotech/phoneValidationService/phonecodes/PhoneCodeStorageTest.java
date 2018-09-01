package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PhoneCodeStorageTest {
    @Test
    public void storesAndReturns() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addItemToBuffer("+1", "+1");
        storage.addItemToBuffer("TEST", "TESTTITLE");
        storage.flush();
        List<Country> countries = storage.get("+1");
        Assert.assertEquals(1, countries.size());
        Assert.assertEquals("TEST", countries.get(0).code);
        Assert.assertEquals("TESTTITLE", countries.get(0).title);
    }

    @Test
    public void phoneWriteFlushes() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addItemToBuffer("+1", "+1");
        storage.addItemToBuffer("TEST", "TESTTITLE");

        storage.addItemToBuffer("+2", "+2");

        List<Country> countries = storage.get("+1");
        Assert.assertEquals(1, countries.size());
        Assert.assertEquals("TEST", countries.get(0).code);
        Assert.assertEquals("TESTTITLE", countries.get(0).title);
    }

    @Test
    public void removesSpacesFromCodes() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addItemToBuffer("  +1  23   4    ", "  +1   23 4 ");
        storage.addItemToBuffer("  T  E   S  T ", "TEST TITLE");
        storage.flush();

        List<Country> countries = storage.get("+1234");
        Assert.assertEquals(1, countries.size());
        Assert.assertEquals("TEST", countries.get(0).code);
        Assert.assertEquals("TEST TITLE", countries.get(0).title);
    }

    @Test
    public void ignoresEmptyAndNullCodes() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addItemToBuffer("+1", "+1");

        storage.addItemToBuffer("    ", "TEST");
        storage.addItemToBuffer("+", "+");
        storage.addItemToBuffer(null, "TEST2");

        Assert.assertTrue(storage.get("+1").isEmpty());

        storage.flush();

        Assert.assertTrue(storage.get("+1").isEmpty());
        Assert.assertTrue(storage.get("    ").isEmpty());
        Assert.assertTrue(storage.get("+").isEmpty());
        Assert.assertTrue(storage.get(null).isEmpty());
    }

    @Test
    public void returnsEmptyListForNonExistingKeys() {
        Assert.assertTrue(new PhoneCodeStorage().get("+1").isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void doesNotAllowToOverrideCodes() {
        PhoneCodeStorage storage = new PhoneCodeStorage();

        storage.addItemToBuffer("+1", "+1");
        storage.flush();
        storage.addItemToBuffer("+1", "+1");
        storage.flush();
    }
}