package us.heptet.magewars.gameservice.core.events.games;

import java.io.Serializable;

/* Created by kay on 5/20/2014. */
/**
 * Details related to a player in the game.
 */
public class GamePlayerDetails implements Serializable {
    int playerSlot;
    String playerUsername;
    String mageEnumName;
    String spellbookName;
    boolean requestingPlayerSlot;

    private Integer spellbookId;

    public GamePlayerDetails() {
        /* no op */
    }

    /**
     * Construct an instance.
     * @param playerSlot
     * @param playerUsername
     */
    public GamePlayerDetails(int playerSlot, String playerUsername) {
        this.playerSlot = playerSlot;
        this.playerUsername = playerUsername;
    }

    /**
     * Construct an instance.
     * @param playerSlot
     * @param playerUsername
     * @param requestingPlayerSlot
     */

    public GamePlayerDetails(int playerSlot, String playerUsername, boolean requestingPlayerSlot) {
        this.playerSlot = playerSlot;
        this.playerUsername = playerUsername;
        this.requestingPlayerSlot = requestingPlayerSlot;
    }

    /**
     * Construct an instance.
     * @param playerSlot
     * @param playerUsername
     * @param mageEnumName
     */
    public GamePlayerDetails(int playerSlot, String playerUsername, String mageEnumName) {
        this.playerSlot = playerSlot;
        this.playerUsername = playerUsername;
        this.mageEnumName = mageEnumName;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getMageEnumName() {
        return mageEnumName;
    }

    public void setMageEnumName(String mageEnumName) {
        this.mageEnumName = mageEnumName;
    }

    public int getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(int playerSlot) {
        this.playerSlot = playerSlot;
    }


    @Override
    public String toString() {
        return "GamePlayerDetails{" +
                "playerSlot=" + playerSlot +
                ", playerUsername='" + playerUsername + '\'' +
                ", mageEnumName='" + mageEnumName + '\'' +
                '}';
    }

    public void setSpellbookId(Integer spellbookId) {
        this.spellbookId = spellbookId;
    }

    public Integer getSpellbookId() {
        return spellbookId;
    }

    public boolean isRequestingPlayerSlot() {
        return requestingPlayerSlot;
    }

    public void setRequestingPlayerSlot(boolean requestingPlayerSlot) {
        this.requestingPlayerSlot = requestingPlayerSlot;
    }
}
