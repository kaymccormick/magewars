package us.heptet.magewars.service.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 3/15/14. */
/**
 * Return object for interface method which requests all games information.
 */
public class AllGamesEvent extends ReadEvent implements Serializable {
    private List<GameDetails> gamesDetails = new ArrayList<>();

    /**
     * Construct an AllGamesEvent
     */
    public AllGamesEvent() {
        /* nothing */
    }

    /**
     * Construct an AllGamesEvent with the specified details
     * @param gamesDetails List of GameDetails instances
     */
    public AllGamesEvent(List<GameDetails> gamesDetails) {

        this.gamesDetails = gamesDetails;
    }

    public List<GameDetails> getGamesDetails() { return gamesDetails; }

    @Override
    public String toString() {
        return "AllGamesEvent{" +
                "gamesDetails=" + gamesDetails +
                '}';
    }
}
