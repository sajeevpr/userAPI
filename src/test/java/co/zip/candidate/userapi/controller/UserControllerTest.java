package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.exception.*;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.model.ui.UIResponse;
import co.zip.candidate.userapi.service.impl.UserServiceImpl;
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
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    @Test
    void createUserTest() throws UserExistsException, InvalidUserInputException, DataPersistantException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();
        UIResponse response = UIResponse.builder().success(true).userId(user.getId().toString()).build();
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(response);
        Assertions.assertEquals(user.getId().toString(), userService.createUser(user).getUserId());
    }

    @Test
    void createUserExceptionTest() throws UserExistsException, InvalidUserInputException, DataPersistantException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final User user = User.builder()
                .id(UUID.randomUUID())
                .email("saju_rrk@yahoo.com")
                .userName("saju")
                .monthlyExpense(BigDecimal.valueOf(12000))
                .monthlyIncome(BigDecimal.valueOf(15000))
                .build();
        Mockito.doThrow(UserExistsException.class).when(userService).createUser(Mockito.any());
        Assertions.assertThrows(ZipException.class, () -> userService.createUser(user));
    }

    @Test
    void listUserTest() throws DataQueryException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UIResponse response = UIResponse.builder().success(true).build();
        Mockito.when(userService.listUsers()).thenReturn(response);
        Assertions.assertEquals(true, userService.listUsers().getSuccess());
    }

    @Test
    void getUserTest() throws DataQueryException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UIResponse response = UIResponse.builder().success(true).build();
        Mockito.when(userService.getUser(Mockito.any())).thenReturn(response);
        Assertions.assertEquals(true, userService.getUser("abc").getSuccess());
    }

    @Test
    void getUserByEmailTest() throws DataQueryException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UIResponse response = UIResponse.builder().success(true).build();
        Mockito.when(userService.getUserByEmail(Mockito.any())).thenReturn(response);
        Assertions.assertEquals(true, userService.getUserByEmail("saju_rrk@yahoo.com").getSuccess());
    }
}
