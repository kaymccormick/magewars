package us.heptet.magewars.webapp.client.view;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.view.client.HasData;
import com.google.inject.ImplementedBy;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.gameservice.core.events.games.GameExtendedDetails;
import us.heptet.magewars.gameservice.core.events.games.GamePlayerDetails;
import us.heptet.magewars.ui.view.View;
import us.heptet.magewars.webapp.client.AuthenticationState;

import java.util.List;

/* Created by kay on 5/20/2014. */
/**
 * Table view interface.
 */
@ImplementedBy(TableViewImpl.class)
public interface TableView extends View {
    /**
     * Set the card set
     * @param cardSet
     */
    void setCardSet(CardSet cardSet);

    /**
     * Presenter interface for TableView presenters
     */
    interface Presenter
    {
        /**
         * On join button clicked.
         */
        void onJoinButtonClicked();

        /**
         * on start button clicked
         */
        void onStartButtonClick();

        /**
         * Get the authentication state (unused)
         * @return
         */
        AuthenticationState getAuthenticationState();

        /**
         * On spell book selection change
         * @param spellbookNode
         */
        void onSpellbookSelectionChange(TableViewImpl.SpellbookNode spellbookNode);


    }

    /**
     * Set the presenter for this view
     * @param presenter
     */
    void setPresenter(Presenter presenter);

    /**
     * Set the ID for the table being viewed
     * @param id
     */
    void setId(int id);

    /**
     * Set the "game extended details" (information displayed in the view)
     * @param details
     */
    void setGameExtendedDetails(GameExtendedDetails details);

    /**
     * Set the "player details list" (relevant player information)
     * @param detailsList
     */
    void setPlayerDetailsList(List<GamePlayerDetails> detailsList);

    /**
     * Update a player's mage
     * @param userName
     * @param mage
     */
    void updateMage(String userName, String mage);

    /**
     * HasData for the player details
     * @return
     */
    HasData<GamePlayerDetails> getPlayerDetails(); /* this is how the data is setPlayerCard, through HasData interface */

    /**
     * HasData for the gameExtendedDetails (unused)
     * @return
     */
    HasValue<GameExtendedDetails> getGameExtendedDetails();
}
