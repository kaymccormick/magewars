package us.heptet.magewars.ui.view;

import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.EventHandler;

/* Created by kay on 4/8/2014. */
/**
 * Interface for a "Card View" - a user-interface view which displays a card,
 * or the back of a card. It may or may not have an underlying {@link PlayerCard}.
 */
public interface CardView extends Control {
    /**
     * Retrieve the underlying {@link PlayerCard} associated with the view.
     * @return
     */
    PlayerCard<? extends Card> getPlayerCard();

    /**
     * Set the "on mouse clicked" handler.
     * @param handler The event handler.
     */
    void setOnMouseClicked(EventHandler handler);

    /***
     * Convenience method to retrieve the {@link Card} instance from the underlying {@link PlayerCard}.
     * @return The {@link Card} instance.
     */
    Card getCard();

    /***
     * Set the underlying {@link PlayerCard}
     * @param playerCard The {@link PlayerCard} to associate and display in the Card View.
     */
    void setPlayerCard(PlayerCard<? extends Card> playerCard);
}
