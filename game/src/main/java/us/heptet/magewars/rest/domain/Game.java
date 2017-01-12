package us.heptet.magewars.rest.domain;

import us.heptet.magewars.service.events.GameDetails;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/* Created by kay on 3/17/14. */
/**
 * Game class in the rest domain. For serialization purposes through spring.
 */
/* There is no "Game id" in this object - should there be? */
@XmlRootElement
public class Game implements Serializable {
    private String gameName;
    private int minPlayers;
    private int maxPlayers;
    private String createdByUsername;

    /**
     * Default for serialization.
     */
    public Game() {
        /* Serialization */
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

    /**
     * Convert to "Game Details" class
     * @return GameDetails instance
     */
    public GameDetails toGameDetails()
    {
        GameDetails details = new GameDetails();
        details.setGameName(gameName);
        details.setMinPlayers(minPlayers);
        details.setMaxPlayers(maxPlayers);
        details.setCreatedByUsername(createdByUsername);
        return details;
    }

    /**
     * Create from "Game details"
     * @param gameDetails GameDetails instance
     * @return Game instance
     */
    public static Game fromGameDetails(GameDetails gameDetails)
    {
        Game game = new Game();
        game.gameName = gameDetails.getGameName();
        game.maxPlayers = gameDetails.getMaxPlayers();
        game.minPlayers = gameDetails.getMinPlayers();
        game.createdByUsername = gameDetails.getCreatedByUsername();
        return game;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", minPlayers=" + minPlayers +
                ", maxPlayers=" + maxPlayers +
                '}';
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }
}
