package us.heptet.magewars.ui;

import javafx.event.*;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Created by kay on 6/17/2014. */
/**
 * A filter for displaying debug information about javafx events.
 * @param <T> The type of event.
 */
public class DebugEventFilter<T extends Event> implements javafx.event.EventHandler<T> {
    private static Logger logger = LoggerFactory.getLogger(DebugEventFilter.class);
    @Override
    public void handle(T event) {

        if(event.getEventType() == MouseEvent.MOUSE_MOVED||
                event.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET ||
                event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
            return;
        }

        logger.debug("[{} {}] Filtered {}",
                event.getEventType().getName(), event.getEventType().getClass().getName(), event);
    }
}
