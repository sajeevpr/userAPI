package com.userapi.controller;

import com.userapi.constants.AccountType;
import com.userapi.exception.*;
import com.userapi.exception.CreditCheckException;
import com.userapi.exception.DataPersistantException;
import com.userapi.exception.DataQueryException;
import com.userapi.exception.InvalidUserException;
import com.userapi.model.Account;
import com.userapi.model.User;
import com.userapi.model.ui.AccountRequest;
import com.userapi.model.ui.UIResponse;
import com.userapi.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    AccountController accountController;

    @Mock
    AccountServiceImpl accountService;

    @Test
    void createAccountTest() throws CreditCheckException, DataPersistantException, InvalidUserException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();
        UUID acctUUid = UUID.randomUUID();
        final Account acct = Account.builder()
                .id(acctUUid)
                .build();
        AccountRequest accountRequest = AccountRequest.builder()
                .accountType(AccountType.ABC_MONEY)
                .accountName("name")
                .build();
        UIResponse response = UIResponse.builder().success(true).accountId(acct.getId().toString()).build();
        Mockito.when(accountService.createAccount(Mockito.any())).thenReturn(response);
        Assertions.assertEquals(acctUUid.toString(), accountService.createAccount(accountRequest).getAccountId());
    }

    @Test
    void listAccountTest() throws DataQueryException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UIResponse response = UIResponse.builder().success(true).build();
        Mockito.when(accountService.listAccounts()).thenReturn(response);
        Assertions.assertEquals(true, accountService.listAccounts().getSuccess());
    }
}
