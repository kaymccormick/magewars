package us.heptet.magewars.test.game;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.EventHandler;


/**
 * Event Handler Action
 * @param <T> event class
 */
public class EventHandlerAction<T extends BaseEvent> implements Action {

    private EventHandler<T> eventHandler;

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Invocation invocation) throws Throwable {
        eventHandler = (EventHandler<T>) invocation.getParameter(1);
        return null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("requests an event handler");
    }

    /**
     * Yield (generate) an event.
     * @param event The event to pass to the captured handler.
     */
    public void yieldingEvent(T event)
    {
        eventHandler.handle(event);
    }
}
