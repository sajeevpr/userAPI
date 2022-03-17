package com.userapi.service.impl;

import com.userapi.constants.UserApiConstants;
import com.userapi.constants.ABCError;
import com.userapi.dao.UserDao;
import com.userapi.exception.*;
import com.userapi.model.User;
import com.userapi.model.ui.UIResponse;
import com.userapi.service.UserService;
import com.userapi.exception.DataPersistantException;
import com.userapi.exception.DataQueryException;
import com.userapi.exception.InvalidUserInputException;
import com.userapi.exception.UserExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UserService implementation
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * Create User
     * @param user user
     * @return UIResponse
     * @throws DataPersistantException for database write failures
     * @throws InvalidUserInputException for invalid user inputs
     * @throws UserExistsException for existing users email
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DataPersistantException.class)
    public UIResponse createUser(User user) throws DataPersistantException, InvalidUserInputException, UserExistsException {
        final String email = user.getEmail();
        final String username = user.getUserName();
        final BigDecimal monthlyIncome = user.getMonthlyIncome();
        final BigDecimal monthlyExpense = user.getMonthlyExpense();

        try {
            //validate input
            if (isInvalidRequest(email, username, monthlyIncome, monthlyExpense)) {
                throw new InvalidUserInputException(ABCError.INVALID_USER_INPUT.getErrorCode(),
                        ABCError.INVALID_USER_INPUT.getErrorDesc());
            }

            //check if the user exists with the given emailId
            User usr = userDao.getUserByEmail(user.getEmail());
            if (usr != null) {
                throw new UserExistsException(ABCError.USER_EXISTS_ERROR.getErrorCode(),
                        ABCError.USER_EXISTS_ERROR.getErrorDesc());
            }

            usr = User.builder()
                    .userName(username)
                    .email(email)
                    .monthlyIncome(monthlyIncome)
                    .monthlyExpense(monthlyExpense)
                    .build();

            final String userId = userDao.createUser(usr).getId().toString();
            log.info("UserID created : {} ", userId);
            return UIResponse.builder().userId(userId).success(true).build();
        } catch(InvalidUserInputException | UserExistsException e) {
            log.error("Exception : {}", e);
            throw e;
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new DataPersistantException(ABCError.DB_WRITE_ERROR.getErrorCode(),
                    ABCError.DB_WRITE_ERROR.getErrorDesc());
        }
    }

    /**
     * To check if the request is valid for create User
     * @param email email
     * @param username username
     * @param monthlyIncome monthly income
     * @param monthlyExpense monthly expense
     * @return true/false
     */
    private boolean isInvalidRequest(String email, String username, BigDecimal monthlyIncome, BigDecimal monthlyExpense) {
        return email == null || email.isEmpty() || (email.indexOf("@") < UserApiConstants.ZERO)
                || username == null || username.isEmpty()
                || monthlyIncome == null || monthlyIncome.equals(BigDecimal.ZERO)
                || monthlyExpense == null || monthlyExpense.equals(BigDecimal.ZERO);
    }

    /**
     * List all users
     * @return list users
     * @throws DataQueryException for database read failures
     */
    @Override
    public UIResponse listUsers() throws DataQueryException{
        try {
            List<User> users =  userDao.listUsers();
            return UIResponse.builder().success(true)
                    .resultList(new ArrayList<>(users)).build();
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new DataQueryException(ABCError.DB_QUERY_ERROR.getErrorCode(),
                    ABCError.DB_QUERY_ERROR.getErrorDesc());
        }
    }
    /**
     * To get user by userId
     * @param id userId
     * @return UIResponse
     * @throws DataQueryException for database query failures
     */
    @Override
    public UIResponse getUser(String id) throws DataQueryException{
        try {
            User user =  userDao.getUser(id);
            return UIResponse.builder().success(true)
                    .resultList(Arrays.asList(user)).build();
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new DataQueryException(ABCError.DB_QUERY_ERROR.getErrorCode(),
                    ABCError.DB_QUERY_ERROR.getErrorDesc());
        }
    }

    /**
     * To get user by email
     * @param email userId
     * @return UIResponse
     * @throws DataQueryException for database query failures
     */
    @Override
    public UIResponse getUserByEmail(String email) throws DataQueryException {
        try {
            User user =  userDao.getUserByEmail(email);
            return UIResponse.builder().success(true)
                    .resultList(Arrays.asList(user)).build();
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new DataQueryException(ABCError.DB_QUERY_ERROR.getErrorCode(),
                    ABCError.DB_QUERY_ERROR.getErrorDesc());
        }
    }
}
