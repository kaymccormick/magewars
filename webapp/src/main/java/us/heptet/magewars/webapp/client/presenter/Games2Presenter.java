package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.GameServiceAsync;
import us.heptet.magewars.webapp.client.view.GamesViewImpl2;

/**
 * Created by jade on 19/08/2016.
 */
public class Games2Presenter implements Presenter {
    private final HandlerManager eventBus;
    private final GamesViewImpl2 gamesViewImpl2;
    private final GameServiceAsync gameServiceAsync;
    private final EventManager eventManager;

    public Games2Presenter(HandlerManager eventBus, GamesViewImpl2 gamesViewImpl2, GameServiceAsync gameServiceAsync, EventManager eventManager) {

        this.eventBus = eventBus;
        this.gamesViewImpl2 = gamesViewImpl2;
        this.gameServiceAsync = gameServiceAsync;
        this.eventManager = eventManager;
    }

    @Override
    public void go(Container container) {

    }

    @Override
    public void onNavigateAway() {

    }
}
