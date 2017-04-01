package net.skeyurt.lit.commons.exception;

/**
 * User : liulu
 * Date : 2017/3/31 22:13
 * version $Id: AppUnCheckedException.java, v 0.1 Exp $
 */
public class AppUnCheckedException extends RuntimeException{

    private static final long serialVersionUID = -6091660167448838129L;

    public AppUnCheckedException() {
        super();
    }

    public AppUnCheckedException(String message) {
        super(message);
    }

    public AppUnCheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppUnCheckedException(Throwable cause) {
        super(cause);
    }

    protected AppUnCheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
