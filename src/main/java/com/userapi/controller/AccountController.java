package com.userapi.controller;

import com.userapi.constants.ABCError;
import com.userapi.exception.ABCException;
import com.userapi.model.ui.AccountRequest;
import com.userapi.model.ui.UIResponse;
import com.userapi.service.AccountService;
import com.userapi.util.UserApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Account
 */
@RestController
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * POST method for creating an Account
     * @param accountRequest accountRequest
     * @return UIResponse
     */
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public UIResponse createAccount(@RequestBody AccountRequest accountRequest) {
        long startTime = System.currentTimeMillis();
        try {
            UIResponse response = accountService.createAccount(accountRequest);
            long endTime = System.currentTimeMillis();
            log.info("Response Time for CreateAccount: {} ms", (endTime - startTime));
            return response;
        } catch(ABCException e) {
            return UserApiUtil.getErrorResponse(e.getErrorCode(), e.getErrorDesc());
        } catch(Exception e) {
            log.error("Exception : {}", e);
            return UserApiUtil.getErrorResponse(ABCError.TECH_ERROR.getErrorCode(), "Error While creating Account");
        }
    }


    /**
     * GET method for listing all Accounts
     * @return UIResponse
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public UIResponse listAccounts() {
        long startTime = System.currentTimeMillis();
        try {
            UIResponse response = accountService.listAccounts();
            long endTime = System.currentTimeMillis();
            log.info("Response Time for listAccounts: {} ms", (endTime - startTime));
            return response;
        } catch(ABCException e) {
            return UserApiUtil.getErrorResponse(e.getErrorCode(), e.getErrorDesc());
        } catch(Exception e) {
            return UserApiUtil.getErrorResponse(ABCError.TECH_ERROR.getErrorCode(), "Error While listing accounts");
        }
    }
}
