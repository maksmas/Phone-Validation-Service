package mmm.neotech.phoneValidationService.phonecodes.enitites;

import java.util.Objects;

public class Country {
    public String code;
    public String title;

    public Country(String code, String title) {
        assert code != null;

        this.code = code;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
