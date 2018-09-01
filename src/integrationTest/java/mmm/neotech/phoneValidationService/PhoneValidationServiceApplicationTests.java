package mmm.neotech.phoneValidationService;

import mmm.neotech.phoneValidationService.phonecodes.PhoneCodeStorage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneValidationServiceApplicationTests {
    @Autowired
    private PhoneCodeStorage phoneCodeStorage;

	@Test
	public void createsPhoneCodeStorageAfterInitialization() {
        Assert.assertNotNull(phoneCodeStorage);
	}
}
