package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import us.heptet.magewars.domain.game.Card;

/* Created by kay on 3/13/14. */
/**
 * Asynchronous version of MageWarsCardsService
 */
public interface MageWarsCardsServiceAsync {
    /**
     * Get cards
     * @param callback
     */
    void getCards(AsyncCallback<Card[]> callback);
}
