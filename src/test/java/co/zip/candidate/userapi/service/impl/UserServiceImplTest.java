package co.zip.candidate.userapi.service.impl;

import co.zip.candidate.userapi.dao.impl.UserDaoImpl;
import co.zip.candidate.userapi.exception.DataPersistantException;
import co.zip.candidate.userapi.exception.DataQueryException;
import co.zip.candidate.userapi.exception.InvalidUserInputException;
import co.zip.candidate.userapi.exception.UserExistsException;
import co.zip.candidate.userapi.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void createUserTest() throws UserExistsException, InvalidUserInputException, DataPersistantException {

        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        when(userDao.getUserByEmail(Mockito.any())).thenReturn(null);
        when(userDao.createUser(Mockito.any())).thenReturn(user);

        Assertions.assertEquals(true, userService.createUser(user).getSuccess());
    }

    @Test
    void createUserEmptyEmailTest() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();
        Assertions.assertThrows(InvalidUserInputException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserInvalidEmailTest() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrkyahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();
        Assertions.assertThrows(InvalidUserInputException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserNullUsernameTest() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();
        Assertions.assertThrows(InvalidUserInputException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserNullMonthlyIncomeTest() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .build();
        Assertions.assertThrows(InvalidUserInputException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserNullMonthlyExpenseTest() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyIncome(BigDecimal.valueOf(12000))
                .build();
        Assertions.assertThrows(InvalidUserInputException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserExistsTest() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        when(userDao.getUserByEmail(Mockito.any())).thenReturn(user);

        Assertions.assertThrows(UserExistsException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserDBWriteFailTest() {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        when(userDao.getUserByEmail(Mockito.any())).thenReturn(null);
        Mockito.doThrow(DataIntegrityViolationException.class).when(userDao).createUser(Mockito.any());

        Assertions.assertThrows(DataPersistantException.class, () -> userService.createUser(user));
    }

    @Test
    void listUsersTest() throws DataQueryException {

        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        when(userDao.listUsers()).thenReturn(Collections.singletonList(user));

        Assertions.assertEquals(true, userService.listUsers().getSuccess());
    }

    @Test
    void listUsersExceptionTest() {
        Mockito.doThrow(NoResultException.class).when(userDao).listUsers();

        Assertions.assertThrows(DataQueryException.class, () -> userService.listUsers());
    }

    @Test
    void getUserTest() throws DataQueryException {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        when(userDao.getUser(Mockito.any())).thenReturn(user);

        Assertions.assertEquals(true, userService.getUser(user.getId().toString()).getSuccess());
    }

    @Test
    void getUserByEmailTest() throws DataQueryException {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        when(userDao.getUserByEmail(Mockito.any())).thenReturn(user);

        Assertions.assertEquals(true, userService.getUserByEmail(user.getId().toString()).getSuccess());
    }

    @Test
    void getUserExceptionTest()  {
        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();

        Mockito.doThrow(NoResultException.class).when(userDao).getUser(Mockito.any());

        Assertions.assertThrows(DataQueryException.class, () -> userService.getUser(user.getId().toString()));
    }
}
