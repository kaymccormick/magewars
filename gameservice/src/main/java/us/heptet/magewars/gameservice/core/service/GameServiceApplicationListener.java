package us.heptet.magewars.gameservice.core.service;

import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/* Created by kay on 5/6/2014. */
/**
 * this is clearly meant to set up the bridge ... but does it work
 */
@Component
public class GameServiceApplicationListener implements org.springframework.context.ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LoggerFactory.getLogger(GameServiceApplicationListener.class).info("ContextRefreshedEvent: installing root logger for JUL-SLF4J bridge");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
