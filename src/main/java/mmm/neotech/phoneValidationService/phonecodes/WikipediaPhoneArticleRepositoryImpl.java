package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Parse;
import mmm.neotech.phoneValidationService.phonecodes.enitites.WikipediaApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
class WikipediaPhoneArticleRepositoryImpl implements WikipediaPhoneArticleRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(WikipediaPhoneArticleRepositoryImpl.class);

    public Optional<WikipediaApiResponse> sectionList() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            WikipediaApiResponse wikipediaApiResponse = restTemplate.getForObject(
                    buildSectionsUrl(),
                    WikipediaApiResponse.class
            );
            return Optional.ofNullable(wikipediaApiResponse);
        } catch (RestClientException e) {
            LOGGER.error("Section list request failed with message: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> phoneCodesArticleHtml(Integer countryCodeSectionIndex) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            WikipediaApiResponse response = restTemplate.getForObject(
                    buildCountryCodesUrl(countryCodeSectionIndex), WikipediaApiResponse.class
            );

            if (response != null) {
                return response.parse().flatMap(Parse::text);
            } else {
                return Optional.empty();
            }
        } catch (RestClientException e) {
            LOGGER.error("Country code request failed with message: " + e.getMessage());
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

    private String buildCountryCodesUrl(Integer sectionIndex) {
        String wikipediaApiUrl = "https://en.wikipedia.org/w/api.php";
        String action = "action=parse";
        String page = "page=List_of_country_calling_codes";
        String format = "format=json&formatversion=2";
        String section = "section=" + sectionIndex;
        String disableTableOfContent = "disabletoc=true";

        return wikipediaApiUrl + "?" + action + "&" + page + "&" + format + "&" + section + "&" + disableTableOfContent;

    }
}
