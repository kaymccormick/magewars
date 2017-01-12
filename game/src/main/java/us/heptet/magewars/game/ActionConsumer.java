package us.heptet.magewars.game;

import us.heptet.magewars.game.action.Action;

/* Created by kay on 4/1/2014. */

/**
 * An interface for consuming actions.
 * @param <T> Type of actions to be consumed
 */
@FunctionalInterface
public interface ActionConsumer<T extends Action> {
    /**
     * Consume an action
     * @param action Action to be consumed.
     */
    void accept(T action);
}
