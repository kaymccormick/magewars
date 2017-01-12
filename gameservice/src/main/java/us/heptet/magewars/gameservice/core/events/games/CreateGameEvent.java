package us.heptet.magewars.gameservice.core.events.games;

import us.heptet.magewars.gameservice.core.events.CreateEvent;
import us.heptet.magewars.service.events.GameDetails;

import java.io.Serializable;

/* Created by kay on 3/17/14. */
/**
 * Class representing the the request for a game to be created.
 */
public class CreateGameEvent extends CreateEvent implements Serializable {
    private GameDetails details;

    public CreateGameEvent() {
        /* no op */
    }

    /**
     * Construct an instance with the given details.
     * @param details
     */
    public CreateGameEvent(GameDetails details) {
        this.details = details;
    }

    public GameDetails getDetails() {
        return details;
    }
}
