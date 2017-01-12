package us.heptet.magewars.domain.game.exceptions;

/* Created by jade on 02/09/2016. */

import us.heptet.magewars.domain.game.setup.GameSetup;

/**
 * Exception indicating invalid number of players.
 */
public class InvalidNumberOfPlayersException extends GameInitializationException {
    /**
     * default
     */
    public InvalidNumberOfPlayersException() {
        /* no no */
    }

    /**
     * Construct exception
     * @param gameSetup Game setup
     */
    public InvalidNumberOfPlayersException(GameSetup gameSetup) {
        super(gameSetup);
    }

    /***
     * Construct exception
     * @param gameSetup Game setup
     * @param message message
     */
    public InvalidNumberOfPlayersException(GameSetup gameSetup, String message) {
        super(gameSetup, message);
    }

    /**
     * Construct exception
     * @param gameSetup Game setup
     * @param message Message
     * @param cause Throwable cause
     */
    public InvalidNumberOfPlayersException(GameSetup gameSetup, String message, Throwable cause) {
        super(gameSetup, message, cause);
    }

    /**
     * Construct exception
     * @param gameSetup Game setup
     * @param cause Throwable cause
     */
    public InvalidNumberOfPlayersException(GameSetup gameSetup, Throwable cause) {
        super(gameSetup, cause);
    }
}
