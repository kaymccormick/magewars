package us.heptet.magewars.rest.client;
/*

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.service.game.GameService;

@ContextConfiguration(locations = "classpath:gameconfig.xml")
public class ITCase extends AbstractTestNGSpringContextTests {
    private static Logger logger = LoggerFactory.getLogger(ITCase.class);
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Autowired
    GameService gameService;

    // this requires a REST service - port is set to 8080, need to investigate
    // TODO INVESTIGATE
    @Test(groups = {"broken"})
    public void testRequestAllGames() throws Exception {
        AllGamesEvent allGamesEvent = gameService.requestAllGames(new RequestAllGamesEvent());
        logger.info("allGamesEvent = {}", allGamesEvent);
        for(GameDetails gameDetails:allGamesEvent.getGamesDetails())
        {
            assert gameDetails != null;
            assert gameDetails.getCreatedByUsername() != null;
            assert !gameDetails.getCreatedByUsername().isEmpty();
        }

    }
}*/
