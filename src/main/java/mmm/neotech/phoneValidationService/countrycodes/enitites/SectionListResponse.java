package mmm.neotech.phoneValidationService.countrycodes.enitites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionListResponse {
    @JsonProperty("parse")
    private Parse parse;

    public Optional<Parse> parse() {
        return Optional.ofNullable(parse);
    }
}

