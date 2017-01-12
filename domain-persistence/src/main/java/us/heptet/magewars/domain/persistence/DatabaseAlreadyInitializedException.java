package us.heptet.magewars.domain.persistence;

/**
 * Created by jade on 10/08/2016.
 */

/**
 * Exception indicating the database has already been initialized.
 */
public class DatabaseAlreadyInitializedException extends RuntimeException {
    public DatabaseAlreadyInitializedException() {
        /* no nop */
    }

    /**
     * Create exception
     * @param message Message.
     */
    public DatabaseAlreadyInitializedException(String message) {
        super(message);
    }

    /**
     * Create exception
     * @param message Message.
     * @param cause Throwable cause.
     */
    public DatabaseAlreadyInitializedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create exception
     * @param cause Throwable cause.
     */
    public DatabaseAlreadyInitializedException(Throwable cause) {
        super(cause);
    }

}
