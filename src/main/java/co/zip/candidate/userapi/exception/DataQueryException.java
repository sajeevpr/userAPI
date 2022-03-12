package co.zip.candidate.userapi.exception;

/**
 * To capture any database query failures
 */
public class DataQueryException extends ZipException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public DataQueryException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
