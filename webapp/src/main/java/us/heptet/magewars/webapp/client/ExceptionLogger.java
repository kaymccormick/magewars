package us.heptet.magewars.webapp.client;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/24/2014. */
/**
 * Strange "ExceptionLogger" class to handler bizarre exception handling in GWT.
 */
public class ExceptionLogger {
    private static Logger logger = Logger.getLogger("ExceptionLogger");
    private static Logger normalLogger = Logger.getLogger(ExceptionLogger.class.getName());

    static {
        logger.setUseParentHandlers(true);
        logger.setLevel(Level.FINEST);
        normalLogger.setLevel(Level.FINEST);

    }

    /***
     * Log the provided exception.
     * @param throwable The throwable.
     */
    static void logException(Throwable throwable)
    {
        String s = "";

        normalLogger.severe("uncaught exception : " /*+ throwable.toString() + " : "*/ + throwable.getMessage());
        //logger.setUseParentHandlers(false);
        logger.severe("BEGIN UNCAUGHT EXCEPTION CHAIN");
        for(Throwable cause = throwable; cause != null; cause = cause.getCause()) {
            logger.severe("BEGIN EXCEPTION " + cause.toString() + " (" + cause.getMessage() + ")");
            for (int i = 0; i < cause.getStackTrace().length; i++) {
                logger.severe(cause.getStackTrace()[i].toString());
                s = s + cause.getStackTrace()[i].toString() + "<br>\r\n";
            }
            s = cause.toString() + "\r\n" + s;
            logger.severe("END EXCEPTION");
        }
        logger.severe("END UNCAUGHT EXCEPTION CHAIN");
        //logger.log(Level.SEVERE, s + "\r\n" + s2);

    }
}
