package us.heptet.magewars.gameserver.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;

/* Created by kay on 6/16/2014. */
/**
 * Spring web application initializer for the "game server."
 */
public class GameServerApplicationInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static Logger myLogger = LoggerFactory.getLogger(GameServerApplicationInitializer.class.getName());
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { GameServerConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { };
    }

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);

        myLogger.warn("registering dispatcher servlet.");
        super.registerDispatcherServlet(servletContext);
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}

