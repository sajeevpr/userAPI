package com.userapi.dao.impl;

import com.userapi.model.Account;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountDaoImplTest {

    @InjectMocks
    private AccountDaoImpl accountDao;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Test
    void listAccountsTest() {
        when(entityManager.createQuery(accountDao.LIST_ACCOUNTS)).thenReturn(query);
        Account account = Account.builder().build();
        when(query.getResultList()).thenReturn(Arrays.asList(account));
        Assert.assertEquals(account, accountDao.listAccounts().get(0));
    }

    @Test
    void createAccountTest() {
        Mockito.doNothing().when(entityManager).persist(any());
        Account account = Account.builder().id(UUID.randomUUID()).build();
        String id = accountDao.createAccount(account).getId().toString();
        Assertions.assertEquals(account.getId().toString(), id);
    }

}
