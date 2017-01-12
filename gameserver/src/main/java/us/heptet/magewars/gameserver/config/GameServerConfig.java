package us.heptet.magewars.gameserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import us.heptet.magewars.gameserver.rest.controller.GameCommandsController;
import us.heptet.magewars.gameserver.rest.controller.GameQueriesController;
import us.heptet.magewars.gameserver.rest.controller.UserController;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;
import us.heptet.magewars.gameservice.config.WebSocketMessagingConfig;

/* Created by kay on 6/16/2014. */
/**
 * Game server configuration.
 */
@Configuration
@EnableWebMvc
@Import({StandaloneGameServiceConfig.class, WebSocketMessagingConfig.class})
public class GameServerConfig {
    @Bean
    GameQueriesController gameQueriesController()
    {
        return new GameQueriesController();
    }

    @Bean
    GameCommandsController gameCommandsController() { return new GameCommandsController(); }

    @Bean
    UserController userController() { return new UserController(); }

}
