package us.heptet.magewars.game.exceptions;

/* Created by kay on 3/27/14. */

import us.heptet.magewars.domain.game.exceptions.GameException;
import us.heptet.magewars.game.action.Action;

/**
 * Base class for exception related to action processing.
 */
public class ActionException extends GameException {
    protected final Action action;

    /**
     * Create the exception.
     */
    public ActionException() {
        /* Default constructor for Serializable type */
        action = null;
    }

    /***
     * Create exception for action-related condition.
     * @param action Action related to exception.
     */
    public ActionException(Action action) {
        this.action = action;
    }

    /**
     * Create the exception.
     * @param message Message
     * @param action Action
     */
    public ActionException(String message, Action action) {
        super(message);
        this.action = action;
    }

    /**
     * Create the exception.
     * @param message Message
     * @param cause Throwable cause
     * @param action Action
     */
    public ActionException(String message, Throwable cause, Action action) {
        super(message, cause);
        this.action = action;
    }

    /**
     * Create the exception.
     * @param cause Throwabe cause
     * @param action Action
     */
    public ActionException(Throwable cause, Action action) {
        super(cause);
        this.action = action;
    }

}
