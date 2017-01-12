package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import us.heptet.magewars.game.events.EventHandler;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.CreateGame;
import us.heptet.magewars.webapp.client.GameServiceAsync;
import us.heptet.magewars.webapp.client.view.GamesView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 5/19/2014. */
/**
 * Games presenter.
 */
public class GamesPresenter implements Presenter, GamesView.Presenter {
    private static Logger logger = Logger.getLogger(GamesPresenter.class.getName());

    private List<GameDetails> gamesDetails;
    private GamesView view;
    private HandlerManager eventBus;
    private GameServiceAsync gameServiceAsync;
    private EventManager eventManager;
    private EventHandler<GameEvent> gameCreatedHandler;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create an instance
     * @param eventBus
     * @param gamesView
     * @param gameServiceAsync
     * @param eventManager
     */
    public GamesPresenter(HandlerManager eventBus, GamesView gamesView, GameServiceAsync gameServiceAsync, EventManager eventManager) {
        this.eventBus = eventBus;
        this.view = gamesView;
        this.gameServiceAsync = gameServiceAsync;
        this.eventManager = eventManager;
        // should this be in "go?"
        this.view.setPresenter(this);
    }

    @Override
    public void go(Container container) {
        bind();
        container.clear();
        container.add(view);
        refreshGames();
    }

    @Override
    public void onNavigateAway() {
        eventManager.removeEventHandler(GameEvent.GAME_CREATED, gameCreatedHandler);
    }


    /**
     * Bind
     */
    public void bind()
    {
        gameCreatedHandler = gameCreatedEvent -> {
            gamesDetails.add(new GameDetails(gameCreatedEvent.getGameId(), gameCreatedEvent.getGameName(),
                    gameCreatedEvent.getUsername(), 0, 0));
            view.setData(gamesDetails);
        };
        eventManager.addEventHandler(GameEvent.GAME_CREATED, gameCreatedHandler);
    }

    private void refreshGames() {
        this.gameServiceAsync.requestAllGames(new RequestAllGamesEvent(), new AsyncCallback<AllGamesEvent>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.severe("Failure to request all games: " + throwable.getMessage());
                History.newItem("login");
            }

            @Override
            public void onSuccess(AllGamesEvent allGamesEvent) {
                gamesDetails = allGamesEvent.getGamesDetails();
                view.setData(gamesDetails);
            }
        });
    }

    @Override
    public void onCreateButtonClicked() {
        CreateGame createGame = new CreateGame(gameServiceAsync);
        createGame.getDialogBox().show();
    }

    @Override
    public void onRefreshButtonClicked() {
        logger.severe("refreshing");
        refreshGames();
    }

    public List<GameDetails> getGamesDetails() {
        return gamesDetails;
    }
}
