package us.heptet.magewars.domain.persistence.exceptions;

/* Created by jade on 12/09/2016. */

/**
 * Card not found exception.
 */
public class CardNotFoundInRepositoryException extends RuntimeException{
    private final String cardEnumName;

    /**
     * Construct instance.
     * @param cardEnumName Card enum name.
     */
    public CardNotFoundInRepositoryException(String cardEnumName) {
        this.cardEnumName = cardEnumName;
    }

    /**
     * Construct instance.
     * @param message Message.
     * @param cardEnumName Card enum name.
     */
    public CardNotFoundInRepositoryException(String message, String cardEnumName) {
        super(message);
        this.cardEnumName = cardEnumName;
    }

    /**
     * Construct instance.
     * @param message Message.
     * @param cause Throwable cause.
     * @param cardEnumName Card enum name.
     */
    public CardNotFoundInRepositoryException(String message, Throwable cause, String cardEnumName) {
        super(message, cause);
        this.cardEnumName = cardEnumName;
    }

    /**
     * Construct instance.
     * @param cause Cause.
     * @param cardEnumName     Card Enum Name.
     */
    public CardNotFoundInRepositoryException(Throwable cause, String cardEnumName) {
        super(cause);
        this.cardEnumName = cardEnumName;
    }

    public String getCardEnumName() {
        return cardEnumName;
    }
}
