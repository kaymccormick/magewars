package us.heptet.magewars.webapp.client.view;

import us.heptet.magewars.ui.view.View;

/**
 * Created by jade on 20/08/2016.
 */

/**
 * Main view interface
 */
public interface MainView extends View {
    /**
     * Presenter interface.
     */
    interface Presenter {

    }

    /**
     * Set the presenter.
     * @param presenter
     */
    void setPresenter(MainView.Presenter presenter);
}
