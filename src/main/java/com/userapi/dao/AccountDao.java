package com.userapi.dao;

import com.userapi.model.Account;

import java.util.List;

/**
 * DAO for Account persistant operations
 */
public interface AccountDao {

    /**
     * ID constant
     */
    String ID = "id";

    /**
     * list Accounts
     * @return Accounts
     */
    List<Account> listAccounts();

    /**
     * Create Account
     * @param account account
     * @return account with account_id
     */
    Account createAccount(Account account);
}
