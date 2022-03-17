package com.userapi.util;

import com.userapi.model.ui.UIResponse;

import javax.naming.OperationNotSupportedException;

/**
 * Util class
 */
public class UserApiUtil {

    private UserApiUtil() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Cannot instantiate the class");
    }

    /**
     * To generate exception UIResponse with an input message
     * @param errorCode error code
     * @param errorDesc error description
     * @return UIResponse object
     */
    public static UIResponse getErrorResponse(String errorCode, String errorDesc) {
        UIResponse response = new UIResponse();
        response.setSuccess(false);
        response.setErrorDesc(errorDesc);
        response.setErrorCode(errorCode);
        return response;
    }
}
