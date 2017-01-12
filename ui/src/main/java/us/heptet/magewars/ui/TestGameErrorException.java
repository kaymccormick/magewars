package us.heptet.magewars.ui;

/* Created by kay on 3/30/2014. */
/**
 * An exception used in test code.
 */
public class TestGameErrorException extends Exception {
    /**
     * Construct an instance.
     */
    public TestGameErrorException() {
        /* nothing */
    }

    /**
     * Construct an instance.
     * @param message
     */
    public TestGameErrorException(String message) {
        super(message);
    }

    /**
     * Construct an instance.
     * @param message
     * @param cause
     */
    public TestGameErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct an instance.
     * @param cause
     */
    public TestGameErrorException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct an instance.
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TestGameErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
