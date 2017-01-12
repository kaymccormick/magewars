package us.heptet.magewars.webapp.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Singleton;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.*;
import us.heptet.magewars.game.events.EventChannel;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.NavigationEvent;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.ui.SpellBookViewer;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.webapp.client.gwt.GameViewImpl;
import us.heptet.magewars.webapp.client.gwt.MyResources;
import us.heptet.magewars.webapp.client.presenter.*;
import us.heptet.magewars.webapp.client.util.MonitorTimer;
import us.heptet.magewars.webapp.client.view.*;

import javax.inject.Inject;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 5/14/2014. */
/**
 * AppController class in the GWT MVP design.
 */
@Singleton
public class AppController implements Presenter, ValueChangeHandler<String> {
    private static Logger logger = Logger.getLogger(AppController.class.getName());
    private final HandlerManager eventBus;
    private final MonitorTimer monitorTimer;
    private AuthenticationState authenticationState;
    private Container container;
    private EventManager eventManager;
    private Injector injector;
    private Presenter presenter;
    private LoginServiceAsync loginServiceAsync;
    private SignupServiceAsync signupServiceAsync;
    private GameServiceAsync gameServiceAsync;
    private Panel logPanel;

    @Inject
    private TableView tableView;

    @Inject
    CardSet cardSet;
    private MyResources resources;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Create an AppController instance.
     * @param eventBus  The HandlerManager.
     * @param loginServiceAsync Login service async.
     * @param signupServiceAsync Signup service async.
     * @param authenticationState Authentication state.
     * @param gameServiceAsync Game Service async.
     * @param eventManager Event Manager.
     * @param eventChannelSet Event channel set.
     * @param injector The injector.
     */
    @Inject
    public AppController(HandlerManager eventBus,
                         LoginServiceAsync loginServiceAsync,
                         SignupServiceAsync signupServiceAsync,
                         AuthenticationState authenticationState,
                         GameServiceAsync gameServiceAsync,
                         EventManager eventManager,
                         Set<EventChannel> eventChannelSet,
                         Injector injector

    ) {
        this.eventBus = eventBus;
        this.loginServiceAsync = loginServiceAsync;
        this.signupServiceAsync = signupServiceAsync;
        this.authenticationState = authenticationState;
        this.gameServiceAsync = gameServiceAsync;
        this.eventManager = eventManager;

        eventManager.addEventHandler(NavigationEvent.ANY, AppController.this::onNavigationEvent);
        assert eventChannelSet != null;
        eventManager.addAndConnectChannels(eventChannelSet);
        this.injector = injector;

        monitorTimer = new MonitorTimer();
        monitorTimer.scheduleRepeating(2000);
        bind();
    }

    private void onNavigationEvent(NavigationEvent event) {
        History.newItem(event.getToken());
    }

    private void bind() {
        History.addValueChangeHandler(this);
    }


    @Override
    public void go(Container container) {
        logger.finest("AppController.go called with " + container.getClass().getName());
        this.container = container;

        if ("".equals(History.getToken())) {
            History.newItem("games");
        } else {
            History.fireCurrentHistoryState();
        }
    }

    @Override
    public void onNavigateAway() {
        /* Unused */
    }

    /**
     * It's not clear what this handles.
     *
     * @param event string value change event?
     */
    @Override
    public void onValueChange(final ValueChangeEvent<String> event) {
        String token = event.getValue();
        logger.fine("token = " + token);
        if(container == null)
        {
            logger.severe("no container! got " + event);
            return;
        }

        if (token == null) {
            return;
        }

            Presenter oldPresenter = getPresenter();
            AppController.this.setPresenter(null);

            if ("home".equals(token)) {
                AppController.this.setPresenter(new HomePresenter(new HomeViewImpl(), authenticationState));
            } else if ("login".equals(token)) {
                AppController.this.setPresenter(new LoginPresenter(new LoginViewImpl(), loginServiceAsync, eventManager));
            } else if ("main".equals(token)) {
                AppController.this.setPresenter(new MainPresenter(new MainViewImpl(), eventManager));
            } else if ("signup".equals(token)) {
                AppController.this.setPresenter(new SignupPresenter(new SignupViewImpl(), signupServiceAsync, eventManager));
            } else if ("games".equals(token)) {
                AppController.this.setPresenter(new GamesPresenter(eventBus, new GamesViewImpl(), gameServiceAsync, eventManager));
            } else if ("builddeck".equals(token)) {
                SpellBookViewer spellBookViewer = new SpellBookViewer(injector.getUiFactory());
                AppController.this.setPresenter(new DeckBuilderPresenter(new DeckBuilderViewImpl(spellBookViewer, injector.getUiFactory()), eventManager));
            } else if ("games2".equals(token)) {
                AppController.this.setPresenter(new Games2Presenter(eventBus, new GamesViewImpl2(), gameServiceAsync, eventManager));
            } else if (token.startsWith("play")) {
                play(token);
            } else if (token.startsWith("table")) {
                String suffix = token.substring(5);
                int tableId = Integer.parseInt(suffix);

                tableView.setId(tableId);

                TablePresenter tablePresenter = new TablePresenter(tableView,
                        gameServiceAsync, cardSet, eventManager);
                tablePresenter.setAuthenticationState(authenticationState);
                tablePresenter.setId(tableId);
                AppController.this.setPresenter(tablePresenter);

            }

            logger.fine("presenter = " + AppController.this.getPresenter());
            if (AppController.this.getPresenter() != null) {
                logger.finest("calling presenter.go");

                if (oldPresenter != null) {
                    container.clear();
                    oldPresenter.onNavigateAway();
                }

                AppController.this.getPresenter().go(container);
            }
    }

    private void play(String token) {
        logger.info("history token playXX");
        String suffix = token.substring(4);
        int gameId = Integer.parseInt(suffix);

        GameControl gameControl = injector.getGameControl();

        GameSituation gameSituation = gameControl.getGameSituation();
        gameSituation.setGameIdentity(gameId);

        // hack because we're fixed at 2 players
        gameSituation.setNumPlayers(2);
        gameSituation.addPlayer(GameElementFactory.createPlayer(0));
        gameSituation.addPlayer(GameElementFactory.createPlayer(1));

        GameViewImpl gameView = injector.getGameView();

        gameView.setGameSituation(gameSituation);

        GameController gameController = injector.getGameController();

        GamePresenter gamePresenter = new GamePresenter(gameView,
                gameController,
                gameId, eventManager);
        AppController.this.setPresenter(gamePresenter);
        gamePresenter.setGame(gameSituation);
    }

    public AuthenticationState getAuthenticationState() {
        return authenticationState;
    }

    public void setLogPanel(Panel logPanel) {
        this.logPanel = logPanel;
    }

    public Panel getLogPanel() {
        return logPanel;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        monitorTimer.setPresenter(presenter);
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public MyResources getResources() {
        return resources;
    }

    public void setResources(MyResources resources) {
        this.resources = resources;
    }
}
