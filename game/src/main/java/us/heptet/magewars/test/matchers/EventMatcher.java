package us.heptet.magewars.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.EventType;

/* * Created by jade on 08/08/2016. */

/**
 * An hamcrest event matcher
 * @param <T> The type of event class
 */
public class EventMatcher<T extends BaseEvent> extends TypeSafeMatcher<T> {
    private EventType<T> eventType;

    EventMatcher(EventType<T> eventType) {
        super();
        this.eventType = eventType;
    }


    /**
     * Create a matcher that matches a particular event type.
     * @param eventType Event Type
     * @param <Y> Class of event instance
     * @return Hamcrest matcher to match events of the given event type.
     */
    public static <Y extends BaseEvent> EventMatcher<Y> matchesEventType(EventType<Y> eventType)
    {
        return new EventMatcher<>(eventType);
    }

    @Override
    protected boolean matchesSafely(T item) {
        return item.getEventType() == eventType;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("matches " + eventType + " events");
    }
}
