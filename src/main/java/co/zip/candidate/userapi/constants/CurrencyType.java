package co.zip.candidate.userapi.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * CurrencyType
 */
public enum CurrencyType {

    /**
     * AUD
     */
    AUD("AUD"),
    /**
     * USD
     */
    USD("USD");

    @Getter
    @Setter
    private String value;

    /**
     * Constructor for CurrencyType
     * @param value
     */
    CurrencyType(String value) {
        this.value = value;
    }
}
