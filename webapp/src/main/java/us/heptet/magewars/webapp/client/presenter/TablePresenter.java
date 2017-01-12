package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.game.events.*;
import us.heptet.magewars.gameservice.core.events.games.GameExtendedDetails;
import us.heptet.magewars.gameservice.core.events.games.GameJoinedEvent;
import us.heptet.magewars.gameservice.core.events.games.GamePlayerDetails;
import us.heptet.magewars.gameservice.core.events.games.GameStartedEvent;
import us.heptet.magewars.gameservice.core.events.games.JoinGameEvent;
import us.heptet.magewars.gameservice.core.events.games.StartGameEvent;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.AuthenticationState;
import us.heptet.magewars.webapp.client.GameServiceAsync;
import us.heptet.magewars.webapp.client.view.TableView;
import us.heptet.magewars.webapp.client.view.TableViewImpl;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 5/20/2014. */
/**
 * Presenter class for the "table view."
 */

public class TablePresenter implements TableView.Presenter, Presenter  {
    private static Logger logger = Logger.getLogger(TablePresenter.class.getName());
    private TableView view;
    private GameServiceAsync gameService;
    private EventManager eventManager;
    private int gameId;
    private List<GamePlayerDetails> playerDetailsList;
    private AuthenticationState authenticationState;
    private final EventHandler<GameEvent> handler;


    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create an instance.
     * @param tableView
     * @param gameService
     * @param cardSet
     * @param eventManager
     */
    @Inject
    public TablePresenter(TableView tableView, GameServiceAsync gameService, CardSet cardSet, EventManager eventManager) {
        this.view = tableView;
        this.gameService = gameService;
        this.eventManager = eventManager;
        assert eventManager != null;
        handler = event -> {
            if (event.getUsername() != authenticationState.getPrincipal()) {
                if(event.getUsername() == null || event.getMage() == null)
                {
                    logger.fine("username or mage is null, not calling updateMage");
                }
                else {
                    view.updateMage(event.getUsername(), event.getMage());
                }
            }
        };

        this.view.setPresenter(this);
        tableView.setCardSet(cardSet);
    }

    private void onGameStarted(GameEvent event) {

        if(event.getGameId() == gameId)
        {
            eventManager.fireEvent(new NavigationEvent("play" + Integer.toString(gameId)));
        }
        else
        {
            assert false;
        }
    }

    private void onGameJoined(GameEvent event) {
        if(event.getGameId() == gameId)
        {
            if(playerDetailsList != null)
            {
                GamePlayerDetails newPlayer = new GamePlayerDetails(event.getPlayerSlot(), event.getUsername());
                if(event.getUsername().equals(event.getConnectedUserName())) {
                    newPlayer.setRequestingPlayerSlot(true);
                }
                // Presenter should likely be responsible for sorting out the model prior to handing it off to the view, no?

                playerDetailsList.add(newPlayer);
                view.setPlayerDetailsList(playerDetailsList);

            }
        }
        else
        {
            logger.warning("Received a game joined event for ID " + event.getGameId() + " when I am ID " + gameId);
        }
    }

    /* Method that handles the "join game" button click on the TableView */
    @Override
    public void onJoinButtonClicked() {
        logger.info("join button clicked.");

        gameService.joinGame(new JoinGameEvent((String) authenticationState.getPrincipal(), gameId), new AsyncCallback<GameJoinedEvent>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.severe("joinGame RPC failed: " + caught.getMessage());
            }

            @Override
            public void onSuccess(GameJoinedEvent result) {
                logger.info("successful join game call rpc call");
            }
        });
    }

    @Override
    public void onStartButtonClick() {
        logger.info("start button clicked");
        gameService.startGame(new StartGameEvent(gameId), new AsyncCallback<GameStartedEvent>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.severe("gameService.startGame rpc call failed " + caught.getMessage());
            }

            @Override
            public void onSuccess(GameStartedEvent result) {
                logger.info(result.getMessage());
                logger.info("gameService.startGame (gameStarted = " + result.isGameStarted()  + ")");
                // Display success or failure TODO
            }
        });
    }


    public void setId(int id)
    {
        this.gameId = id;
        gameService.requestGameExtendedDetails(new RequestGameExtendedDetailsEvent(id), new AsyncCallback<GameExtendedDetailsEvent>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        logger.severe("RPC Failed: " + throwable.getMessage());
                        History.newItem("login");
                    }

                    @Override
                    public void onSuccess(GameExtendedDetailsEvent gameExtendedDetailsEvent) {
                        if (gameExtendedDetailsEvent != null) {
                            TablePresenter.this.onGameExtendedDetailsEvent(gameExtendedDetailsEvent);
                        }
                    }
                });
        eventManager.fireEvent(new GameEvent(GameEvent.VIEW_TABLE, id, null, (String) authenticationState.getPrincipal(), null));
    }

    private void onGameExtendedDetailsEvent(GameExtendedDetailsEvent gameExtendedDetailsEvent) {

        playerDetailsList = gameExtendedDetailsEvent.getGameExtendedDetails().getPlayers();
        logger.info("size of playerdetailslist = " + playerDetailsList.size());

        this.setGameExtendedDetails(gameExtendedDetailsEvent.getGameExtendedDetails());
    }

    private void setGameExtendedDetails(GameExtendedDetails gameExtendedDetails) {
        view.setGameExtendedDetails(gameExtendedDetails);
    }

    @Override
    public void go(Container container) {
        bind();

        container.clear();

        eventManager.setCacheUnhandledEvents(true);
        eventManager.addEventHandler(GameEvent.SPELLBOOK_SELECTED, handler);
        eventManager.addEventHandler(GameEvent.GAME_JOINED, TablePresenter.this::onGameJoined);
        eventManager.addEventHandler(GameEvent.GAME_STARTED, TablePresenter.this::onGameStarted);
        eventManager.subscribe(new TableEventSubscription(gameId));

        container.add(view);
    }

    @Override
    public void onNavigateAway() {
        eventManager.removeEventHandler(GameEvent.SPELLBOOK_SELECTED, handler);
        eventManager.removeEventHandler(GameEvent.GAME_JOINED, TablePresenter.this::onGameJoined);
        eventManager.removeEventHandler(GameEvent.GAME_STARTED, TablePresenter.this::onGameStarted);
    }

    private void bind() {
        // Why is this empty?
    }

    @Override
    public AuthenticationState getAuthenticationState() {
        return authenticationState;
    }

    @Override
    public void onSpellbookSelectionChange(TableViewImpl.SpellbookNode spellbookNode) {
        // Messaging
        GameEvent event = new GameEvent(GameEvent.SPELLBOOK_SELECTED, gameId, null, (String) authenticationState.getPrincipal(), null);
        event.setMage(spellbookNode.getMage());
        event.setSpellbookId(spellbookNode.getSpellbookId());

        eventManager.fireEvent(event);
    }


    public void setAuthenticationState(AuthenticationState authenticationState) {
        this.authenticationState = authenticationState;
    }
}
