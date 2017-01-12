package us.heptet.magewars.gameservice.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.games.CreateGameEvent;
import us.heptet.magewars.gameservice.core.events.games.GameCreatedEvent;
import us.heptet.magewars.gameservice.core.events.games.GameDetailsEvent;
import us.heptet.magewars.gameservice.core.events.games.RequestGameDetailsEvent;
import us.heptet.magewars.gameservice.core.events.games.StartGameEvent;
import us.heptet.magewars.gameservice.persistence.services.GamePersistenceService;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.gameservice.core.events.games.JoinGameEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import org.jmock.Mockery;

/*
This class is a mystery!!
 */
@ContextConfiguration(classes = {StandaloneGameServiceConfig.class})
public class GameEventHandlerIntegrationTest extends AbstractTestNGSpringContextTests {
    private static Logger logger = LoggerFactory.getLogger(GameEventHandlerIntegrationTest.class);

    @Autowired
    CombinedGameService gameService;
    private Mockery context;

    @BeforeMethod
    public void setUp() throws Exception {
        context = new Mockery();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestAllGames() throws Exception {
        AllGamesEvent allGamesEvent = gameService.requestAllGames(new RequestAllGamesEvent());
        for(GameDetails gameDetails:allGamesEvent.getGamesDetails())
        {
            assert gameDetails != null;
            assert gameDetails.getCreatedByUsername() != null;
            assert !gameDetails.getCreatedByUsername().isEmpty();
            logger.info("{}", gameDetails);
        }
    }

    @Test
    public void testCreateGame() throws Exception {
        GameCreatedEvent r = gameService.createGame(new CreateGameEvent(new GameDetails("test game", "testuser1", 2, 2)));
        assert r.isGameCreated();
        logger.info("game id = " + r.getGameId());
        GameDetailsEvent gameDetailsEvent = gameService.requestGameDetails(new RequestGameDetailsEvent(r.getGameId()));
        assert gameDetailsEvent != null;
        GameDetails gameDetails = gameDetailsEvent.getGameDetails();
        assert gameDetails != null;
        String gameName = gameDetails.getGameName();
        assert gameName != null;
        assert gameName.equals("test game");
        String createdByUsername = gameDetails.getCreatedByUsername();
        assert createdByUsername != null;
        assert createdByUsername.equals("testuser1");

    }

    @Test(groups = {"unimp"})
    public void testRequestGameDetails() throws Exception {

    }

    @Test
    public void testRequestGameExtendedDetails() throws Exception {
        GameExtendedDetailsEvent event =
            gameService.requestGameExtendedDetails(new RequestGameExtendedDetailsEvent(798));
        logger.info("zzl {}", event.getGameExtendedDetails());
    }

    @Test
    public void testJoinGame() throws Exception {
        int gameId = 0;
        GameCreatedEvent createdEvent = gameService.createGame(new CreateGameEvent(new GameDetails("my game!", "testuser1", 2, 2)));
        gameId = createdEvent.getGameId();
        //gameId = gameService.requestAllGames(new RequestAllGamesEvent()).getGamesDetails().get(0).getGameId();
        gameService.joinGame(new JoinGameEvent("testuser1", gameId));
    }

    @Test(groups = {"broken"})
    public void testStartGame() throws Exception {
        final GamePersistenceService gamePersistenceService = context.mock(GamePersistenceService.class);

        GameEventHandler gameEventHandler = applicationContext.getBean(GameEventHandler.class);
        gameEventHandler.startGame(new StartGameEvent(0));
    }
}