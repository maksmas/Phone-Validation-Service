package mmm.neotech.phoneValidationService.countrycodes;

import mmm.neotech.phoneValidationService.countrycodes.enitites.SectionListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class CountryCodeRepositoryImpl implements CountryCodeRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(CountryCodeRepositoryImpl.class);

    public Optional<SectionListResponse> sectionList() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            SectionListResponse sectionListResponse = restTemplate.getForObject(buildSectionsUrl(), SectionListResponse.class);
            return Optional.ofNullable(sectionListResponse);
        } catch (RestClientException e) {
            LOGGER.error("Section list request failed with message: " + e.getMessage());
            return Optional.empty();
        }
    }

    private String buildSectionsUrl() {
        String wikipediaApiUrl = "https://en.wikipedia.org/w/api.php";
        String action = "action=parse";
        String page = "page=List_of_country_calling_codes";
        String format = "format=json&formatversion=2";
        String prop =  "prop=sections";

        return wikipediaApiUrl + "?" + action + "&" + page + "&" + format + "&" + prop;
    }

}
