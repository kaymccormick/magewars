package us.heptet.magewars.game.exceptions;

/* Created by kay on 3/28/14. */

import us.heptet.magewars.game.action.Action;

/**
 * Exception for Action Not Ready condition.
 */
public class ActionNotReady extends ActionException {

    public ActionNotReady() {
        /* Default constructor for serializable type */
    }

    /***
     * Create exception with the given action/
     * @param action Action that is not ready for operation.
     */
    public ActionNotReady(Action action) {
        super(action);
    }
}
