package mmm.neotech.phoneValidationService.phonecodes.enitites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parse {
    @JsonProperty("sections")
    private Section[] sections;

    @JsonProperty("text")
    private String text;

    public Optional<Section[]> sections() {
        return Optional.ofNullable(sections);
    }

    public Optional<String> text() { return Optional.ofNullable(text); }
}
