package us.heptet.magewars.game;

import us.heptet.magewars.game.action.Action;
import us.heptet.magewars.game.action.UiAction;

/* Created by kay on 4/1/2014. */
/**
 * UI Action completedHandler.
 */
@FunctionalInterface
public interface UiActionCompletedHandler {
    /***
     * A ui action has been completed.
     * @param uiAction The ui action.
     * @param success Success flag.
     */
    void uiActionCompleted(UiAction<? extends Action> uiAction, boolean success);
}
