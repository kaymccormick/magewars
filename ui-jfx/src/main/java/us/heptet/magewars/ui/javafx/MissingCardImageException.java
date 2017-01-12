package us.heptet.magewars.ui.javafx;

import us.heptet.magewars.domain.game.CardEnum;

/* Created by kay on 6/16/2014. */
/**
 * Exception for a missing card image. This class needs help, clearly. Should we move this to a generic
 * exception package?
 */
public class MissingCardImageException extends RuntimeException {
    private final CardEnum cardEnum;
    private final String resourceName;

    /**
     * Construct exception.
     */
    public MissingCardImageException() {
        resourceName = null;
        cardEnum = null;
    }

    /**
     * Construct exception
     * @param cardEnum
     * @param resName
     */
    public MissingCardImageException(CardEnum cardEnum, String resName) {
        this.cardEnum = cardEnum;
        this.resourceName = resName;
    }

    /**
     * Get the name of the resource
     * @return
     */
    public String getResourceName() {
        return resourceName;
    }

    public CardEnum getCardEnum() {
        return cardEnum;
    }
}
