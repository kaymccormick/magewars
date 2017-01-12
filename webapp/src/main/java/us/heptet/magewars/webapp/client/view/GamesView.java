package us.heptet.magewars.webapp.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.ui.view.View;

import java.util.List;

/* Created by kay on 5/19/2014. */
/**
 * Games View interface
 */
public interface GamesView extends View {
    /**
     * Presenter interface for Games View Presenter
     */
    interface Presenter
    {
        /**
         * On create button clicked
         */
        void onCreateButtonClicked();

        /**
         * On refresh button clicked
         */
        void onRefreshButtonClicked();
    }

    /**
     * Has click handlers for createbutton
     * @return
     */
    HasClickHandlers getCreateButton();

    /**
     * HasClickHandlers for refresh button
     * @return
     */
    HasClickHandlers getRefreshButton();

    /**
     * set the data associated with the view
     * @param data
     */
    void setData(List<GameDetails> data);

    /**
     * Get the view as a GWT widget
     * @return
     */
    Widget asWidget();

    /**
     * Set the presenter for the view
     * @param presenter
     */
    void setPresenter(GamesView.Presenter presenter);
}

