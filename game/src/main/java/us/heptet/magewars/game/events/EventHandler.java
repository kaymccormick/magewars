package us.heptet.magewars.game.events;

/* Created by kay on 6/20/2014. */
/**
 * An interface for handling events.
 * @param <T> Event object type
 */
@FunctionalInterface
public interface EventHandler<T extends BaseEvent> {
    /**
     * Handle an event
     * @param event Event
     */

    void handle(T event);
}
