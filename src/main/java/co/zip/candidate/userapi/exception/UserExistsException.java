package co.zip.candidate.userapi.exception;

/**
 * To capture user exists with any identifier like email
 */
public class UserExistsException extends ZipException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public UserExistsException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
