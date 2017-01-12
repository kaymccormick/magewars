package us.heptet.magewars.webapp.server.config;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;
import us.heptet.magewars.gameservice.config.WebSocketMessagingConfig;

/* Created by kay on 5/11/2014. */
/**
 * Spring configuration class for the primary webapp.
 */
@Configuration
//@ImportResource("localconfig.xml")
@Import({StandaloneGameServiceConfig.class, WebSocketMessagingConfig.class})
@ImportResource({"classpath:localconfig.xml"})
@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = "us.heptet.magewars.testwebapp.signup")
@EnableWebMvc
public class WebAppConfig {
    /**
     * Construct an instance.
     */
    public WebAppConfig() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
