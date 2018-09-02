package mmm.neotech.phoneValidationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneValidationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void returnsValidationResponse() throws URISyntaxException, UnsupportedEncodingException {
        ValidationResponse response = requestValidation("+3712900000");
        assertTrue(response.valid);

        response = requestValidation("wrong_number");
        assertFalse(response.valid);
    }

    @Test
    public void returns404forNonExistingEndpoints() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/nonExisting", String.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void allowsOnlyGetRequestsForValidationEndpoints() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/validate?phone=invalid_phone",
                null,
                String.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ValidationResponse requestValidation(String phone) throws UnsupportedEncodingException, URISyntaxException {
        String encodedPlus = URLEncoder.encode("+", "UTF-8");

        return this.restTemplate.getForObject(
                new URI("http://localhost:" + port + "/validate?phone=" + phone.replace("+", encodedPlus)),
                ValidationResponse.class
        );
    }
}