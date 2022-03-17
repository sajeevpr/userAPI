package com.userapi.exception;

/**
 * To capture any database query failures
 */
public class DataQueryException extends ABCException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public DataQueryException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
