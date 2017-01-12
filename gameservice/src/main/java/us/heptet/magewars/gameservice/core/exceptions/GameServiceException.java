package us.heptet.magewars.gameservice.core.exceptions;

/* * Created by jade on 30/08/2016.
 */

/**
 * Generic game service exception to be subclassed for specific error states.
 */
public class GameServiceException extends RuntimeException {
    /**
     * Construct an instance.
     */
    public GameServiceException() {
        /* nothing */
    }

    /**
     * Construct an instance.
     * @param message
     */
    public GameServiceException(String message) {
        super(message);
    }

    /**
     * Construct an instance.
     * @param message
     * @param cause
     */
    public GameServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct an instance.
     * @param cause
     */
    public GameServiceException(Throwable cause) {
        super(cause);
    }
}
