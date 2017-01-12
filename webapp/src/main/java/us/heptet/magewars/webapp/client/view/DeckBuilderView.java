package us.heptet.magewars.webapp.client.view;

import us.heptet.magewars.ui.view.View;

/**
 * Created by jade on 21/08/2016.
 */

/**
 * View for Deck Builder
 */
public interface DeckBuilderView extends View {
    /**
     * Presenter interface.
     */
    interface Presenter {

    }

    /**
     * Set the presenter.
     * @param presenter
     */
    void setPresenter(DeckBuilderView.Presenter presenter);
}
