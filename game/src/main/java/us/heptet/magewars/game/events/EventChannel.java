package us.heptet.magewars.game.events;

import java.io.Serializable;

/* Created by kay on 6/19/2014. */
/**
 * A channel through which events may be sent and received.
 */
public interface EventChannel extends AcceptsEvent, Serializable {
    /***
     * Open the event channel.
     */
    void openEventChannel();

    /**
     * Add an "Event acceptor" to the channel
     * @param acceptsEvent     Accepts event interface
     */
    void addEventAcceptor(AcceptsEvent acceptsEvent);

    /**
     * Subscribe to a particular sub-channel
     * @param subscription subscroption
     */
    void subscribe(EventSubscription subscription);

    /**
     * Reset the event channel.
     */
    void reset();
}
