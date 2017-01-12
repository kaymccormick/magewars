package us.heptet.magewars.ui;

/* Created by kay on 3/30/2014. */
/**
 * A functional interface for handling game errors.
 */
@FunctionalInterface
public interface GameErrorHandler {
    /**
     * Handle a game error.
     * @param error
     * @return
     */
    boolean handle(GameError error);
}
