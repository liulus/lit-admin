package net.skeyurt.lit.commons.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * User : liulu
 * Date : 2017/3/21 19:34
 * version $Id: AppException.java, v 0.1 Exp $
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -679982092243426441L;

    @Getter
    @Setter
    private String errorCode;


    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    protected AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    protected AppException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
