package com.userapi.exception;

import lombok.Getter;

/**
 * ABCException which is the parent of all exceptions in this project
 */
@Getter
public class ABCException extends Exception{

    /**
     * errCode
     */
    private String errorCode;

    /**
     * errDesc
     */
    private String errorDesc;

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public ABCException(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
