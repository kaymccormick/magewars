package us.heptet.magewars.game.events;

/**
 * An interface providing methods for dispatching (firing) and adding event handlers.
 */
public interface EventDispatcher {
    /**
     * Fire an event
     * @param event Event
     * @param <T> Type of event
     */
    <T extends BaseEvent> void fireEvent(T event);

    /**
     * Add an event handler
     * @param eventType Type of event
     * @param handler handler
     * @param <T> Type of event class
     */
    <T extends BaseEvent> void addEventHandler(EventType<T> eventType, EventHandler<? super T> handler);

    /**
     * Remove an event handler
     * @param eventType Type of event
     * @param handler Handler
     * @param <T> Type of event class
     */
    <T extends BaseEvent> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> handler);

}
