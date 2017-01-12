package us.heptet.magewars.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.ArenaImpl;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.domain.game.setup.GameSetupImpl;
import us.heptet.magewars.game.state.GameStateChangeProcessor;
import us.heptet.magewars.game.state.GameStateChangeProcessorImpl;
import us.heptet.magewars.test.GameTestHelper;

import static org.testng.Assert.*;

/**
 * Created by jade on 31/07/2016.
 */
public class GameTest {
    private static Logger logger = LoggerFactory.getLogger(GameTest.class);

    private Game game;
    private Mockery mockery;
    private CardSet cardSet;
    private SpellBookManager spellbookManager;
    private EventDispatcher eventDispatcher;
    private Arena arena;
    private EventManager eventManager;
    private GameControl gameControl;
    private GameSetupImpl gameSetup;
    private GameStateChangeProcessor gameStateChangeProcessor;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        cardSet = mockery.mock(CardSet.class);
        spellbookManager = new SpellBookManager(new BaseCardSet());
        eventManager = mockery.mock(EventManager.class);

        eventDispatcher = eventManager;
        arena = new ArenaImpl();
        game = new Game(arena, cardSet);
        gameStateChangeProcessor = new GameStateChangeProcessorImpl(game);
        mockery.checking(new Expectations(){{
//            oneOf(eventManager).addEventHandler(with(equal(GameEvent.COMPLETE_PHASE)), with(any(EventHandler.class)));
            ignoring(eventManager);
        }});
        gameControl = new GameControlImpl(game, eventManager, spellbookManager);
        gameControl.setGameStateChangeProcessor(gameStateChangeProcessor);
        gameSetup = new GameSetupImpl(new BaseCardSet());
        gameSetup.useDefaults();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

/*
    @Test(groups={"unimp"})
    public void testGetAvailableMages() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(cardSet).getMages();
        }});
        List<Mage> mages = game.getAvailableMages();
        assertNotNull(mages, "getAvailableMages should return non-null");
        // gameSituation merely returns what it gets from cardSet
        //assertTrue(mages.size() > 0, "getAvailableMages should return non-empty list");
        mockery.assertIsSatisfied();
    }
*/

    @Test
    public void testGetGameSituation() throws Exception {
        assertEquals(game.getGameSituation(), game);
        mockery.assertIsSatisfied();
    }


    @Test
    public void testStartGame() throws Exception {
        gameControl.startGame(gameSetup);
        mockery.assertIsSatisfied();
    }

    @Test(groups={"unimp"})
    public void testGetCurrentPhase() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testNextPhase() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetGameControl() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetGameControl() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testInitializeMageSpellbook() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetNumPlayers() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetPlayers() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetPlayerInfo() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetPlayerInfo() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetNumPlayers() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetPlayer() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testAddPlayer() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetPlayersInitiativeOrder() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testChangeInitiative() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetInitiativeIndex() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetInitiativeIndex() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetActingPlayerIndex() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetActingPlayerIndex() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testAdvancePlayerControl() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetCardSet() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetArena() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetPlayerGameState() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetPlayerGameState1() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetActingPlayer() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testGetGameSetup() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetGameSetup() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testCreatePlayerSetup() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetArena() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testIsGameInProgress() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetGameInProgress() throws Exception {

    }

    @Test(groups={"broken"})
    public void testCreatePlayerMageGameObjects() throws Exception {
        game.createPlayerMageGameObjects();
    }

    @Test(groups={"unimp"})
    public void testGetPlayerMageArenaCreatureFactory() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSetPlayerMageArenaCreatureFactory() throws Exception {

    }

    @Test
    public void testSerialize() throws Exception {
        GameTestHelper gameTestHelper = new GameTestHelper();
        GameControl gameControl = gameTestHelper.createGameControl();
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(gameControl.getGameSituation());
        logger.info("{}", value);
    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        GameTestHelper gameTestHelper = new GameTestHelper();
        GameControl gameControl = gameTestHelper.createGameControl();
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(gameControl.getGameSituation());
        Game newGame = objectMapper.readValue(value, Game.class);
        logger.info("newGame = {}", newGame);
    }
}