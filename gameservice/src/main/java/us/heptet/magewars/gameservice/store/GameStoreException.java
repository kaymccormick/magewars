package us.heptet.magewars.gameservice.store;

/**
 * Created by jade on 20/09/2016.
 */
public class GameStoreException extends RuntimeException {
    public GameStoreException() {
        /* default */
    }

    /**
     * Create exception
     * @param message
     */
    public GameStoreException(String message) {
        super(message);
    }

    /**
     * Create exception
     * @param message
     * @param cause
     */
    public GameStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create exception
     * @param cause
     */
    public GameStoreException(Throwable cause) {
        super(cause);
    }
}
