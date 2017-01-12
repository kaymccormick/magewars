package us.heptet.magewars.webapp.client;

import us.heptet.magewars.game.events.RPCEvent;

/**
 * Created by jade on 25/09/2016.
 */
public class EventTest {
    RPCEvent event;

    public EventTest() {
        /* default */
    }

    public RPCEvent getEvent() {
        return event;
    }

    public void setEvent(RPCEvent event) {
        this.event = event;
    }
}
