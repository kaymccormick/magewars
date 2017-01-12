package us.heptet.magewars.gameservice.core.events.games;

import java.io.Serializable;

/* Created by kay on 6/2/2014. */
/**
 * Structure for requesting a game start.
 */
public class StartGameEvent implements Serializable {
    int gameId;

    public StartGameEvent() {
        /* no op */
    }

    /**
     * Create a request with the given game id.
     * @param gameId
     */
    public StartGameEvent(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "StartGameEvent{" +
                "gameId=" + gameId +
                '}';
    }
}
