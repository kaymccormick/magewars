package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 5/22/2014. */
/**
 * Class representing a game in the system (non-playable, just an entry)
 */
public class Game implements Serializable {
    private String gameName;
    private int minPlayers;
    private int maxPlayers;
    private int gameTableId;

    /***
     * Default constructor.
     */
    public Game() {
        /* Default constructor */
    }

    /***
     * Create a game for registration.
     * @param gameName Name of game
     * @param minPlayers Minimum number of players.
     * @param maxPlayers Maximum number of players.
     */
    public Game(String gameName, int minPlayers, int maxPlayers) {
        this.gameName = gameName;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getGameTableId() {
        return gameTableId;
    }

    public void setGameTableId(int gameTableId) {
        this.gameTableId = gameTableId;
    }
}
