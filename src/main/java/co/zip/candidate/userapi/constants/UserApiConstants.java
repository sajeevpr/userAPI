package co.zip.candidate.userapi.constants;

import javax.naming.OperationNotSupportedException;

/**
 * Constants for UserApi Applications
 */
public class UserApiConstants {

    private UserApiConstants() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Cannot instantiate the class");
    }

    /**
     * ZERO
     */
    public static final int ZERO = 0;

    /**
     * Credit threshold for creating account
     */
    public static final long CREDIT_THRESHOLD = 1000;
}
