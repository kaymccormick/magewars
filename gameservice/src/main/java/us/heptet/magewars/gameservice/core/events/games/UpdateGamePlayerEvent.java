package us.heptet.magewars.gameservice.core.events.games;

import java.io.Serializable;

/* Created by kay on 6/11/2014. */
/**
 * Structure for updating a player in an existing game.
 */
public class UpdateGamePlayerEvent implements Serializable {
    int gameId;
    String username;
    Integer playerSlot;
    int spellbookId;

    public UpdateGamePlayerEvent() {
        /* no op */
    }

    /**
     * Construct an instance.
     * @param gameId
     * @param username
     * @param spellbookId
     */
    public UpdateGamePlayerEvent(int gameId, String username, int spellbookId) {
        this.gameId = gameId;
        this.username = username;
        this.spellbookId = spellbookId;
    }

    /**
     * Construct an instance.
     * @param gameId
     * @param playerSlot
     * @param spellbookId
     */
    public UpdateGamePlayerEvent(int gameId, Integer playerSlot, int spellbookId) {
        this.gameId = gameId;
        this.playerSlot = playerSlot;
        this.spellbookId = spellbookId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(Integer playerSlot) {
        this.playerSlot = playerSlot;
    }

    public int getSpellbookId() {
        return spellbookId;
    }

    public void setSpellbookId(int spellbookId) {
        this.spellbookId = spellbookId;
    }
}
