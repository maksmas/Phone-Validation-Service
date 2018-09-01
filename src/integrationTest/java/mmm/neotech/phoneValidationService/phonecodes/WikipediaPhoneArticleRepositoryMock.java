package mmm.neotech.phoneValidationService.phonecodes;

import com.fasterxml.jackson.databind.ObjectMapper;
import mmm.neotech.phoneValidationService.phonecodes.enitites.Parse;
import mmm.neotech.phoneValidationService.phonecodes.enitites.WikipediaApiResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Primary
public class WikipediaPhoneArticleRepositoryMock implements WikipediaPhoneArticleRepository {
    public boolean returnEmptySectionList = false;
    public boolean returnEmptyHtml = false;

    @Override
    public Optional<WikipediaApiResponse> sectionList() {
        if (returnEmptySectionList) {
            return Optional.empty();
        } else {
            try {
                WikipediaApiResponse responseMock = readResponseFromFile(
                        "wikipediaSectionListResponseMock.json"
                );
                return Optional.ofNullable(responseMock);
            } catch (IOException e) {
                // Shouldn't happen. Tests will fail otherwise
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<String> phoneCodesArticleHtml(Integer countryCodeSectionIndex) {
        if (returnEmptyHtml) {
            return Optional.empty();
        } else {
            try {
                WikipediaApiResponse responseMock = readResponseFromFile("wikipediaSectionResponseMock.json");

                //noinspection OptionalGetWithoutIsPresent
                return Optional.of(responseMock.parse().flatMap(Parse::text).get());
            } catch (IOException e) {
                // Shouldn't happen. Tests will fail otherwise
                return Optional.empty();
            }
        }
    }

    private WikipediaApiResponse readResponseFromFile(String fileName) throws IOException {
        return new ObjectMapper().readValue(
                new ClassPathResource(fileName).getFile(),
                WikipediaApiResponse.class
        );
    }
}
