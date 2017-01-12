package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.game.events.*;
import us.heptet.magewars.gameservice.core.events.games.GameExtendedDetails;
import us.heptet.magewars.gameservice.core.events.games.GameJoinedEvent;
import us.heptet.magewars.gameservice.core.events.games.GamePlayerDetails;
import us.heptet.magewars.gameservice.core.events.games.GameStartedEvent;
import us.heptet.magewars.gameservice.core.events.games.JoinGameEvent;
import us.heptet.magewars.gameservice.core.events.games.SpellbookDetails;
import us.heptet.magewars.gameservice.core.events.games.StartGameEvent;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.test.game.EventHandlerAction;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.AuthenticationState;
import us.heptet.magewars.webapp.client.GameServiceAsync;
import us.heptet.magewars.webapp.client.view.TableView;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class TablePresenterTest {
    private static Logger logger = LoggerFactory.getLogger(GamesPresenterTest.class);
    private final String authorizedUsername = "authorizedUser";
    Mockery context;
    TablePresenter tablePresenter;
    TableView tableView;
    GameServiceAsync gameServiceAsync;
    EventManager eventManager;
    private CardSet cardSet;
    private EventHandlerAction<GameEvent> spellbookSelectionChangedAction;
    private EventHandlerAction<GameEvent> gameJoinedAction;
    private EventHandlerAction<GameEvent> gameStartedAction;
    private Integer gameId = 1;
    private String gameName = "my game";
    private String firstPlayerUsername = "firstUser";
    private Integer firstPlayerSlot = 0;
    private String createdByUsername = "creatingPlayer";
    private int minPlayers = 2;
    private int maxPlayers = 2;
    private List<GamePlayerDetails> players;
    private List<String> availableMages;
    private List<SpellbookDetails> defaultSpellbookDetailsList;
    private GameExtendedDetailsEvent gameExtendedDetailsEvent;
    private GameExtendedDetails gameExtendedDetails;
    private int joinedPlayerSlot = 1;
    private Container container;

    @BeforeMethod
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        context =  new Mockery();
        /* Using imposterizer so that we can mock classes as well as interfaces */
        context.setImposteriser(ClassImposteriser.INSTANCE);

        tableView = context.mock(TableView.class);
        container = context.mock(Container.class);

        gameServiceAsync  = context.mock(GameServiceAsync.class);
        eventManager = context.mock(EventManager.class);
        cardSet = context.mock(CardSet.class);
        spellbookSelectionChangedAction = new EventHandlerAction<>();
        gameJoinedAction = new EventHandlerAction<>();
        gameStartedAction = new EventHandlerAction<>();

        players = new ArrayList<>();
        availableMages = new ArrayList<>();
        defaultSpellbookDetailsList = new ArrayList<>();

        /* Our extended details filled with basic info */
        gameExtendedDetails = new GameExtendedDetails(gameId, gameName, createdByUsername, minPlayers, maxPlayers,
                players, availableMages, defaultSpellbookDetailsList);

        /* An event filled with said details */
        gameExtendedDetailsEvent = new GameExtendedDetailsEvent(gameExtendedDetails);

        /* Expectations for the instantiation of the TablePresenter */
        context.checking(new Expectations(){{
            oneOf(tableView).setPresenter(with(any(TableView.Presenter.class)));
            oneOf(tableView).setCardSet(with(same(cardSet)));
            // TODO re-evaluate ignoring the container mock
            ignoring(container);
            allowing(eventManager).subscribe(with(any(EventSubscription.class)));

        }
        });
        tablePresenter = new TablePresenter(tableView, gameServiceAsync, cardSet, eventManager);
        tablePresenter.setAuthenticationState(new AuthenticationState(true));

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @SuppressWarnings("unchecked")
    public void setId()
    {
        final AsyncAction<GameExtendedDetailsEvent> gameExtCbAction =new AsyncAction<>();
        context.checking(new Expectations(){{
            // set Id
            oneOf(gameServiceAsync).requestGameExtendedDetails(with(equal(new RequestGameExtendedDetailsEvent(gameId))), with(any(AsyncCallback.class)));
            will(gameExtCbAction);
            oneOf(tableView).setGameExtendedDetails(with(same(gameExtendedDetails)));
        }});
        tablePresenter.setId(gameId);
        gameExtCbAction.succeedGiving(gameExtendedDetailsEvent);

    }
    @Test
    @SuppressWarnings("unchecked")
    public void testGameJoined() throws Exception {
        doGo();
        context.checking(new Expectations(){{
            oneOf(eventManager).fireEvent(with(us.heptet.magewars.test.matchers.EventMatcher.matchesEventType(GameEvent.VIEW_TABLE)));
        }});
        setId();
        context.checking(new Expectations(){{
            oneOf(tableView).setPlayerDetailsList(with(same(players)));
        }
        });
        int numPlayers = players.size();
        logger.info("current num players = {}", numPlayers);
        gameJoinedAction.yieldingEvent(new GameEvent(GameEvent.GAME_JOINED, gameId, gameName, firstPlayerUsername, firstPlayerSlot));
        int postEventNumPlayers = players.size();
        logger.info("post event num players = {}", postEventNumPlayers);

        context.assertIsSatisfied();
        assertEquals (postEventNumPlayers, numPlayers + 1);

    }

    private void doGo() {
        context.checking(new Expectations(){
            {
                oneOf(eventManager).setCacheUnhandledEvents(true);
                oneOf(eventManager).addEventHandler(with(equal(GameEvent.SPELLBOOK_SELECTED)),
                        with(any(EventHandler.class)));
                will(spellbookSelectionChangedAction);
                oneOf(eventManager).addEventHandler(with(equal(GameEvent.GAME_JOINED)),
                        with(any(EventHandler.class)));
                will(gameJoinedAction);
                oneOf(eventManager).addEventHandler(with(equal(GameEvent.GAME_STARTED)),
                        with(any(EventHandler.class)));
                will(gameStartedAction);
            }});
        tablePresenter.go(container);


    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnJoinButtonClicked() throws Exception {
        doGo();
        context.checking(new Expectations(){{
            oneOf(eventManager).fireEvent(with(us.heptet.magewars.test.matchers.EventMatcher.matchesEventType(GameEvent.VIEW_TABLE)));
        }});

        setId();
        final AuthenticationState authenticationState = context.mock(AuthenticationState.class);
        final AsyncAction<GameJoinedEvent> gameJoinedEventAsyncAction = new AsyncAction<>();
        tablePresenter.setAuthenticationState(authenticationState);
        context.checking(new Expectations() {
            {
                oneOf(authenticationState).getPrincipal();
                will(returnValue(authorizedUsername));
                oneOf(gameServiceAsync).joinGame(with(any(JoinGameEvent.class)), with(any(AsyncCallback.class)));
                will(gameJoinedEventAsyncAction);
                oneOf(tableView).setPlayerDetailsList(with(any(List.class)));
            }
        });
        tablePresenter.onJoinButtonClicked();
        gameJoinedEventAsyncAction.succeedGiving(new GameJoinedEvent(joinedPlayerSlot, true));
        gameJoinedAction.yieldingEvent(new GameEvent(GameEvent.GAME_JOINED, gameId, gameName, authorizedUsername, joinedPlayerSlot));
        context.assertIsSatisfied();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnStartButtonClick() throws Exception {
        doGo();

        context.checking(new Expectations(){{
            oneOf(eventManager).fireEvent(with(us.heptet.magewars.test.matchers.EventMatcher.matchesEventType(GameEvent.VIEW_TABLE)));
        }});

        setId();
        final AsyncAction<GameStartedEvent> gameStartedEventAsyncAction = new AsyncAction<>();
        context.checking(new Expectations(){{
            oneOf(gameServiceAsync).startGame(with(any(StartGameEvent.class)), with(any(AsyncCallback.class)));
            will(gameStartedEventAsyncAction);
            oneOf(eventManager).fireEvent(with(equal(new NavigationEvent("play" + gameId))));
        }
        });
        tablePresenter.onStartButtonClick();
        gameStartedEventAsyncAction.succeedGiving(new GameStartedEvent(true));
        gameStartedAction.yieldingEvent(new GameEvent(GameEvent.GAME_STARTED, gameId, gameName, null, null));
        context.assertIsSatisfied();
    }

    //@Test(groups={"broken"})
    public void testGo() throws Exception {
        final Container container = context.mock(Container.class);
        context.checking(new Expectations(){{
            oneOf(container).clear();;
            oneOf(container).add(with(same(tableView)));
        }
        });
        tablePresenter.go(container);
        context.assertIsSatisfied();

    }
}