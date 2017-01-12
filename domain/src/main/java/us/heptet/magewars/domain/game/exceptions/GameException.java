package us.heptet.magewars.domain.game.exceptions;

/* Created by kay on 3/27/14. */

/**
 * Game exception.
 */
public class GameException extends RuntimeException {
    /**
     * Construct a GameException.
     */
    public GameException() {
        /* Default */
    }

    /**
     * Construct a GameException.
     * @param message Message.
     */
    public GameException(String message) {
        super(message);
    }

    /**
     * Construct a GameException.
     * @param message Message.
     * @param cause Cause.
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a GameException.
     * @param cause Cause.
     */
    public GameException(Throwable cause) {
        super(cause);
    }
}
