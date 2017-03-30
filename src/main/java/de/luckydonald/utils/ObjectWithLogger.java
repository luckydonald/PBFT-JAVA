package de.luckydonald.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a logger ready to use.
 * @author luckydonald
 */
public class ObjectWithLogger {
    private Logger logger = null;
    private static Logger staticLogger = null;
    public Logger getLogger() {
        if (logger == null) {
            logger =  Logger.getLogger(this.getClass().getCanonicalName());
        }
        logger.setLevel(Level.FINE);
        return logger;
    }
    public static Logger getStaticLogger() {
        if (staticLogger == null) {
            staticLogger = Logger.getLogger(new Throwable().getStackTrace()[1].getClassName());
        }
        return staticLogger;
    }

    public Handler addLogConsoleHandler() {
        return addLogConsoleHandler(null);
    }
    public Handler addLogConsoleHandler(Level l) {
        if (l == null) {
            return addLogConsoleHandler(Level.ALL);
        }
        // Create and set handler
        Handler systemOut = new ConsoleHandler();
        systemOut.setLevel( l );
        getLogger().addHandler( systemOut );
        getLogger().setLevel( l );

        // Prevent logs from processed by default Console handler.
        getLogger().setUseParentHandlers( false ); // Solution 1
        return systemOut;
    }
}