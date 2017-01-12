package us.heptet.magewars.gameservice.core.events.games;

import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.ReadEvent;


/* Created by kay on 3/17/14. */
/**
 * Event representing the response for a request of game details.
 */
public class GameDetailsEvent extends ReadEvent {
    private int key;
    private GameDetails gameDetails;

    public GameDetailsEvent() {
        /* no-op */
    }

    /**
     * Construct with a key
     * @param key
     */
    private GameDetailsEvent(int key)
    {
        this.key = key;
    }

    /**
     * construct with a key and details
     * @param key
     * @param gameDetails
     */
    public GameDetailsEvent(int key, GameDetails gameDetails) {
        this.key = key;
        this.gameDetails = gameDetails;
    }

    public int getKey() {
        return key;
    }

    public GameDetails getGameDetails() {
        return gameDetails;
    }

    /**
     * return a "not found" structure
     * @param key
     * @return
     */
    public static GameDetailsEvent notFound(int key)
    {
        GameDetailsEvent ev = new GameDetailsEvent(key);
        ev.entityFound = false;
        return ev;
    }
}
