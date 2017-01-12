package us.heptet.magewars.gameservice.core.events.games;

import java.io.Serializable;

/* Created by kay on 5/22/2014. */
/**
 * Response structure for a join game request.
 */
public class JoinGameEvent implements Serializable {
    private String username;
    private int gameId;

    public JoinGameEvent() {
        /* no op */
    }

    /***
     * Create an instance.
     * @param username
     * @param gameId
     */
    public JoinGameEvent(String username, int gameId) {
        this.username = username;
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
