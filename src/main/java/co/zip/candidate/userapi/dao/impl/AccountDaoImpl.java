package co.zip.candidate.userapi.dao.impl;

import co.zip.candidate.userapi.dao.AccountDao;
import co.zip.candidate.userapi.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * DAO for Account persistant operations
 */
@Component
public class AccountDaoImpl implements AccountDao {

    /**
     * LIST_ACCOUNTS Query
     */
    public static final String LIST_ACCOUNTS = "select a from Account a";

    @PersistenceContext
    EntityManager entityManager;

    /**
     * list Accounts
     * @return Accounts
     */
    @Override
    public List<Account> listAccounts() {
        Query query = entityManager.createQuery(LIST_ACCOUNTS);
        return query.getResultList();
    }

    /**
     * Create Account
     * @param account account
     * @return account with account_id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Account createAccount(Account account) {
        entityManager.persist(account);
        return account;
    }

}
