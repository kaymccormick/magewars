package us.heptet.magewars.gameservice.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.gameservice.messaging.EventController;
import us.heptet.magewars.gameservice.store.GameStore;

/**
 * Created by jade on 12/08/2016.
 */
@EnableWebSocketMessageBroker
@Import({WebSocketSecurityConfig.class})
public class WebSocketMessagingConfig extends AbstractWebSocketMessageBrokerConfigurer implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    EventController eventController()
    {
        return new EventController(applicationContext.getBean(CombinedGameService.class), applicationContext.getBean(SimpMessagingTemplate.class), applicationContext.getBean(GameStore.class));
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/msg");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/topic");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }
}
