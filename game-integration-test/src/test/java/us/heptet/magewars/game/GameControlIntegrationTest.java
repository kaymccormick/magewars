package us.heptet.magewars.game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.exceptions.GameNotInProgressException;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.domain.game.setup.GameSetup;

import static org.testng.Assert.*;

/**
 * Created by jade on 21/08/2016.
 */


@ContextConfiguration(locations = {"classpath:gameconfig.xml"})
public class GameControlIntegrationTest extends AbstractTestNGSpringContextTests {

    private static Logger logger = LoggerFactory.getLogger(GameControlIntegrationTest.class);
    private GameControl gameControl;
    private GameControlImpl gameControlImpl;
    private GameSituation gameSituation;
    private GameSetup gameSetup;
    private EventManager eventManager;

    @BeforeMethod
    public void setUp() throws Exception {
        gameControl = applicationContext.getBean(GameControl.class);
        gameControlImpl = (GameControlImpl)gameControl;
        assertNotNull(gameControl, "GameControl instance should not be null");
        gameSituation = null;
        gameSetup = null;
        if(gameControl != null) {
            gameSituation = gameControl.getGameSituation();
            gameSetup = gameControl.getGameSetup();
            eventManager = gameControl.getEventManager();
        }
    }

    @Test
    public void testGameControlExistence() throws Exception {

    }

    @Test
    public void testGameStateChangeProcessir() throws Exception {
        logger.debug("gameStateChangeProcessor = {}", gameControl.getGameStateChangeProcessor());
        assertNotNull(gameControl.getGameStateChangeProcessor(), "GameStateChangeProcessor should not be null");
    }

    @Test(expectedExceptions = {GameNotInProgressException.class})
    public void testInitiateRoundGameNotStarted() throws Exception {
        PhaseInterface phase = gameControl.initiateRound();

    }
}
