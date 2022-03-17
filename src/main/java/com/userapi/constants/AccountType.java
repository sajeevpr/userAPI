package com.userapi.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * AccountType
 */
public enum AccountType {

    /**
     * ABCPay
     */
    ABC_PAY("ABCPay"),
    /**
     * ABCMoney
     */
    ABC_MONEY("ABCMoney");

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
