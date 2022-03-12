package co.zip.candidate.userapi.exception;

/**
 * To capture if the monthly income and expense difference goes below a threshold
 */
public class CreditCheckException extends ZipException{

    /**
     * Constructor
     * @param errorCode error code
     * @param errorDesc error description
     */
    public CreditCheckException(String errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
