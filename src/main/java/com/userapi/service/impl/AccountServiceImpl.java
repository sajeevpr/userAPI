package com.userapi.service.impl;

import com.userapi.constants.UserApiConstants;
import com.userapi.constants.ABCError;
import com.userapi.dao.AccountDao;
import com.userapi.dao.UserDao;
import com.userapi.exception.*;
import com.userapi.model.Account;
import com.userapi.model.User;
import com.userapi.model.ui.AccountRequest;
import com.userapi.model.ui.UIResponse;
import com.userapi.service.AccountService;
import com.userapi.exception.CreditCheckException;
import com.userapi.exception.DataPersistantException;
import com.userapi.exception.DataQueryException;
import com.userapi.exception.InvalidUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao Impl for AccountService interface
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    /**
     * Create Account for an existing user
     * @param accountRequest accountRequest with userId and account details
     * @return UIResponse
     * @throws DataPersistantException for database write failure
     * @throws InvalidUserException for invalid userId
     * @throws CreditCheckException for credit check failures
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DataPersistantException.class)
    public UIResponse createAccount(AccountRequest accountRequest)
            throws DataPersistantException, InvalidUserException, CreditCheckException {

        try {
            //lookup user
            User user = userDao.getUser(accountRequest.getUserId());
            if (user == null) {
                throw new InvalidUserException(ABCError.INVALID_USER_ERROR.getErrorCode(),
                        ABCError.INVALID_USER_ERROR.getErrorDesc());
            }

            //credit check
            if (user.getMonthlyIncome().subtract(user.getMonthlyExpense()).doubleValue() < UserApiConstants.CREDIT_THRESHOLD) {
                throw new CreditCheckException(ABCError.CREDIT_CHECK_ERROR.getErrorCode(),
                        ABCError.CREDIT_CHECK_ERROR.getErrorDesc());
            }

            Account account = Account.builder()
                    .accountName(accountRequest.getAccountName())
                    .accountType(accountRequest.getAccountType())
                    .balance(BigDecimal.ZERO)
                    .currency(accountRequest.getCurrency())
                    .user(user)
                    .build();

            //persisting account
            account = accountDao.createAccount(account);

            //adding account to the user
            user.getAccounts().add(account);

            return UIResponse.builder()
                    .accountId(account.getId().toString())
                    .success(true).build();
        } catch(InvalidUserException | CreditCheckException e) {
            log.error("Exception : {}", e);
            throw e;
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new DataPersistantException(ABCError.DB_WRITE_ERROR.getErrorCode(),
                    ABCError.DB_WRITE_ERROR.getErrorDesc());
        }
    }

    /**
     * list Accounts
     * @return UIResponse
     * @throws DataQueryException for database read failures
     */
    @Override
    public UIResponse listAccounts() throws DataQueryException {
        try {
            List<Account> accounts =  accountDao.listAccounts();
            return UIResponse.builder().success(true)
                    .resultList(new ArrayList<>(accounts)).build();
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new DataQueryException(ABCError.DB_QUERY_ERROR.getErrorCode(),
                    ABCError.DB_QUERY_ERROR.getErrorDesc());
        }
    }
}
