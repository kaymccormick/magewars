package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import us.heptet.magewars.domain.game.Card;

/* Created by kay on 3/13/14. */
/**
 * Service to retrieve mage wars cards.
 */
@RemoteServiceRelativePath("springGwtServices/mageWarsCards")
public interface MageWarsCardsService extends RemoteService {
    /**
     * Get the cards.
     * @return
     */
    Card[] getCards();
}
