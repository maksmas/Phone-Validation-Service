package mmm.neotech.phoneValidationService.phonecodes;

import mmm.neotech.phoneValidationService.phonecodes.enitites.Parse;
import mmm.neotech.phoneValidationService.phonecodes.enitites.Section;
import mmm.neotech.phoneValidationService.phonecodes.enitites.WikipediaApiResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static mmm.neotech.phoneValidationService.PhoneConstants.MAX_COUNTRY_CODE_LENGTH;
import static mmm.neotech.phoneValidationService.PhoneConstants.MIN_COUNTRY_CODE_LENGTH;

@Component
public class PhoneCodeStorageFactory {
    private static final String TREE_LIST_SECTION_TITLE = "Tree list";
    private final List<String> BLACKLIST = Arrays.asList("North American Numbering Plan");

    private WikipediaPhoneArticleRepository repository;

    @Autowired
    public PhoneCodeStorageFactory(WikipediaPhoneArticleRepository repository) {
        this.repository = repository;
    }

    public PhoneCodeStorage createStorage() {
        Optional<Integer> sectionNumber = findTreeListSectionNumber();

        if (sectionNumber.isPresent()) {
            Optional<String> wrappedHtml = repository.phoneCodesArticleHtml(sectionNumber.get());

            if (wrappedHtml.isPresent()) {
                return parseHtml(wrappedHtml.get());
            }
        }

        throw new BeanInitializationException("Failed to fetch phone codes from Wikipedia");
    }

    private PhoneCodeStorage parseHtml(String html) {
        Document doc = Jsoup.parse(html);
        Elements tableCells = doc.select("td");
        PhoneCodeStorage phoneCodeStorage = new PhoneCodeStorage();

        for (Element td : tableCells) {
            Elements anchors = td.select("a");

            for (Element anchor : anchors) {
                String innerHtml = anchor.html();

                if (innerHtml == null || BLACKLIST.contains(innerHtml)) {
                    continue;
                }

                innerHtml = innerHtml.trim();

                if (isPhoneCode(innerHtml)) {
                    phoneCodeStorage.addPhoneCode(innerHtml);
                } else {
                    String title = anchor.attr("title");
                    phoneCodeStorage.addCountry(innerHtml.toUpperCase(), title.trim());
                }
            }
        }

        phoneCodeStorage.flush();
        return phoneCodeStorage;
    }

    private Optional<Integer> findTreeListSectionNumber() {
        Optional<WikipediaApiResponse> wrappedSectionList = repository.sectionList();

        Optional<Section[]> wrappedSections = wrappedSectionList
                .flatMap(WikipediaApiResponse::parse)
                .flatMap(Parse::sections);

        if (wrappedSections.isPresent()) {
            Section[] sections = wrappedSections.get();

            for (Section s : sections) {
                if (s.line().isPresent() && s.line().get().equalsIgnoreCase(TREE_LIST_SECTION_TITLE)) {
                    return s.index();
                }
            }
        }

        return Optional.empty();
    }

    private boolean isPhoneCode(String code) {
        return code.startsWith("+") &&
               code.length() >= MIN_COUNTRY_CODE_LENGTH &&
               code.length() <= MAX_COUNTRY_CODE_LENGTH;
    }
}
