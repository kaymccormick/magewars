package us.heptet.magewars.service.events;

import java.io.Serializable;

/* Created by kay on 3/17/14. */
/**
 * Class representing game details.
 */
public class GameDetails implements Serializable {
    private Integer gameId;
    private String gameName;

    private String createdByUsername;
    private int minPlayers;
    private int maxPlayers;
    private GameStatus gameStatus;

    public GameDetails()
    {
        /* default */

    }

    /**
     * Construct an instance.
     * @param gameId Game id.
     * @param gameName Game name.
     * @param createdByUsername Username of game creator.
     * @param minPlayers Minimum numbers of players.
     * @param maxPlayers Maximum number of players.
     */
    public GameDetails(int gameId, String gameName, String createdByUsername, int minPlayers, int maxPlayers) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.createdByUsername = createdByUsername;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Construct an instance.
     * @param gameName Game name.
     * @param createdByUsername Username of game creator.
     * @param minPlayers Minimum numbers of players.
     * @param maxPlayers Maximum number of players.
     */
    public GameDetails(String gameName, String createdByUsername, int minPlayers, int maxPlayers) {
        this.gameName = gameName;
        this.createdByUsername = createdByUsername;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Construct an instance.
     * @param gameId Game id.
     * @param gameName Game name.
     * @param createdByUsername Username of game creator.
     * @param minPlayers Minimum numbers of players.
     * @param maxPlayers Maximum number of players.
     * @param gameStatus Status of game
     */
    public GameDetails(Integer gameId, String gameName, String createdByUsername, int minPlayers, int maxPlayers, GameStatus gameStatus) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.createdByUsername = createdByUsername;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.gameStatus = gameStatus;
    }

    /**
     * Copy constructor.
     * @param copyOf Game details to copy.
     */
    public GameDetails(GameDetails copyOf)
    {
        this(copyOf.getGameId(), copyOf.getGameName(), copyOf.getCreatedByUsername(), copyOf.getMinPlayers(),
                copyOf.getMaxPlayers(), copyOf.getGameStatus());
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

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public String toString() {
        return "GameDetails{" +
                "gameName='" + gameName + '\'' +
                ", createdByUsername='" + createdByUsername + '\'' +
                '}';
    }
}
