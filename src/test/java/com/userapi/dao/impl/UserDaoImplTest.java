package com.userapi.dao.impl;

import com.userapi.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    @InjectMocks
    private UserDaoImpl userDao;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Test
    void createUserTest() {
        Mockito.doNothing().when(entityManager).persist(any());
        User user = User.builder().id(UUID.randomUUID()).build();
        String id = userDao.createUser(user).getId().toString();
        Assertions.assertEquals(user.getId().toString(), id);
    }

    @Test
    void listUsersTest() {
        when(entityManager.createQuery(UserDaoImpl.LIST_USERS)).thenReturn(query);
        User user = User.builder().build();
        when(query.getResultList()).thenReturn(Collections.singletonList(user));
        Assertions.assertEquals(user, userDao.listUsers().get(0));
    }

    @Test
    void getUserTest() {
        when(entityManager.createQuery(UserDaoImpl.GET_USER)).thenReturn(query);
        UUID uuid = UUID.randomUUID();
        User user = User.builder().id(uuid).build();
        when(query.getSingleResult()).thenReturn(user);
        Assertions.assertEquals(user, userDao.getUser(uuid.toString()));
    }

    @Test
    void getUserByEmailTest() {
        when(entityManager.createQuery(UserDaoImpl.GET_USER_BY_EMAIL)).thenReturn(query);
        UUID uuid = UUID.randomUUID();
        final String email = "saju_rrk@yahoo.com";
        User user = User.builder().id(uuid).email(email).build();
        when(query.getSingleResult()).thenReturn(user);
        Assertions.assertEquals(user, userDao.getUserByEmail(user.getEmail()));
    }

}
