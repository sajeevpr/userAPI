package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.exception.*;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.model.ui.UIResponse;

/**
 * Service interface for User operations
 */
public interface UserService {

    /**
     * Create User
     * @param user user
     * @return UIResponse
     * @throws DataPersistantException for database write failures
     * @throws InvalidUserInputException for invalid user inputs
     * @throws UserExistsException for existing users email
     */
    UIResponse createUser(User user) throws DataPersistantException, InvalidUserInputException, UserExistsException;

    /**
     * List all users
     * @return list users
     * @throws DataQueryException for database read failures
     */
    UIResponse listUsers() throws DataQueryException;

    /**
     * To get user by userId
     * @param id userId
     * @return UIResponse
     * @throws DataQueryException for database query failures
     */
    UIResponse getUser(String id) throws DataQueryException;

    /**
     * To get user by email
     * @param email userId
     * @return UIResponse
     * @throws DataQueryException for database query failures
     */
    UIResponse getUserByEmail(String email) throws DataQueryException;
}
