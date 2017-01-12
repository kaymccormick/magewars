package us.heptet.magewars.game.events;

import java.util.Collection;
import java.util.Set;

/* Created by kay on 6/19/2014. */
/**
 * An interface extending {@link AcceptsEvent} including methods for using event channels.
 */
public interface EventManager extends AcceptsEvent, EventDispatcher {

    /**
     * Set the event channels
     * @param eventChannels Set of EventChanne instances
     */
    void setEventChannels(Set<EventChannel> eventChannels);

    /**
     * Add event channels and connect to them. Each member of eventChannelSet has its
     * openEventChannel method called, and is added to the EventChannel list maintained
     * by the implementing class. The EventChannel list maintained by the class may already
     * have contents - the contents are not affected in any way.
     * @param eventChannelSet Collection of EventChannel instances.
     */
    void addAndConnectChannels(Collection<EventChannel> eventChannelSet);

    /**
     * Subscribe to a sub-channel or subscription
     * @param subscription Subscroption
     * @return Object representing the subscription, if any (implementation-defined)
     */
    Object subscribe(EventSubscription subscription);

    /**
     * Set a boolean indicating that unhandled events should be cached, and
     * delivered when the event handlers have been registered. Sort of a hack to
     * handle the transition between the game table view and the game view.
     * @param b Boolean, true if unhandled events should be cached.
     */
    void setCacheUnhandledEvents(boolean b);

    /**
     * Reset the event channels
     */
    void resetEventChannels();

    /**
     * Add an event channel to the Event Manager
     * @param eventChannel event channel
     */
    void addEventChannel(EventChannel eventChannel);
}
