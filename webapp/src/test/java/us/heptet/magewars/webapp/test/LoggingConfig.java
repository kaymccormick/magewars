package us.heptet.magewars.webapp.test;

import java.io.IOException;
import java.util.logging.*;

/* Created by jade on 08/07/2016. */
/**
 *
 */
public class LoggingConfig {

    private final LogManager logManager;
    private final Logger rootLogger;

    class LogHandler extends Handler {

        @Override
        public void publish(LogRecord record) {
            System.err.println(record.toString());
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }

    public LoggingConfig() {
        logManager = LogManager.getLogManager();
        rootLogger = logManager.getLogger("");
        rootLogger.addHandler(new LogHandler());

        try {
            rootLogger.addHandler(new FileHandler("my-gwt-log.txt"));
        } catch(IOException x)
        {
            System.err.println(x.toString());
        }
    }
}
