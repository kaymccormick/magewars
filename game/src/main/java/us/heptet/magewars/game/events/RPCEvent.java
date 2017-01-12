package us.heptet.magewars.game.events;


import java.io.Serializable;

/* Created by kay on 7/1/2014. */
/**
 * Wrapper class for events.
 */
public class RPCEvent implements Serializable /*<T extends BaseEvent>*/ {
    private BaseEvent event;
    private String broadcasterId;
    private String userName;

    public RPCEvent() {
        /* empty */
    }

    /**
     * Create an RPC event wrapper with the given event
     * @param event The event.
     */
    public RPCEvent(BaseEvent event) {
        setEvent(event);
    }

    public BaseEvent getEvent() {
        return event;
    }

    public void setEvent(BaseEvent event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "RPCEvent[" + broadcasterId + "]{" + event  + "}";
    }

    public String getBroadcasterId() {
        return broadcasterId;
    }

    public void setBroadcasterId(String broadcasterId) {
        this.broadcasterId = broadcasterId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
