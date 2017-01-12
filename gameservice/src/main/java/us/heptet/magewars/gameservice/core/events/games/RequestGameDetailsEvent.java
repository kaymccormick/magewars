package us.heptet.magewars.gameservice.core.events.games;

import us.heptet.magewars.service.events.RequestReadEvent;

/* Created by kay on 3/17/14. */
/**
 * Structure for requesting game details.
 */
public class RequestGameDetailsEvent extends RequestReadEvent {
    private int key;

    public RequestGameDetailsEvent() {
        /* no op */
    }

    /**
     * Create the structure with the specified key.
     * @param uuid
     */
    public RequestGameDetailsEvent(int uuid) {
        this.key = uuid;
    }

    public int getKey() {
        return key;
    }
}
