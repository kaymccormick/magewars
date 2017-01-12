package us.heptet.magewars.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/***
 * Source: http://stackoverflow.com/questions/10734025/logback-two-appenders-multiple-loggers-different-levels
 * Author: http://stackoverflow.com/users/24108/john-oxley
 */
public class ThresholdLoggerFilter extends Filter<ILoggingEvent> {
    private Level level;
    private String logger;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }

        if (!event.getLoggerName().startsWith(logger))
            return FilterReply.NEUTRAL;

        if (event.getLevel().isGreaterOrEqual(level)) {
            return FilterReply.NEUTRAL;
        } else {
            return FilterReply.DENY;
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    @Override
    public void start() {
        if (this.level != null && this.logger != null) {
            super.start();
        }
    }
}