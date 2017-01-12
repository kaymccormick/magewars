package us.heptet.magewars.service.events;

import java.io.Serializable;

/* Created by kay on 3/15/14. */
/**
 * Service Read Event
 */
public class ReadEvent implements Serializable {
    protected boolean entityFound = true;

    public ReadEvent() {
        /* nothing */
    }

    public boolean isEntityFound() {
        return entityFound;
    }
}
