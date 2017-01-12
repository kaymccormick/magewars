package us.heptet.magewars.game.events;

import java.io.Serializable;

/* Created by kay on 6/19/2014. */
/**
 * Interface with a method for accepting events.
 */
@FunctionalInterface
public interface AcceptsEvent extends Serializable {
    /**
     * Accept an event
     * @param event Event
     * @param <T> Class of event
     */
    <T extends BaseEvent> void acceptEvent(T event);
}
