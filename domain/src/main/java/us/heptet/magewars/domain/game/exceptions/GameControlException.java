package us.heptet.magewars.domain.game.exceptions;

/* Created by jade on 17/09/2016. */

/**
 * Game Control Exception
 */
public class GameControlException extends GameException {
    public GameControlException() {
        /* default */
    }

    /**
     * Create exception.
     * @param message message
     */
    public GameControlException(String message) {
        super(message);
    }

    /**
     * Create exception.
     * @param message Message.
     * @param cause Throwable cause.
     */
    public GameControlException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create exception.
     * @param cause Throwable cause.
     */
    public GameControlException(Throwable cause) {
        super(cause);
    }
}
