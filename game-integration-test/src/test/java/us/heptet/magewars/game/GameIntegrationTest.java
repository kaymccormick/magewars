package us.heptet.magewars.game;
/*
 * INTEGRATION TEST *                         * INTEGRATION TEST *                        * INTEGRATION TEST *
 *
 * INTEGRATION TEST *                         * INTEGRATION TEST *                        * INTEGRATION TEST *
 *
 * INTEGRATION TEST *                         * INTEGRATION TEST *                        * INTEGRATION TEST  *
 *
 */
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.game.exceptions.GameInitializationException;
import us.heptet.magewars.domain.game.exceptions.GameStartNotReadyException;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.game.action.CastAction;
import us.heptet.magewars.game.action.QuickcastUiAction;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.phase.PhaseProperty;
import us.heptet.magewars.game.phase.ReadyPhaseType;
import us.heptet.magewars.game.state.GameStateChangeProcessor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

/* Created by kay on 3/26/14. */
/**
 *
 *
 * This is actually an integration test.
 */
@ContextConfiguration(
        //classes = {GameConfig.class}
        locations = {"classpath:gameconfig.xml"}
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameIntegrationTest extends AbstractTestNGSpringContextTests {
        //implements ApplicationContextAware {

    {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
    static Logger logger = Logger.getLogger(GameIntegrationTest.class.getName());
    private ApplicationContext ctx;
    private Mockery mockery;
    private GameStateChangeProcessor gameStateChangeProcessor;
    private EventManager eventManager;

    static {
        logger.setLevel(Level.FINEST);
    }

    @BeforeMethod
    public void setUp() throws Exception {

        mockery = new Mockery();
        //gameStateChangeProcessor = mockery.mock(GameStateChangeProcessor.class);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        eventManager = mockery.mock(EventManager.class);
        mockery.checking(new Expectations(){{
            //ignoring(gameStateChangeProcessor);
            ignoring(eventManager);
        }});

        java.io.StringWriter s = new StringWriter();

        PrintWriter p = new PrintWriter(s);
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        PrintStream p2 = new PrintStream(bo);

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.setPrintStream(p2);
        StatusPrinter.print(loggerContext);

        logger.fine("setUp complete");



    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    /* This doesn't involve game at all */
    @Test
    public void testCreatePlayerMageArenaCreature() throws Exception {
        Player player = us.heptet.magewars.domain.game.GameElementFactory.createPlayer(0);

        Mage beastMaster = new BeastMaster();
        player.setMagePlayerCard(us.heptet.magewars.domain.game.GameElementFactory.createPlayerCard(player, beastMaster));

        /* This is the method we are testing */
        ArenaCreature arenaCreature = GameElementFactory.createPlayerMageArenaCreature(player);
        assert(arenaCreature != null);

        logger.fine(arenaCreature.toString());
        mockery.assertIsSatisfied();
    }

    @Test(expectedExceptions = {GameStartNotReadyException.class, GameInitializationException.class})
    public void testStartGameWithoutMages() throws Exception {
        final GameControl gameControl = setupGame(false);
        final GameSituation gameSituation = gameControl.getGameSituation();
        final GameSituation game = gameSituation;

        mockery.assertIsSatisfied();

        gameSituation.getGameControl().startGame(gameControl.getGameSetup());

        Assert.fail("startGame returned normally");
    }

    @SuppressWarnings("unchecked")
    // we can't fix this until we know how to pull spells back out that we've planned - refactor
    @Test
    public void testEnchantment() throws Exception {
        final GameControl gameControl = setupGame(true);
        final GameSituation gameSituation = gameControl.getGameSituation();
        final GameSituation game = gameSituation;

        game.getGameControl().startGame(gameControl.getGameSetup());

        Assert.assertTrue(game.getArena().getAllObjects().size() > 0);
        //playerrefactor
//        Assert.assertNotNull(game.getPlayer(0).getMage().getPlayer());


        // was actingplayerindex but there is none yet
        int playerIndex = 0;
        Player player = game.getPlayer(playerIndex);
        ArenaCreature mageAC = player.getMageArenaCreature();
        Assert.assertNotNull(mageAC);

        PhaseInterface phase0 = game.getGameControl().getCurrentPhase();
        assertEquals(ReadyPhaseType.INITIATIVE, phase0.getPhaseType());

        PhaseInterface phase1 = game.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);

        assertEquals(ReadyPhaseType.RESET, phase1.getPhaseType());
        PhaseInterface phase2 = game.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        assertEquals(ReadyPhaseType.CHANNEL, phase2.getPhaseType());
        PhaseInterface phase3 = game.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        assertEquals(ReadyPhaseType.UPKEEP, phase3.getPhaseType());
        PhaseInterface phase4 = game.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        assertEquals(ReadyPhaseType.PLANNING, phase4.getPhaseType());

        for(int i = 0; i < game.getNumPlayers(); i++)
        {
            Player player1 = game.getPlayer(i);
            List<us.heptet.magewars.domain.game.PlayerCard> cards = player1.getSpellBook().getCards();
            logger.info("looking at deck for player " + i + "[" + cards.size() + " cards]");

            PlayerCard spell1 = null;
            PlayerCard spell2 = null;

            for(int j = 0; j < cards.size(); j++)
            {
                assert cards.get(j) != null;
                assert cards.get(j).getGameElementType() != null;

                logger.info(cards.get(j).getGameElementType().toString());
                if(cards.get(j).getGameElementType() == GameElementType.ENCHANTMENT)
                {

                    PlayerCard firstSpell = cards.get(j);
                    spell1 = firstSpell;
                    if(j == cards.size() - 1)
                    {
                        PlayerCard secondSpell = cards.get(j - 1);
                        spell2 = secondSpell;
                    }
                    else
                    {
                        PlayerCard secondSpell = cards.get(j + 1);
                        spell2 = secondSpell;
                    }
                    break;
                }
            }

            phase4.setProperty(player1, PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_1, spell1);
            phase4.setProperty(player1, PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_2, spell2);

            logger.fine("Player " + Integer.toString(i) + "  First Planned Spell = " + spell1);
            logger.fine("Player " + Integer.toString(i) + " Second Planned Spell = " + spell2);

        }

        // execute planning phase
        game.getGameControl().nextPhase();

        for(int i = 0; i < game.getNumPlayers(); i++)
        {
            Player player1 = game.getPlayer(i);

            // we should ensure that the spell is removed from the spellbook
            assertFalse("spell1 should not be in spell book", player1.getSpellBook().getCards().contains(phase4.getProperties()[i + 1].get(PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_1)));
            assertFalse("spell2 should not be in spell book", player1.getSpellBook().getCards().contains(phase4.getProperties()[i + 1].get(PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_2)));
        }

        game.getGameControl().nextPhase();

        //game.getCurrentStage().nextPhase(gameStateChangeProcessor)();
        //game.getCurrentStage().nextPhase(gameStateChangeProcessor)();
        game.getGameControl().getCurrentRound().nextStage(gameStateChangeProcessor);

        //** failing here
        //** this is actually set after the reset phase!!
        Assert.assertTrue(mageAC.quickcastAvailable(), mageAC.toString());

        //playerrefactor
        //Assert.assertTrue(player.getPlayerGameState().getFirstSpellEnabled());

        CastAction action = new CastAction(player, mageAC);

        action.setCard((Spell)phase4.getProperties()[game.getActingPlayerIndex() + 1].get(PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_1));
        // need new way
        //action.setCard(game.getPlayerGameState().getPlannedSpells().getFirstSpell());

        // fix me should not be null
        AcquiredActionTarget acquiredActionTarget = new AcquiredActionTarget(action.getCard().getActionTarget(), mageAC);
        Assert.assertNotNull(acquiredActionTarget.getTarget());
        AcquiredActionTargets targets = new AcquiredActionTargets(acquiredActionTarget);
        action.setAcquiredActionTargets(targets);

        PhaseInterface currentPhase = game.getGameControl().getCurrentPhase();

        assert currentPhase != null;

        logger.info("Phase is " + currentPhase.getName());
        currentPhase.consumeAction(action);
        logger.info("Enchantments: " + mageAC.getEnchantments().toString());


        mockery.assertIsSatisfied();
        //game.getCurrentStage().nextPhase(gameStateChangeProcessor)();

    }


    public GameControl setupGame(boolean selectMages)
    {
        logger.info("entering setupGame");
        GameControl gameControl = applicationContext.getBean(GameControl.class);
        if(selectMages) {
            gameControl.getGameSetup().useDefaults();
        }
        gameStateChangeProcessor = gameControl.getGameStateChangeProcessor();

        return gameControl;
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"broken"})
    // we can't fix this until we know how to pull spells back out that we've planned - refactor
    public void testEnchantmentWithUiAction() throws Exception {
        final GameControl gameControl = setupGame(true);
        final GameSituation gameSituation = gameControl.getGameSituation();
        final GameSituation game = gameSituation;

        gameSituation.getGameControl().startGame(gameControl.getGameSetup());

        //playerrefactor
        //Assert.assertNotNull(gameSituation.getPlayer(0).getMage().getPlayer());

        // initiate round is done for us- it probably shouldn't be
        //       game.initiateRound();
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        PhaseInterface phase4 = gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);

        for(int i = 0; i < game.getNumPlayers(); i++)
        {
            Player player1 = game.getPlayer(i);
            List<us.heptet.magewars.domain.game.PlayerCard> cards = player1.getSpellBook().getCards();
            logger.info("looking at deck for player " + i + "[" + cards.size() + " cards]");

            PlayerCard spell1 = null;
            PlayerCard spell2 = null;


            for(int j = 0; j < cards.size(); j++)
            {
                assert cards.get(j) != null;
                assert cards.get(j).getGameElementType() != null;

                logger.info(cards.get(j).getGameElementType().toString());
                if(cards.get(j).getGameElementType() == GameElementType.ENCHANTMENT)
                {

                    PlayerCard firstSpell = cards.get(j);
                    spell1 = firstSpell;
                    if(j == cards.size() - 1)
                    {
                        PlayerCard secondSpell = cards.get(j - 1);
                        spell2 = secondSpell;
                    }
                    else
                    {
                        PlayerCard secondSpell = cards.get(j + 1);
                        spell2 = secondSpell;
                    }
                    break;
                }
            }

            phase4.setProperty(player1, PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_1, spell1);
            phase4.setProperty(player1, PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_2, spell2);

            logger.fine("Player " + Integer.toString(i) + "  First Planned Spell = " + spell1);
            logger.fine("Player " + Integer.toString(i) + " Second Planned Spell = " + spell2);
        }
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentRound().nextStage(gameStateChangeProcessor);

        /* Get the acting player */
        Player player = gameSituation.getActingPlayer();

        ArenaCreature mageAC = player.getMageArenaCreature();
        Assert.assertNotNull(mageAC);
        //Assert.assertNotNull(mageAC.getDomainObject());
        Assert.assertTrue(mageAC.quickcastAvailable());
        //playerrefactor
        //        Assert.assertTrue(player.getPlayerGameState().getFirstSpellEnabled());

        EventTarget eventTarget = new EventTarget() {
            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain eventDispatchChain) {
                return eventDispatchChain;
            }
        };
        QuickcastUiAction quickcastUiAction = new QuickcastUiAction(gameSituation.getGameControl().getCurrentPhase(), player, mageAC,
                //gameSituation.getCurrentPhase()::consumeAction);
                new ActionConsumer<CastAction>() {
                    @Override
                    public void accept(CastAction action) {
                        gameSituation.getGameControl().getCurrentPhase().consumeAction(action);
                    }
                });


        // need new way
        //quickcastUiAction.setSpell(actingPlayerGameState.getPlannedSpells().getFirstSpell());
        quickcastUiAction.initiateAction();
        //actingPlayerGameState.setCurrentAction(quickcastUiAction);

        Assert.assertTrue(quickcastUiAction.getSelectionState().isSelectable(mageAC));
        quickcastUiAction.getSelectionState().acquireSelectionTarget(mageAC);

        logger.info("Enchantments: " + mageAC.getEnchantments().toString());

        //game.getCurrentStage().nextPhase(gameStateChangeProcessor)();
        mockery.assertIsSatisfied();
    }

    // FIXME it's unclear whether this test is correct
    @Test//(expectedExceptions = PhaseNotReadyToComplete.class)
    public void testPlanningPhaseNoPlannedSpells() throws Exception {
        final GameControl gameControl = setupGame(true);
        final GameSituation gameSituation = gameControl.getGameSituation();
        final GameSituation game = gameSituation;

        assert gameStateChangeProcessor != null;

        gameSituation.getGameControl().startGame(gameControl.getGameSetup());
        // initiate round is done for us- it probably shouldn't be
        //       game.initiateRound();
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        gameSituation.getGameControl().getCurrentStage().nextPhase(gameStateChangeProcessor);
        mockery.assertIsSatisfied();
//        Assert.fail(.nextPhase(gameStateChangeProcessor) returned normally"); this is normal now
    }

    @Test//(expectedExceptions = )
    public void testStartGame() throws Exception {
        final GameControl gameControl = setupGame(true);
        final GameSituation gameSituation = gameControl.getGameSituation();
        final GameSituation game = gameSituation;

        //Assert.assertNotNull(game.getAvailableMages(), "Available mages should not be null");
        //Assert.assertTrue(game.getAvailableMages().size() != 0, "No available mags");

        game.getGameControl().startGame(gameControl.getGameSetup());
        mockery.assertIsSatisfied();

    }


/*    @Test
    public void testWaSync() throws Exception {
        AtmosphereClient client = ClientFactory.getDefault().newClient(AtmosphereClient.class);
        Function<BaseEvent> function = new Function<BaseEvent>() {
            @Override
            public void on(BaseEvent baseEvent) {
                logger.info(baseEvent.toString());
            }
        };
        RequestBuilder requestBuilder = client.newRequestBuilder().method(Request.METHOD.GET).
                //uri("http://localhost:8080/atmosphere/wasync").
                        uri("http://localhost:8080/chat").
                transport(Request.TRANSPORT.WEBSOCKET).transport(Request.TRANSPORT.STREAMING).trackMessageLength(true);
        Socket socket = client.create();
        socket.on(Event.MESSAGE, new Function<String>() {
            @Override
            public void on(String o) {
                logger.info("message = " + o.toString());
            }
        });
        socket.open(requestBuilder.build());
        Future f= socket.fire("hello p00p");
        f.finishOrThrowException();
        Thread.sleep(10000);
        socket.close();
    }
*/
    /*    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }*/
}
