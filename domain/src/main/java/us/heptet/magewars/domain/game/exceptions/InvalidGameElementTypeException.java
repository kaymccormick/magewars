package us.heptet.magewars.domain.game.exceptions;

import us.heptet.magewars.domain.game.GameElementType;

/**
 * Created by jade on 03/09/2016.
 */

/**
 * Invalid game element type exception.
 */
public class InvalidGameElementTypeException extends GameException {
    private final GameElementType expectedGameElementType;
    private final GameElementType actualGameElementType;

    /**
     * Serializable constructor.
     */
    public InvalidGameElementTypeException() {
        /* No-op */
        expectedGameElementType = null;
        actualGameElementType = null;
    }

    /**
     * Create a InvalidGameElementTypeException.
     * @param expectedGameElementType Expected type.
     * @param actualGameElementType Actual type.
     */
    public InvalidGameElementTypeException(GameElementType expectedGameElementType, GameElementType actualGameElementType) {
        this.expectedGameElementType = expectedGameElementType;
        this.actualGameElementType = actualGameElementType;
    }

    /**
     * Create a InvalidGameElementTypeException.
     * @param message Message
     * @param expectedGameElementType Expected type.
     * @param actualGameElementType Actual tye.
     */
    public InvalidGameElementTypeException(String message, GameElementType expectedGameElementType, GameElementType actualGameElementType) {
        super(message);
        this.expectedGameElementType = expectedGameElementType;
        this.actualGameElementType = actualGameElementType;
    }

    /**
     * Create a InvalidGameElementTypeException.
     * @param message Message.
     * @param cause Cause.
     * @param expectedGameElementType Expected Type.
     * @param actualGameElementType Actual Type.
     */
    public InvalidGameElementTypeException(String message, Throwable cause, GameElementType expectedGameElementType, GameElementType actualGameElementType) {
        super(message, cause);
        this.expectedGameElementType = expectedGameElementType;
        this.actualGameElementType = actualGameElementType;
    }

    /**
     * Create a InvalidGameElementTypeException.
     * @param cause Cause.
     * @param expectedGameElementType Expected type.
     * @param actualGameElementType Actual type.
     */
    public InvalidGameElementTypeException(Throwable cause, GameElementType expectedGameElementType, GameElementType actualGameElementType) {
        super(cause);
        this.expectedGameElementType = expectedGameElementType;
        this.actualGameElementType = actualGameElementType;
    }

    public GameElementType getExpectedGameElementType() {
        return expectedGameElementType;
    }

    public GameElementType getActualGameElementType() {
        return actualGameElementType;
    }
}
