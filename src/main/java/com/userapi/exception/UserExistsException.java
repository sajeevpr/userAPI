package com.userapi.exception;

/**
 * To capture user exists with any identifier like email
 */
public class UserExistsException extends ABCException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public UserExistsException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
