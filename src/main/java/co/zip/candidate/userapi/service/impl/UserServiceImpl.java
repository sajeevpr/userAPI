package co.zip.candidate.userapi.service.impl;

import co.zip.candidate.userapi.constants.UserApiConstants;
import co.zip.candidate.userapi.constants.ZipError;
import co.zip.candidate.userapi.dao.UserDao;
import co.zip.candidate.userapi.exception.*;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.model.ui.UIResponse;
import co.zip.candidate.userapi.service.UserService;
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
                throw new InvalidUserInputException(ZipError.INVALID_USER_INPUT.getErrorCode(),
                        ZipError.INVALID_USER_INPUT.getErrorDesc());
            }

            //check if the user exists with the given emailId
            User usr = userDao.getUserByEmail(user.getEmail());
            if (usr != null) {
                throw new UserExistsException(ZipError.USER_EXISTS_ERROR.getErrorCode(),
                        ZipError.USER_EXISTS_ERROR.getErrorDesc());
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
            throw new DataPersistantException(ZipError.DB_WRITE_ERROR.getErrorCode(),
                    ZipError.DB_WRITE_ERROR.getErrorDesc());
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
            throw new DataQueryException(ZipError.DB_QUERY_ERROR.getErrorCode(),
                    ZipError.DB_QUERY_ERROR.getErrorDesc());
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
            throw new DataQueryException(ZipError.DB_QUERY_ERROR.getErrorCode(),
                    ZipError.DB_QUERY_ERROR.getErrorDesc());
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
            throw new DataQueryException(ZipError.DB_QUERY_ERROR.getErrorCode(),
                    ZipError.DB_QUERY_ERROR.getErrorDesc());
        }
    }
}
