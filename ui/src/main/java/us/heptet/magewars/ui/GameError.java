package us.heptet.magewars.ui;

/* Created by kay on 3/30/2014. */
/**
 * A "Game error" class which currently wraps a single exception, but which
 * can be expanded to include more diagnostic information.
 */
public class GameError {
    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
