package mmm.neotech.phoneValidationService.countrycodes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryCodeServiceTest {
    @Autowired
    private CountryCodeService countryCodeService;

    @Autowired
    private CountryCodeRepositoryMock countryCodeRepositoryMock;

    @Test
    public void returnsOrderByCodeSectionNumber() {
        countryCodeRepositoryMock.returnEmptyResponse = false;

        Optional<Integer> sectionNumber = countryCodeService.findOrderedByCodeSectionNumber();
        Assert.assertTrue(sectionNumber.isPresent());
        Assert.assertEquals(new Integer(2), sectionNumber.get());
    }

    @Test
    public void returnsEmptyOptionalWhenNoResponseFromWikipedia() {
        countryCodeRepositoryMock.returnEmptyResponse = true;

        Optional<Integer> sectionNumber = countryCodeService.findOrderedByCodeSectionNumber();
        Assert.assertFalse(sectionNumber.isPresent());
    }
}