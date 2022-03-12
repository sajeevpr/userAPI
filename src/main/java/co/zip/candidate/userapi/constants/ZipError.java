package co.zip.candidate.userapi.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * ErrorCode and ErrorDesc enum
 */
public enum ZipError {

    /**
     * INVALID_USER_INPUT
     */
    INVALID_USER_INPUT("101", "Invalid user input while creating user"),

    /**
     * USER_EXISTS_ERROR
     */
    USER_EXISTS_ERROR("102", "User creation failed. User already exists"),

    /**
     * DB_QUERY_ERROR
     */
    DB_QUERY_ERROR("201", "DB Query Error"),

    /**
     * DB_WRITE_ERROR
     */
    DB_WRITE_ERROR("202", "DB Write Error"),

    /**
     * INVALID_USER_ERROR
     */
    INVALID_USER_ERROR("301", "User not found"),

    /**
     * INVALID_USER_ERROR
     */
    CREDIT_CHECK_ERROR("400", "CreditCheckFailed - Account cannot be created"),

    /**
     * TECH_ERROR
     */
    TECH_ERROR("500", "Internal Server Error");

    @Getter
    @Setter
    private String errorCode;

    @Getter
    @Setter
    private String errorDesc;

    /**
     * Constructor for errorCode and errorDesc
     * @param errorCode
     * @param errorDesc
     */
    ZipError(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
