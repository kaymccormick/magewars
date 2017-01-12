package us.heptet.magewars.game;

import us.heptet.magewars.game.action.Action;

/* Created by kay on 4/1/2014. */
/**
 * Interface for a "phase action handler."
 */
public interface PhaseActionHandler {
    /***
     * Pass the action.
     */
    void passAction();

    /***
     * Complete the actions.
     */
    void completeActions();

    /***
     * Consume an action.
     * @param action Action
     * @param <T> type of action
     */
    <T extends Action> void consumeAction(T action);
}
