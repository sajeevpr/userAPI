package com.userapi.exception;

/**
 * To capture any database write failures
 */
public class DataPersistantException extends ABCException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public DataPersistantException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
