package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.constants.ZipError;
import co.zip.candidate.userapi.exception.ZipException;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.model.ui.UIResponse;
import co.zip.candidate.userapi.service.UserService;
import co.zip.candidate.userapi.util.UserApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for User
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * POST method for creating a user
     * @param user user
     * @return UIResponse
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public UIResponse createUser(@RequestBody User user) {
        long startTime = System.currentTimeMillis();
        try {
            UIResponse response = userService.createUser(user);
            long endTime = System.currentTimeMillis();
            log.info("Response Time for CreateUser: {} ms", (endTime - startTime));
            return response;
        } catch(ZipException e) {
            return UserApiUtil.getErrorResponse(e.getErrorCode(), e.getErrorDesc());
        } catch(Exception e) {
            log.error("Exception : {}", e);
            return UserApiUtil.getErrorResponse(ZipError.TECH_ERROR.getErrorCode(), "Error While creating user");
        }
    }

    /**
     * GET method for listing all users
     * @return UIResponse
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UIResponse listUsers() {
            long startTime = System.currentTimeMillis();
            try {
                UIResponse response = userService.listUsers();
                long endTime = System.currentTimeMillis();
                log.info("Response Time for listUsers: {} ms", (endTime - startTime));
                return response;
            } catch(ZipException e) {
                return UserApiUtil.getErrorResponse(e.getErrorCode(), e.getErrorDesc());
            } catch(Exception e) {
                log.error("Exception : {}", e);
                return UserApiUtil.getErrorResponse(ZipError.TECH_ERROR.getErrorCode(), "Error While listing user");
            }
    }

    /**
     * GET method for listing user by user_id
     * @param id userId
     * @return UIResponse
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UIResponse getUser(@PathVariable(value="id") String id) {
        long startTime = System.currentTimeMillis();
        try {
            UIResponse response = userService.getUser(id);
            long endTime = System.currentTimeMillis();
            log.info("Response Time for getUser: {} ms", (endTime - startTime));
            return response;
        } catch(ZipException e) {
            return UserApiUtil.getErrorResponse(e.getErrorCode(), e.getErrorDesc());
        } catch(Exception e) {
            log.error("Exception : {}", e);
            return UserApiUtil.getErrorResponse(ZipError.TECH_ERROR.getErrorCode(), "Error handling getUser Request");
        }
    }

    /**
     * GET method for listing user by user email
     * @param email email
     * @return UIResponse
     */
    @RequestMapping(value = "/user/email/{email}", method = RequestMethod.GET)
    public UIResponse getUserByEmail(@PathVariable(value="email") String email) {
        long startTime = System.currentTimeMillis();
        try {
            UIResponse response = userService.getUserByEmail(email);
            long endTime = System.currentTimeMillis();
            log.info("Response Time for getUserByEmail: {} ms", (endTime - startTime));
            return response;
        } catch(ZipException e) {
            return UserApiUtil.getErrorResponse(e.getErrorCode(), e.getErrorDesc());
        } catch(Exception e) {
            log.error("Exception : {}", e);
            return UserApiUtil.getErrorResponse(ZipError.TECH_ERROR.getErrorCode(), "Error handling getUserByEmail Request");
        }
    }

}
