package mmm.neotech.phoneValidationService.phonecodes.enitites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Section {
    @JsonProperty("line")
    private String line;
    @JsonProperty("index")
    private Integer index;

    public Optional<String> line() {
        return Optional.ofNullable(line);
    }

    public Optional<Integer> index() {
        return Optional.ofNullable(index);
    }
}
