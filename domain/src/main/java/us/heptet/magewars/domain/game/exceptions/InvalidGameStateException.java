package us.heptet.magewars.domain.game.exceptions;

/* Created by jade on 03/09/2016. */

/**
 * Invalid game state exception
 */
public class InvalidGameStateException extends GameException {
    public InvalidGameStateException() {
        /* no-op */
    }

    /**
     * Create exception.
     * @param s message.
     */
    public InvalidGameStateException(String s) {

        super(s);
    }
}
