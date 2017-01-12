package us.heptet.magewars.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.layout.TTLLLayout;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.spi.ContextAwareBase;
import org.slf4j.bridge.SLF4JBridgeHandler;


/**
 * Created by jade on 08/07/2016.
 */
public class LogbackConfigurator extends ContextAwareBase implements Configurator {
    @Override
    public void configure(LoggerContext loggerContext) {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setName("console");

        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<>();
        encoder.setContext(loggerContext);

        TTLLLayout layout = new TTLLLayout();
        layout.setContext(loggerContext);
        layout.start();
        encoder.setLayout(layout);
        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(consoleAppender);
        rootLogger.setLevel(Level.TRACE);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        Logger springLogger = loggerContext.getLogger("org.springframework");
        springLogger.setLevel(Level.WARN);


    }

}
