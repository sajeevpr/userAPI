package co.zip.candidate.userapi.exception;

/**
 * To capture any database write failures
 */
public class DataPersistantException extends ZipException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public DataPersistantException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
