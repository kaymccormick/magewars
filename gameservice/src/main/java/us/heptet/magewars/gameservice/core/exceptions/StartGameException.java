package us.heptet.magewars.gameservice.core.exceptions;

/* Created by jade on 12/09/2016. */

/**
 * An exception occuring in starting the game, as part of the Game Service.
 */
public class StartGameException extends GameServiceException {
    /**
     * Construct exception.
     */
    public StartGameException() {
        /* nothing */
    }

    /**
     * Construct exception.
     * @param message
     */
    public StartGameException(String message) {
        super(message);
    }

    /**
     * Construct exception.
     * @param message
     * @param cause
     */
    public StartGameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct exception.
     * @param cause
     */
    public StartGameException(Throwable cause) {
        super(cause);
    }
}
