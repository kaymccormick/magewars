package us.heptet.magewars.webapp.client.presenter;

import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.view.MainView;

/**
 * Created by jade on 20/08/2016.
 */

/**
 * Presenter for the Main view
 */
public class MainPresenter implements Presenter, MainView.Presenter {
    private final MainView view;

    /**
     * Create the Presenter
     * @param mainView
     * @param eventManager
     */
    public MainPresenter(MainView mainView, EventManager eventManager) {
        this.view = mainView;
        this.view.setPresenter(this);
    }

    @Override
    public void go(Container container) {
        container.clear();
        container.add(view);
    }

    @Override
    public void onNavigateAway() {
        /* nothing to do */
    }
}
