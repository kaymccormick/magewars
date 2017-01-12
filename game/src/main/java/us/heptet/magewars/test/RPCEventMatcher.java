package us.heptet.magewars.test;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.EventType;
import us.heptet.magewars.game.events.RPCEvent;

/**
 * Matcher to match the event type inside of a wrapped event. The genericity of this metcher makes no sense. FIXME
 * @param <T> Type of rpcevent
 */
public class RPCEventMatcher<T extends RPCEvent> extends TypeSafeMatcher<T> {
    EventType eventType;

    /**
     * Constructor
     * @param eventType
     */
    public RPCEventMatcher(EventType eventType) {
        super();
        this.eventType = eventType;
    }

    /**
     * Construct matcher
     * @param eventType Event type
     * @param <Y> Type
     * @return matcher
     */
    public static <Y extends RPCEvent> RPCEventMatcher<Y> matchesEventType(EventType eventType)
    {
        return new RPCEventMatcher<>(eventType);
    }

    @Override
    protected boolean matchesSafely(T item) {
        BaseEvent event = item.getEvent();
        EventType itemEventType = event.getEventType();
        if(itemEventType == eventType)
        {
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("matches " + eventType + " events");
    }
}
