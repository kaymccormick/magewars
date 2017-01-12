package us.heptet.magewars.domain.persistence.exceptions;

/* * Created by jade on 20/09/2016. */

/**
 * Player not found exception.
 */
public class PlayerNotFoundException extends RuntimeException {
    /**
     * Construct exception.
     */
    public PlayerNotFoundException() {
        /* nothing to do */
    }

    /**
     * Construct exception.
     * @param message Message
     */
    public PlayerNotFoundException(String message) {
        super(message);
    }

    /**
     * Construct exception.
     * @param message Message
     * @param cause Throwable cause
     */
    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct exception.
     * @param cause Throwable cause
     */
    public PlayerNotFoundException(Throwable cause) {
        super(cause);
    }
}
