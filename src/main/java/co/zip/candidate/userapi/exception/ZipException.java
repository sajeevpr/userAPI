package co.zip.candidate.userapi.exception;

import lombok.Getter;

/**
 * ZipException which is the parent of all exceptions in this project
 */
@Getter
public class ZipException extends Exception{

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
    public ZipException(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
