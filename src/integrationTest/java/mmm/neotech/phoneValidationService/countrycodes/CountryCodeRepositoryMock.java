package mmm.neotech.phoneValidationService.countrycodes;

import com.fasterxml.jackson.databind.ObjectMapper;
import mmm.neotech.phoneValidationService.countrycodes.enitites.SectionListResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Primary
public class CountryCodeRepositoryMock implements CountryCodeRepository {
    public boolean returnEmptyResponse = false;

    @Override
    public Optional<SectionListResponse> sectionList() {
        if (returnEmptyResponse) {
            return Optional.empty();
        } else {
            try {
                SectionListResponse slr = new ObjectMapper().readValue(
                        new ClassPathResource("wikipediaSectionsresponseMock.json").getFile(),
                        SectionListResponse.class
                );

                return Optional.ofNullable(slr);
            } catch (IOException e) {
                // Shouldn't happen. Tests will fail otherwise
                return Optional.empty();
            }
        }
    }
}
