package us.heptet.magewars.gameservice.core.events;

import us.heptet.magewars.gameservice.core.events.games.GameExtendedDetails;

import java.io.Serializable;

/* Created by kay on 5/20/2014. */
/**
 * A class for representing game extended details as a response.
 */
public class GameExtendedDetailsEvent implements Serializable {
    private GameExtendedDetails gameExtendedDetails;


    public GameExtendedDetailsEvent() {
        /* no op */
    }

    /**
     * Construct an instance.
     * @param gameExtendedDetails
     */
    public GameExtendedDetailsEvent(GameExtendedDetails gameExtendedDetails) {
        this.gameExtendedDetails = gameExtendedDetails;
    }

    public GameExtendedDetails getGameExtendedDetails() {
        return gameExtendedDetails;
    }

    public void setGameExtendedDetails(GameExtendedDetails gameExtendedDetails) {
        this.gameExtendedDetails = gameExtendedDetails;
    }

    @Override
    public String toString() {
        return "GameExtendedDetailsEvent{" +
                "gameExtendedDetails=" + gameExtendedDetails +
                '}';
    }
}
