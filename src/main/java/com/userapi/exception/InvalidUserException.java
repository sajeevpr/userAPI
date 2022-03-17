package com.userapi.exception;

/**
 * To capture for invalid user
 */
public class InvalidUserException extends ABCException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public InvalidUserException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
