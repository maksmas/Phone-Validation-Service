package mmm.neotech.phoneValidationService.countrycodes.enitites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parse {
    @JsonProperty("sections")
    private Section[] sections;

    public Optional<Section[]> sections() {
        return Optional.ofNullable(sections);
    }
}
