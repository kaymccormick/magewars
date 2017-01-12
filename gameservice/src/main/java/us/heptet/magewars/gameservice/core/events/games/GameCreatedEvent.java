package us.heptet.magewars.gameservice.core.events.games;

import us.heptet.magewars.service.events.GameDetails;

import java.io.Serializable;

/* Created by kay on 3/17/14. */
/**
 * Class representing the creation of a game.
 */
public class GameCreatedEvent implements Serializable {
    private int gameId;
    private boolean gameCreated;
    private GameDetails gameDetails;

    /**
     * Default constructor
     */
    public GameCreatedEvent() {
        gameDetails = null;
        gameCreated = false;
    }

    /**
     * Construct a game created event with the given flag for whether or not a game
     * was created (Typically this is passed in as false, because there are no
     * details being provided for the game).
     * @param gameCreated
     */
    public GameCreatedEvent(boolean gameCreated) {
        this.gameCreated = gameCreated;
        gameDetails = null;
        assert !this.gameCreated;
    }

    /**
     * Construct an instance with the specified attributes.
     * @param gameId
     * @param gameCreated
     */
    public GameCreatedEvent(int gameId, boolean gameCreated) {
        this.gameId = gameId;
        this.gameCreated = gameCreated;
        gameDetails = null;
    }

    /**
     * Construct an instance with the specified attributes.
     * @param gameId
     * @param gameDetails
     */
    public GameCreatedEvent(int gameId, GameDetails gameDetails) {
        this.gameId = gameId;
        this.gameDetails = gameDetails;
        gameCreated = true;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public boolean isGameCreated() {
        return gameCreated;
    }

    public GameDetails getGameDetails() {
        return gameDetails;
    }
}
