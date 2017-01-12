package us.heptet.magewars.gameservice.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.gameservice.core.events.games.CreateGameEvent;
import us.heptet.magewars.gameservice.core.events.games.GameCreatedEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;


//@ContextConfiguration(classes = us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig.class)
@ContextConfiguration(classes = {StandaloneGameServiceConfig.class})
public class GameServiceIntegrationTest extends AbstractTestNGSpringContextTests {

    private static Logger logger = LoggerFactory.getLogger(GameServiceIntegrationTest.class);

    @Autowired
    CombinedGameService gameService;

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestAllGames() throws Exception {
        AllGamesEvent allGamesEvent = gameService.requestAllGames(new RequestAllGamesEvent());
        for(GameDetails gameDetails:allGamesEvent.getGamesDetails())
        {
            logger.info("Game name: " + gameDetails.getGameName());
            logger.info("Max players: " + gameDetails.getMaxPlayers());
        }
        logger.info(allGamesEvent.toString());
    }

    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreateGame() throws Exception {

        userRepository.deleteAll();
        User user = new User();
        user.setUserName("testuser1");
        userRepository.save(user);

        GameCreatedEvent gameCreatedEvent = gameService.createGame(
                new CreateGameEvent(new GameDetails("Game created by testCreateGame", "testuser1", 2, 2)));

    }

    @Test(groups = {"unimp"})
    public void testRequestGameDetails() throws Exception {

    }
}