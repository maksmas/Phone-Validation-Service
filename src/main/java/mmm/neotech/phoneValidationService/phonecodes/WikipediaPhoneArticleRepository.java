package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.WikipediaApiResponse;

import java.util.Optional;

public interface WikipediaPhoneArticleRepository {
    Optional<WikipediaApiResponse> sectionList();

    Optional<String> phoneCodesArticleHtml(Integer countryCodeSectionIndex);
}
