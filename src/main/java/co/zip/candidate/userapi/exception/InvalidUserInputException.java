package co.zip.candidate.userapi.exception;

/**
 * To capture any invalid input from the user
 */
public class InvalidUserInputException extends ZipException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public InvalidUserInputException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
