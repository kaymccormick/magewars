package us.heptet.magewars.game.events;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 6/19/2014. */
/**
 *
 *
 * Our "Event Manager" implementation - should look at retrofitting with event bus.
 */
public class EventManagerImpl implements EventManager {
    private final transient Map<EventType<? extends BaseEvent>,
                List<EventHandler>> eventHandlerMap = new HashMap<>();
    private static transient Logger logger = Logger.getLogger(EventManagerImpl.class.getName());
    private transient Queue<BaseEvent> eventQueue = new LinkedList<>();
    private Set<EventChannel> eventChannels = new HashSet<>();

    static {
        logger.setLevel(Level.ALL);
    }

    private boolean cacheUnhandledEvents = false;

    /***
     * Construct an EventManagerImpl.
     */
    public EventManagerImpl() {
        logger.finest("Constructing EventManagerImpl.");
    }

    @Override
    public <T extends BaseEvent> void fireEvent(T event) {
        logger.fine(this + ".fireEvent " + event.toString());
        acceptEvent(event);
    }

    @Override
    public void setEventChannels(Set<EventChannel> eventChannels) {
        this.eventChannels = eventChannels;
    }

    /***
     * Connect to the event channels.
     */
    public void connectChannels() {
        for(EventChannel eventChannel:eventChannels)
        {
            eventChannel.openEventChannel();
            eventChannel.addEventAcceptor(this);

        }
    }

    @Override
    public void addAndConnectChannels(Collection<EventChannel> eventChannelSet) {
        assert eventChannelSet != null;
        Iterator<EventChannel> i = eventChannelSet.iterator();
        logger.finest(this + ": in addAndConnectChannels (" + this.hashCode() + ")" );
        while(i.hasNext())
        {
            EventChannel e = i.next();
            // check for null, may be injected
            if(e != null) {
                e.openEventChannel();
                eventChannels.add(e);
                e.addEventAcceptor(this);
            }
        }
    }

    @Override
    public Object subscribe(EventSubscription subscription) {
        Object result = null;
        for (EventChannel eventChannel : eventChannels) {
            eventChannel.subscribe(subscription);
        }
        return result;

    }

    @Override
    public void setCacheUnhandledEvents(boolean b) {

        cacheUnhandledEvents = b;
    }

    @Override
    public void resetEventChannels() {
        for(EventChannel eventChannel:eventChannels)
        {
            eventChannel.reset();
        }

    }

    @Override
    public void addEventChannel(EventChannel eventChannel) {
        eventChannels.add(eventChannel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEvent> void acceptEvent(T event) {
        if(event.getEventType() == null)
        {
            logger.warning("event type of " + event.getClass().getName() + " is null");
        } else {
            logger.finest(this + ": Accepting event " + event);
        }
        if(event.getEventType().isForwarding() && !event.isGameInternal()) {
            logger.finest(this + ": forwarding event to event channels");
            if(eventChannels.isEmpty())
            {
                logger.warning(this + ": No event channels available. (" + this.hashCode() + ")");
            }
            for (EventChannel eventChannel : eventChannels) {
                if (eventChannel != event.getSourceChannel()) {
                    logger.finest(this + ": forwarding event to channel " + eventChannel);
                    eventChannel.acceptEvent(event);
                }
            }
        }
        List<EventHandler> list;
        logger.finest(this + ": looking for event handlers " + event.getEventType() + "[" + Integer.toHexString(event.getEventType().hashCode()) + "]");
        for(EventType eventType:eventHandlerMap.keySet())
        {
            logger.finest(this + ": key = " + eventType.toString() + "[" + Integer.toHexString(eventType.hashCode()) + "]");
        }
        if(eventHandlerMap.containsKey(event.getEventType()))
        {
            logger.finest(this + ": found event handlers " + event.getEventType());
            list = eventHandlerMap.get(event.getEventType());
            for(EventHandler handler:list)
            {
                logger.fine(this + ": calling into event handler");
                handler.handle(event);
            }
        }
        else if(cacheUnhandledEvents)
        {
            logger.fine("no handler available, caching event");
            eventQueue.add(event);
        }
    }

    @Override
    public <T extends BaseEvent> void addEventHandler(EventType<T> eventType, EventHandler<? super T> handler)
    {
        logger.finest(this + ": Adding event handler for " + eventType);
        if(!eventHandlerMap.containsKey(eventType))
        {
            logger.finest(this + ": does not contain key, populating with empty list");
            eventHandlerMap.put(eventType, new ArrayList<EventHandler>());
        }
        eventHandlerMap.get(eventType).add(handler);
        if(!eventQueue.isEmpty())
        {
            // will there be race conditions in this code?
            Queue<BaseEvent> newQueue = new LinkedList<>();
            for(BaseEvent event:eventQueue)
            {
                // need to handle more generalized events
                if(event.getEventType() == eventType)
                {
                    logger.fine("Found cached event of type " + eventType);
                    logger.fine("event = " + event);
                    EventHandler handler1 = handler;
                    handler1.handle(event);
                } else {
                    newQueue.add(event);
                }
            }
            eventQueue = newQueue;
        }
    }

    @Override
    public <T extends BaseEvent> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> handler) {
        logger.finest(this + ": Removing event handler for " + eventType);
        if(!eventHandlerMap.containsKey(eventType))
        {
            logger.finest(this + ": does not contain key. cannot remove an event handler.");
            return;
        }
        eventHandlerMap.get(eventType).remove(handler);
    }
}
