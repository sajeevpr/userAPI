package co.zip.candidate.userapi.service.impl;

import co.zip.candidate.userapi.constants.AccountType;
import co.zip.candidate.userapi.constants.CurrencyType;
import co.zip.candidate.userapi.dao.impl.AccountDaoImpl;
import co.zip.candidate.userapi.dao.impl.UserDaoImpl;
import co.zip.candidate.userapi.exception.CreditCheckException;
import co.zip.candidate.userapi.exception.DataPersistantException;
import co.zip.candidate.userapi.exception.InvalidUserException;
import co.zip.candidate.userapi.exception.InvalidUserInputException;
import co.zip.candidate.userapi.model.Account;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.model.ui.AccountRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    UserDaoImpl userDao;

    @Mock
    AccountDaoImpl accountDao;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void createAccountTest() throws CreditCheckException, DataPersistantException, InvalidUserException {
        final UUID uuid = UUID.randomUUID();
        AccountRequest accountRequest = AccountRequest.builder()
                .userId(uuid.toString())
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        final User user = User.builder()
                .id(uuid)
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        Account acct = Account.builder()
                .id(UUID.randomUUID())
                .user(user)
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        user.setAccounts(new ArrayList<>());

        Mockito.when(userDao.getUser(Mockito.any())).thenReturn(user);
        Mockito.when(accountDao.createAccount(Mockito.any())).thenReturn(acct);

        accountService.createAccount(accountRequest);
    }

    @Test
    void createAccountInvalidUserTest() throws CreditCheckException, DataPersistantException, InvalidUserException {
        final UUID uuid = UUID.randomUUID();
        AccountRequest accountRequest = AccountRequest.builder()
                .userId(uuid.toString())
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        final User user = User.builder()
                .id(uuid)
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        Account acct = Account.builder()
                .id(UUID.randomUUID())
                .user(user)
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        user.setAccounts(new ArrayList<>());

        Mockito.when(userDao.getUser(Mockito.any())).thenReturn(null);
        //Mockito.when(accountDao.createAccount(Mockito.any())).thenReturn(acct);

        Assertions.assertThrows(InvalidUserException.class, () -> accountService.createAccount(accountRequest));
    }

    @Test
    void createAccountInsufficientCreditTest() throws CreditCheckException, DataPersistantException, InvalidUserException {
        final UUID uuid = UUID.randomUUID();
        AccountRequest accountRequest = AccountRequest.builder()
                .userId(uuid.toString())
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        final User user = User.builder()
                .id(uuid)
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12200))
                .monthlyIncome(BigDecimal.valueOf(13000))
                .build();

        Account acct = Account.builder()
                .id(UUID.randomUUID())
                .user(user)
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        user.setAccounts(new ArrayList<>());

        Mockito.when(userDao.getUser(Mockito.any())).thenReturn(user);

        Assertions.assertThrows(CreditCheckException.class, () -> accountService.createAccount(accountRequest));
    }

    @Test
    void createAccountDBWriteErrorTest() throws CreditCheckException, DataPersistantException, InvalidUserException {
        final UUID uuid = UUID.randomUUID();
        AccountRequest accountRequest = AccountRequest.builder()
                .userId(uuid.toString())
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        final User user = User.builder()
                .id(uuid)
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12200))
                .monthlyIncome(BigDecimal.valueOf(14000))
                .build();

        Account acct = Account.builder()
                .id(UUID.randomUUID())
                .user(user)
                .accountName("account1")
                .accountType(AccountType.ZIP_MONEY)
                .currency(CurrencyType.AUD)
                .build();

        user.setAccounts(new ArrayList<>());

        Mockito.when(userDao.getUser(Mockito.any())).thenReturn(user);
        Mockito.doThrow(DataIntegrityViolationException.class).when(accountDao).createAccount(Mockito.any());
        Assertions.assertThrows(DataPersistantException.class, () -> accountService.createAccount(accountRequest));
    }

}
