package com.userapi.dao;

import com.userapi.model.User;

import java.util.List;

/**
 * DAO for User persistant operations
 */
public interface UserDao {

    /**
     * ID constant
     */
    String ID = "id";

    /**
     * EMAIL constant
     */
    String EMAIL = "email";

    /**
     * Create User
     * @param user user
     * @return user with userId
     */
    User createUser(User user);

    /**
     * List users
     * @return list of users
     */
    List<User> listUsers();

    /**
     * getUser by userId
     * @param id id
     * @return user
     */
    User getUser(String id);

    /**
     * getUserByEmail
     * @param email email
     * @return user
     */
    User getUserByEmail(String email);
}
