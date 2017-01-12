package us.heptet.magewars.ui.controller;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.ZoneImpl;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.PlayerInfo;
import us.heptet.magewars.game.events.EventHandler;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.phase.ChannelPhase;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.phase.PlanningPhase;
import us.heptet.magewars.test.GameTestHelper;
import us.heptet.magewars.test.matchers.EventMatcher;
import us.heptet.magewars.test.game.EventHandlerAction;
import us.heptet.magewars.ui.view.GameView;
import us.heptet.magewars.ui.view.PlanSpellsView;

import java.util.ArrayList;
import java.util.List;

public class GameControllerTest {

    private Mockery mockery;
    private GameSituation gameSituation;
    private GameView gameView;
    private GameController gameController;
    private PlayerInfo playerInfo;
    private PlanSpellsController planSpellsController;
    private GameControl gameControl;

    private EventHandlerAction<GameEvent> newPhaseEvent;
    private GameTestHelper gameTestHelper;

    @BeforeMethod
    public void setUp() throws Exception {

        gameTestHelper = new GameTestHelper();
        mockery = new Mockery();

        mockery.setImposteriser(ClassImposteriser.INSTANCE);

        gameView = mockery.mock(GameView.class);
        playerInfo = mockery.mock(PlayerInfo.class);
        planSpellsController = mockery.mock(PlanSpellsController.class);
        EventManager eventManager = mockery.mock(EventManager.class);

        newPhaseEvent = new EventHandlerAction<>();

        gameSituation = mockery.mock(GameSituation.class);

        gameControl = mockery.mock(GameControl.class);
        mockery.checking(new Expectations() {{
            oneOf(gameView).setController(with(any(GameView.Controller.class))); // check any
            allowing(gameSituation).getNumPlayers();
            // add new listening expectations here
            oneOf(gameControl).addEventHandler(with(equal(PhaseEvent.NEW_PHASE)), with(any(EventHandler.class)));
            oneOf(gameControl).addEventHandler(with(equal(GameEvent.ADD_OBJECT)), with(any(EventHandler.class)));
            oneOf(gameControl).addEventHandler(with(equal(GameEvent.CHANGE_INITIATIVE)), with(any(EventHandler.class)));
            oneOf(gameControl).addEventHandler(with(equal(GameEvent.PLAYER_INFO)), with(any(EventHandler.class)));
            will(newPhaseEvent);
            allowing(gameControl).getGameSituation();
            will(returnValue(gameSituation));

        }});

        gameController = new GameController(gameView, gameControl, planSpellsController);//, eventManager);


    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testNop() throws Exception {
        mockery.assertIsSatisfied();
    }

    @Test
    public void testOnPhaseChanged() throws Exception {
        /* FIXME getplayers is returning default value - should throw error? */
        mockery.checking(new Expectations() {
            {
                allowing(gameSituation).getPlayers();
                allowing(gameSituation).getGameControl();
            }
        });

        PhaseInterface newPhase = new ChannelPhase(gameSituation); // should be mock? - probably

        mockery.checking(new Expectations() {
            {
            }
        });

        gameController.onPhaseChanged(newPhase);
        mockery.assertIsSatisfied();
    }

    /*
     * What are we trying to test here??
     */
    @Test
    public void testOnPhaseChangedToPlanningPhase() throws Exception {
        /* FIXME getplayers is returning default value - should throw error? */
        mockery.checking(new Expectations() {
            {
                allowing(gameSituation).getPlayers();
                allowing(gameSituation).getGameControl();
            }
        });

        PhaseInterface newPhase = new PlanningPhase(gameSituation); // should be mock? - probably

        final PlanSpellsView planSpellsView = mockery.mock(PlanSpellsView.class);
        final Player player1 = mockery.mock(Player.class, "player1");
        final Player player2 = mockery.mock(Player.class, "player2");
        final List<Player> playersInitiativeOrder = new ArrayList<Player>();
        playersInitiativeOrder.add(player1);
        playersInitiativeOrder.add(player2);
        mockery.checking(new Expectations() {
            {
                oneOf(gameSituation).getPlayersInitiativeOrder();
                will(returnValue(playersInitiativeOrder));
                oneOf(planSpellsController).showForPlayer(with(player1));
                oneOf(planSpellsController).getView();
                will(returnValue(planSpellsView));
                oneOf(gameView).showSpellbookViewer(with(planSpellsView));
            }
        });

        // should we verify planning player queue?
        // currently this is unset and not used - clearly not fully baked

        gameController.onPhaseChanged(newPhase);
        mockery.assertIsSatisfied();
    }

    @Test
    public void testOnStartButtonClicked() throws Exception {
        mockery.checking(new Expectations() {{
            oneOf(gameControl).fireEvent(with(EventMatcher.matchesEventType(GameEvent.START_GAME)));
        }});
        gameController.onStartButtonClicked();

    }

/*
    @Test(groups={"broken"})
    public void testOnAddObject() throws Exception {
        GameObject gameObject = mockery.mock(GameObject.class);
        mockery.checking(new Expectations(){{
            oneOf(gameObject).getLocation(); will(returnValue(GameElementFactory.createZone(0, 0)));
            oneOf(gameObject).getPlayerCard(); will(returnValue(gameTestHelper.enchantmentPlayerCard(gameTestHelper.player1())));
            oneOf(gameObject).getControllingPlayer(); will(returnValue(gameTestHelper.player1()));
        }});
        GameEvent gameEvent = new GameEvent(GameEvent.ADD_OBJECT, gameObject);
        gameController.onAddObject(gameEvent);

    }
*/

}