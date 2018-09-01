package mmm.neotech.phoneValidationService.countrycodes;

import mmm.neotech.phoneValidationService.countrycodes.enitites.SectionListResponse;

import java.util.Optional;

public interface CountryCodeRepository {
    public Optional<SectionListResponse> sectionList();
}
