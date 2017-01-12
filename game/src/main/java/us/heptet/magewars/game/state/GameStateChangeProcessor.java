package us.heptet.magewars.game.state;

import java.util.Iterator;
import java.util.Map;

/* Created by jade on 12/07/2016. */

/**
 * Interface for processing game state changes.
 */
@FunctionalInterface
public interface GameStateChangeProcessor {
    /**
     * Process game state changes.
     * @param gameStateChangeIterator Game state change iterator
     * @param properties Properties
     */
    // TODO change properties to something more sensible, perhaps two arguments?
    void process(Iterator<GameStateChange> gameStateChangeIterator, Map<String, Object>[] properties);
}
