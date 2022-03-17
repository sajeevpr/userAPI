package com.userapi.model.ui;

import com.userapi.constants.AccountType;
import com.userapi.constants.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Model class for Account Creation request
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    /**
     * accountType enum
     */
    private AccountType accountType;

    /**
     * accountName
     */
    private String accountName;

    /**
     * Currency enum
     */
    private CurrencyType currency;

    /**
     * userId
     */
    private String userId;

}
