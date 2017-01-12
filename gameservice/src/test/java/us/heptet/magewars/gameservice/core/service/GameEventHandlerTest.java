package us.heptet.magewars.gameservice.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.jmock.Mockery;
import org.jmock.Expectations;
import us.heptet.magewars.domain.game.AvailableMages;
import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.Mage;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.GameStatus;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.GameRepository;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.persistence.services.GamePersistenceService;
import us.heptet.magewars.gameservice.store.GameStore;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

public class GameEventHandlerTest {
    private static Logger logger = LoggerFactory.getLogger(GameEventHandlerTest.class);
    private final int gameId = 1;
    private final GamePlayerDetails player1 = new GamePlayerDetails(0, "player1", CardEnum.WARLOCK.name());
    private final GamePlayerDetails player2 = new GamePlayerDetails(1, "player2", CardEnum.BEASTMASTER.name());
    private final GamePlayerDetails player3 = new GamePlayerDetails(2, "player3", CardEnum.BEASTMASTER.name());
    Mockery context;
    private final int minPlayers = 2;
    private final int maxPlayers = 2;
    private final StartGameEvent startGameEvent = new StartGameEvent(gameId);
    private final Set<Mage> mageSet = new HashSet<>();
    private final List<GamePlayerDetails> gamePlayerDetails = new ArrayList<>();
    private final RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent = new RequestGameExtendedDetailsEvent(gameId);
    private final GameStartedEvent trueGameStartedEvent = new GameStartedEvent(true);
    private GamePersistenceService gamePersistenceService;
    private AvailableMages availableMages;
    private GameEventHandler gameEventHandler;
    private GameRepository gameRepository;

    @BeforeMethod
    public void setUp() throws Exception {
        GameStore gameStore = new GameStore<>();

        gameEventHandler = new GameEventHandler(gameStore);

        context = new Mockery();

        gameRepository = context.mock(GameRepository.class);
        gameStore.setGameRepository(gameRepository);

        gamePersistenceService = context.mock(GamePersistenceService.class);
        availableMages = context.mock(AvailableMages.class);

        gameEventHandler.setGamePersistenceService(gamePersistenceService);
        gameEventHandler.setAvailableMages(availableMages);

        mageSet.clear();
        mageSet.add(new BeastMaster());
        mageSet.add(new Warlock());

        gamePlayerDetails.clear();
        gamePlayerDetails.add(player1);
        gamePlayerDetails.add(player2);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestAllGames() throws Exception {
        context.checking(new Expectations(){{
            oneOf(gamePersistenceService).requestAllGames(with(any(RequestAllGamesEvent.class)));
            will(returnValue(new AllGamesEvent()));
        }});
        AllGamesEvent allGamesEvent = gameEventHandler.requestAllGames(new RequestAllGamesEvent());
        List<GameDetails> gamesDetails = allGamesEvent.getGamesDetails();
        assertTrue(gamesDetails.isEmpty());
    }

    @Test
    public void testCreateGame() throws Exception {
        GameDetails gameDetails = new GameDetails("game", "user1", 2, 2);
        context.checking(new Expectations()
        {{
            oneOf(gamePersistenceService).createGame(with(any(CreateGameEvent.class)));
            will(returnValue(new GameCreatedEvent(false)));
        }});
        GameCreatedEvent game = gameEventHandler.createGame(new CreateGameEvent(gameDetails));
        assertFalse(game.isGameCreated());
    }

    @Test(groups = {"unimp"})
    public void testRequestGameDetails() throws Exception {

    }

    @Test(groups = {"unimp"})
    public void testRequestGameExtendedDetails() throws Exception {

    }

    @Test(groups = {"unimp"})
    public void testJoinGame() throws Exception {

    }

    @BeforeMethod(groups = {"startGame"})
    public void beforeStartGame()
    {
        logger.info("before start game");
    }
    //@Mock
    //GamePersistenceService gamePersistenceService;
    @Test(groups = {"functest", "startGame", "broken"})
    public void testStartGame() throws Exception {
        final GameExtendedDetails gameExtendedDetails = new GameExtendedDetails(gameId, "test game", "testuser1",
                minPlayers, maxPlayers, gamePlayerDetails,
                new ArrayList<String>(), new ArrayList<SpellbookDetails>());

        context.checking(new Expectations() {{
            oneOf(gamePersistenceService).requestGameExtendedDetails(requestGameExtendedDetailsEvent);
            will(returnValue(new GameExtendedDetailsEvent(gameExtendedDetails)));
            oneOf(availableMages).getAvailableMageSet();
            will(returnValue(mageSet));
            oneOf(gamePersistenceService).startGame(startGameEvent);
            will(returnValue(trueGameStartedEvent));

            oneOf(gameRepository).findOne(with(any(Serializable.class)));
            will(returnValue(new Game("test game", 2, 2, new User("user1"), GameStatus.SETUP)));

        }});
        GameStartedEvent gameStartedEvent = gameEventHandler.startGame(startGameEvent);
        logger.debug("got {}", gameStartedEvent);
        context.assertIsSatisfied();
        assert gameStartedEvent.isGameStarted() : "Game not started: " + gameStartedEvent.getMessage();
    }

    @Test(groups = {"functest", "startGame"})
    public void testStartGameWithZeroPlayers() throws Exception {
        final GameExtendedDetails gameExtendedDetails = new GameExtendedDetails(gameId, "test game", "testuser1",
                minPlayers, maxPlayers, new ArrayList<GamePlayerDetails>(),
                new ArrayList<String>(), new ArrayList<SpellbookDetails>());

        context.checking(new Expectations() {{
            oneOf(gamePersistenceService).requestGameExtendedDetails(requestGameExtendedDetailsEvent);
            will(returnValue(new GameExtendedDetailsEvent(gameExtendedDetails)));
            oneOf(availableMages).getAvailableMageSet();
            will(returnValue(mageSet));

        }});
        GameStartedEvent gameStartedEvent = gameEventHandler.startGame(startGameEvent);
        logger.debug("got {}", gameStartedEvent);
        context.assertIsSatisfied();
        assert !gameStartedEvent.isGameStarted() : "Game started when it should not have been.";
    }

    @Test(groups = {"functest", "startGame"})
    public void testStartGameWithOnePlayer() throws Exception {
        gamePlayerDetails.clear();
        gamePlayerDetails.add(player1);

        final GameExtendedDetails gameExtendedDetails = new GameExtendedDetails(gameId, "test game", "testuser1",
                minPlayers, maxPlayers, gamePlayerDetails,
                new ArrayList<String>(), new ArrayList<SpellbookDetails>());

        context.checking(new Expectations() {{
            oneOf(gamePersistenceService).requestGameExtendedDetails(requestGameExtendedDetailsEvent);
            will(returnValue(new GameExtendedDetailsEvent(gameExtendedDetails)));
            oneOf(availableMages).getAvailableMageSet();
            will(returnValue(mageSet));

        }});
        GameStartedEvent gameStartedEvent = gameEventHandler.startGame(startGameEvent);
        logger.debug("got {}", gameStartedEvent);
        context.assertIsSatisfied();
        assert !gameStartedEvent.isGameStarted() : "Game started when it should not have been.";
    }

    @Test
    public void testUpdateGamePlayer() throws Exception {

    }

}