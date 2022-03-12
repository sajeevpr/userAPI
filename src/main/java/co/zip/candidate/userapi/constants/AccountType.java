package co.zip.candidate.userapi.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * AccountType
 */
public enum AccountType {

    /**
     * zipPay
     */
    ZIP_PAY("zipPay"),
    /**
     * zipMoney
     */
    ZIP_MONEY("zipMoney");

    @Getter
    @Setter
    private String value;

    /**
     * Constructor for AccountType
     * @param value
     */
    AccountType(String value) {
        this.value = value;
    }
}
