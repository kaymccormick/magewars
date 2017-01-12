package us.heptet.magewars.gameservice.core.events.games;

import java.io.Serializable;

/* Created by kay on 5/22/2014. */
/**
 * An event representing the response to a request for a player to join a game.
 */
public class GameJoinedEvent implements Serializable {
    int playerSlot;
    boolean successfulJoin;

    public GameJoinedEvent() {
        /* no op */
    }

    /**
     * Construct an instance.
     * @param playerSlot
     * @param successfulJoin
     */
    public GameJoinedEvent(int playerSlot, boolean successfulJoin) {
        this.playerSlot = playerSlot;
        this.successfulJoin = successfulJoin;
    }

    public int getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(int playerSlot) {
        this.playerSlot = playerSlot;
    }

    public boolean isSuccessfulJoin() {
        return successfulJoin;
    }

    public void setSuccessfulJoin(boolean successfulJoin) {
        this.successfulJoin = successfulJoin;
    }
}
