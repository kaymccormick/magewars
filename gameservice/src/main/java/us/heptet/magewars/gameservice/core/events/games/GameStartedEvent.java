package us.heptet.magewars.gameservice.core.events.games;

import java.io.Serializable;

/* Created by kay on 6/2/2014. */
/**
 * Structure for the response to a start game request.
 */
public class GameStartedEvent implements Serializable {
    boolean gameStarted = true;
    String message;

    public GameStartedEvent() {
        /* no op */
    }

    /**
     * Construct an instance.
     * @param gameStarted
     */
    public GameStartedEvent(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    /**
     * Construct an instance.
     * @param gameStarted
     * @param message
     */
    public GameStartedEvent(boolean gameStarted, String message) {
        this.gameStarted = gameStarted;
        this.message = message;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    @Override
    public String toString() {
        return "GameStartedEvent{" +
                "gameStarted=" + gameStarted +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
