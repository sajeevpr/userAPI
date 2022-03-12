package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.exception.*;
import co.zip.candidate.userapi.model.ui.AccountRequest;
import co.zip.candidate.userapi.model.ui.UIResponse;

/**
 * Service interface for Account operations
 */
public interface AccountService {

    /**
     * Create Account for an existing user
     * @param accountRequest accountRequest with userId and account details
     * @return UIResponse
     * @throws DataPersistantException for database write failure
     * @throws InvalidUserException for invalid userId
     * @throws CreditCheckException for credit check failures
     */
    UIResponse createAccount(AccountRequest accountRequest)
            throws DataPersistantException, InvalidUserException, CreditCheckException;

    /**
     * list Accounts
     * @return UIResponse
     * @throws DataQueryException for database read failures
     */
    UIResponse listAccounts() throws DataQueryException;
}
