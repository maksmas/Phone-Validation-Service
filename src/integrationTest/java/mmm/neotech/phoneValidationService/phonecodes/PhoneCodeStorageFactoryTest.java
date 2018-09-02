package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Country;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneCodeStorageFactoryTest {
    @Autowired
    private PhoneCodeStorageFactory phoneCodeStorageFactory;

    @Autowired
    private WikipediaPhoneArticleRepositoryMock countryCodeRepositoryMock;

    @Before
    public void setUp() {
        countryCodeRepositoryMock.returnEmptySectionList = false;
        countryCodeRepositoryMock.returnEmptyHtml = false;
    }

    @Test(expected = BeanInitializationException.class)
    public void throwsExceptionWhenNoSectionListResponse() {
        countryCodeRepositoryMock.returnEmptySectionList = true;

        phoneCodeStorageFactory.createStorage();
    }

    @Test(expected = BeanInitializationException.class)
    public void throwsExceptionWhenNoSectionResponse() {
        countryCodeRepositoryMock.returnEmptyHtml = true;

        phoneCodeStorageFactory.createStorage();
    }

    @Test
    public void createsPhoneCodeStorage() {
        PhoneCodeStorage storage = phoneCodeStorageFactory.createStorage();

        List<Country> countries = storage.get("+371");
        Assert.assertEquals(1, countries.size());
        Assert.assertEquals("LV", countries.get(0).code);
        Assert.assertEquals("Latvia", countries.get(0).title);

        countries = storage.get("+290");
        Assert.assertEquals(2, countries.size());
        Assert.assertTrue(countries.contains(new Country("SH", "Saint Helena")));
        Assert.assertTrue(countries.contains(new Country("TA", "Tristan da Cunha")));
    }

    @After
    public void tearDown() {
        countryCodeRepositoryMock.returnEmptyHtml = false;
        countryCodeRepositoryMock.returnEmptySectionList = false;
    }
}