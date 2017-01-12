package us.heptet.magewars.domain.game.exceptions;

import us.heptet.magewars.domain.game.setup.GameSetup;

/* * Created by jade on 02/09/2016. */

/**
 * An exception produced during game initialization.
 */
public class GameInitializationException extends GameException {
    private final GameSetup gameSetup;
    /**
     * Construct a GameInitializationException.
     */
    public GameInitializationException() {
        gameSetup = null;
    }

    /**
     * Construct instance
     * @param gameSetup Game setup
     */
    public GameInitializationException(GameSetup gameSetup) {
        this.gameSetup = gameSetup;
    }

    /**
     * Construct a GameInitializationException.
     * @param gameSetup Game setup
     * @param message Message.
     */
    public GameInitializationException(GameSetup gameSetup, String message) {
        super(message);
        this.gameSetup = gameSetup;
    }

    /**
     * Construct a GameInitializationException.
     * @param gameSetup Game setup
     * @param message Message
     * @param cause Throwable cause.
     */
    public GameInitializationException(GameSetup gameSetup, String message, Throwable cause) {
        super(message, cause);
        this.gameSetup = gameSetup;
    }

    /**
     * Construct a GameInitializationException.
     * @param gameSetup Game setup
     * @param cause Throwable cause
     */
    public GameInitializationException(GameSetup gameSetup, Throwable cause) {
        super(cause);
        this.gameSetup = gameSetup;
    }

    public GameSetup getGameSetup() {
        return gameSetup;
    }
}
