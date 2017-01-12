package us.heptet.magewars.webapp.client.presenter;

import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.view.DeckBuilderView;

/**
 * Created by jade on 21/08/2016.
 */

/**
 * Deck builder presenter.
 */
public class DeckBuilderPresenter implements Presenter, DeckBuilderView.Presenter {
    private final DeckBuilderView view;

    /**
     * Create instance
     * @param view
     * @param eventManager
     */
    public DeckBuilderPresenter(DeckBuilderView view, EventManager eventManager) {
        this.view = view;
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
