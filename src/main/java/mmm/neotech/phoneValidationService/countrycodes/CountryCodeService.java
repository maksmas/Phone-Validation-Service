package mmm.neotech.phoneValidationService.countrycodes;

import mmm.neotech.phoneValidationService.countrycodes.enitites.Parse;
import mmm.neotech.phoneValidationService.countrycodes.enitites.Section;
import mmm.neotech.phoneValidationService.countrycodes.enitites.SectionListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CountryCodeService {
    private static final String ORDERED_BY_CODE_SECTION_TITLE = "Ordered by code";

    private CountryCodeRepository repository;

    @Autowired
    public CountryCodeService(CountryCodeRepository repository) {
        this.repository = repository;
    }

    public Optional<Integer> findOrderedByCodeSectionNumber() {
        Optional<SectionListResponse> wrappedSectionList = repository.sectionList();

        Optional<Section[]> wrappedSections = wrappedSectionList
                .flatMap(SectionListResponse::parse)
                .flatMap(Parse::sections);

        if (wrappedSections.isPresent()) {
            Section[] sections = wrappedSections.get();

            for (Section s : sections) {
                if (s.line().isPresent() && s.line().get().equalsIgnoreCase(ORDERED_BY_CODE_SECTION_TITLE)) {
                    return s.index();
                }
            }
        }

        return Optional.empty();
    }
}
