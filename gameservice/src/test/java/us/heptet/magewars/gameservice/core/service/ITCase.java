package us.heptet.magewars.gameservice.core.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;

import javax.inject.Inject;

/**
 * Created by jade on 27/07/2016.
 */
@ContextConfiguration(classes = {StandaloneGameServiceConfig.class})
public class ITCase extends AbstractTestNGSpringContextTests {
/*
    @ImportResource({"classpath:gameconfig.xml", "classpath:persistenceconfig.xml"})
    //FIXME need to sort out bean configuration for this other integration tests, and module at large
    static class Config
    {
        @Bean
        GameEventHandler gameEventHandler()
        {
            return new GameEventHandler();
        }

        @Bean
        GameStore gameStore()
        {
            return new GameStore();
        }

    }
*/

    @Inject
    CombinedGameService gameEventHandler;

    @Test
    public void testGameEventHandler() throws Exception {

    }
}
