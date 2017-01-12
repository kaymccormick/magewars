package us.heptet.magewars.game;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.domain.game.Mage;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.exceptions.GameControlException;
import us.heptet.magewars.domain.game.exceptions.GameInitializationException;
import us.heptet.magewars.domain.game.exceptions.GameNotInProgressException;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;
import us.heptet.magewars.game.events.EventHandler;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.EventManagerImpl;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.exceptions.PlayerMagesNotSet;
import us.heptet.magewars.game.phase.InitiativePhase;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.domain.game.setup.GameSetup;
import us.heptet.magewars.game.spellbook.SpellBookInitializer;
import us.heptet.magewars.game.stage.ReadyStage;
import us.heptet.magewars.game.stage.Stage;
import us.heptet.magewars.game.state.GameStateChangeProcessor;

import java.util.ArrayList;
import java.util.List;

public class GameControlImplTest {

    private static Logger logger = LoggerFactory.getLogger(GameControlImplTest.class);
    private int numPlayers = 0;
    private GameStateChangeProcessor processor;
    GameControlImpl gameControlImpl;
    GameSituation gameSituation;
    private Mockery mockery;
    private GameSetup gameSetup;
    private PlayerInfo mockPlayerInfo;
    private EventManager eventManager;
    private final List<Mage> mages = new ArrayList<Mage>();
    private SpellBookInitializer spellBookInitializer;
    private States gameInProgressState;
    private CardSet cardSet;

    @BeforeMethod
    public void setUp() throws Exception {

        logger.trace("Set up");

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        mages.add(new BeastMaster());
        mages.add(new Warlock());

        mockery = new JUnit4Mockery() {{
            setThreadingPolicy(new Synchroniser());
        }};
        mockery.setImposteriser(ClassImposteriser.INSTANCE);

        gameSituation = mockery.mock(GameSituation.class);

        gameSetup = mockery.mock(GameSetup.class);
        eventManager = new EventManagerImpl();

        processor = mockery.mock(GameStateChangeProcessor.class);
        cardSet = new BaseCardSet();
        spellBookInitializer = new SpellBookManager(cardSet);

        mockery.checking(new Expectations(){{
            oneOf(gameSituation).setGameControl(with(any(GameControl.class)));
            allowing(gameSituation).getArena();
        }});


        /* This sets up event handlers (new functionality as of 2016/09/11) which requires expectations.
           We can either allow anything or expect specific kinds of events. We should have a test helper. Right now we have to duplicate
           expectations in GameTest.java
         */
        gameControlImpl = new GameControlImpl(gameSituation, eventManager, spellBookInitializer);

        gameControlImpl.setGameSetup(gameSetup);

        gameControlImpl.setGameStateChangeProcessor(processor);

        numPlayers = 2;
        gameInProgressState = mockery.states("gameInProgress").startsAs("false");
        mockery.checking(new Expectations(){{
            allowing(gameSituation).getPlayers();
            allowing(gameSituation).changeInitiative();
            allowing(gameSituation).getPlayerInfo(); will(returnValue(gameSituation));
            allowing(gameSetup).getNumPlayers(); will(returnValue(numPlayers));
            allowing(gameSituation).getNumPlayers(); will(returnValue(numPlayers));
            allowing(gameSituation).getGameControl(); will(returnValue(gameControlImpl));
            allowing(gameSituation).getPlayer(with(any(Integer.class)));

            allowing(gameSituation).isGameInProgress(); when(gameInProgressState.is("false")); will(returnValue(false));
            allowing(gameSituation).isGameInProgress(); when(gameInProgressState.is("true")); will(returnValue(true));
            ignoring(processor);
        }

        });


        mockPlayerInfo = mockery.mock(PlayerInfo.class);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }



    @Test(expectedExceptions = {GameNotInProgressException.class})
    public void testInitiateRoundGameNotStarted() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameSituation).isGameInProgress(); will(returnValue(false));
        }});
        PhaseInterface p = gameControlImpl.initiateRound();
        mockery.assertIsSatisfied();
    }

    // this is confusing and wrong because startGame executes initiate round itself
    @Test
    public void testInitiateRound() throws Exception {
        doStartGame();
        PhaseInterface p = gameControlImpl.initiateRound();
        mockery.assertIsSatisfied();
    }

    private void doStartGame() {
        mockery.checking(new Expectations(){{
            oneOf(gameSetup).isSetupComplete(); will(returnValue(true));
            allowing(gameSituation).setNumPlayers(numPlayers);
            allowing(gameSituation).addPlayer(with(any(Player.class)));
            allowing(gameSetup).getPlayerMageCard(with(any(Integer.class))); will(returnValue(new BeastMaster()));
            oneOf(gameSituation).createPlayerMageGameObjects();
            oneOf(gameSituation).setGameInProgress(true); then(gameInProgressState.is("true"));
        }});

        gameControlImpl.startGame(gameSetup);
    }

    @Test
    public void testStartGame() throws Exception {
        doStartGame();

        mockery.assertIsSatisfied();
    }

    @Test(expectedExceptions = {GameInitializationException.class})
    public void testStartGameAlreadyStarted() throws Exception {
        doStartGame();

        doStartGame();

        mockery.assertIsSatisfied();
    }

    @Test(expectedExceptions = {GameInitializationException.class})
    public void testEnsureGameReadyToStartPlayerMagesNotSet() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameSetup).isSetupComplete(); will(returnValue(false));
        }
        });

        gameControlImpl.ensureGameReadyToStart();
        mockery.assertIsSatisfied();
    }

    @Test
    public void testEnsureGameReadyToStart() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameSetup).isSetupComplete(); will(returnValue(true));
        }
        });

        gameControlImpl.ensureGameReadyToStart();

        mockery.assertIsSatisfied();
    }


    @Test
    public void testNextPhase() throws Exception {
        Stage stage = mockery.mock(Stage.class);

        gameControlImpl.setCurrentStage(stage);
        mockery.checking(new Expectations() {{
            //oneOf(gameSituation).getCurrentStage(); will(returnValue(stage));
            oneOf(stage).nextPhase(processor);
        }});
        gameControlImpl.nextPhase();

    }

    @Test
    public void testNextStage() throws Exception {
        Round round = mockery.mock(Round.class);
        mockery.checking(new Expectations() {{
            oneOf(round).nextStage(processor);
        }});
        gameControlImpl.setCurrentRound(round);
        gameControlImpl.nextStage();
    }

    @Test(expectedExceptions = GameControlException.class)
    public void testOnCompletePhaseEventNoCurrentPhase() throws Exception {
        gameControlImpl.fireEvent(new PhaseEvent(PhaseEvent.COMPLETE_PHASE));
    }

    @Test(expectedExceptions = GameControlException.class)
    public void testOnCompletePhaseEventNoCurrentStage() throws Exception {
        gameControlImpl.setCurrentPhase(new InitiativePhase());
        gameControlImpl.fireEvent(new PhaseEvent(PhaseEvent.COMPLETE_PHASE));
    }

    @Test(expectedExceptions = GameControlException.class)
    public void testOnCompletePhaseEvent() throws Exception {
        gameControlImpl.setCurrentPhase(new InitiativePhase());
        gameControlImpl.setCurrentStage(new ReadyStage());
        gameControlImpl.fireEvent(new PhaseEvent(PhaseEvent.COMPLETE_PHASE));
    }
}