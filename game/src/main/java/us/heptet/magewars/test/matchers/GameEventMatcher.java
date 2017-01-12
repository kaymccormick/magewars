package us.heptet.magewars.test.matchers;

import us.heptet.magewars.game.events.EventType;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.test.matchers.EventMatcher;

/* Created by jade on 17/08/2016. */

/**
 * Match game events - subclass of EventMatcher, though we don't really need it.
 * @param <T> Type of game event.
 */
public class GameEventMatcher<T extends GameEvent> extends EventMatcher<T> {
    /**
     * construct an instance
     * @param eventType Event type
     */
    public GameEventMatcher(EventType<T> eventType) {
        super(eventType);
    }
}
