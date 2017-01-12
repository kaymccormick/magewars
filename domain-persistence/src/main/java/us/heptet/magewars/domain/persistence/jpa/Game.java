package us.heptet.magewars.domain.persistence.jpa;

import us.heptet.magewars.domain.persistence.exceptions.PlayerNotFoundException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Created by kay on 5/5/2014. */
/**
 * Entity class for a Game.
 */
@Entity
public class Game implements Serializable {
    @SequenceGenerator(name="game_gameid_seq", sequenceName = "game_gameid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_gameid_seq")
    @Id
    private Integer gameId;

    private String name;

    private int minPlayers;
    private int maxPlayers;

    // This is not cascade because ... createdByUser should already be an existing user.
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="userId", nullable = false)
    private
    User createdByUser;

    private GameStatus status;

    /* I'd like to use a non-primary-key column to join to mage/card
       but it isn't required to be supported and doesn't appear to work
       in hibernate, at least with the annotations I used */

    @ManyToMany
    @JoinTable(name = "gamemage", joinColumns = {@JoinColumn(name = "gameid")},
            inverseJoinColumns = {@JoinColumn(name = "cardid")})
    private
    Set<Mage> availableMages;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Player> players;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Map<ZoneSpec, Zone> zones;

    /**
     * Create a default game object.
     */
    public Game() {
        zones = new HashMap<>();
        players = new HashSet<>();
        availableMages = new HashSet<>();
        status = GameStatus.SETUP;
    }



    /**
     * Create a game object with the specified attributes.
     * @param name  Name of the game.
     * @param minPlayers Minimum number of players.
     * @param maxPlayers Maximum number of players.
     * @param createdByUser The user entity which created the game.
     * @param status The status of the game.
     */
    public Game(String name, int minPlayers, int maxPlayers, User createdByUser, GameStatus status) {
        this();
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.createdByUser = createdByUser;
        this.status = status;
    }

    /***
     * Add a zone entity to the game entity.
     * @param zone The zone entity to add.
     */
    public void addZone(Zone zone)
    {
        zones.put(new ZoneSpec(zone.getZoneCol(), zone.getZoneRow()), zone);
    }

    /**
     * Get the Player JPA instance for a given player instance.
     * @param playerIndex Player index.
     * @return Player instance.
     */
    public Player getPlayer(int playerIndex)
    {
        for(Player player:players) {
            if(player.getPlayerSlot() == playerIndex)
            {
                return player;
            }
        }
        throw new PlayerNotFoundException();
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<ZoneSpec, Zone> getZones() {
        return zones;
    }

    public void setZones(Map<ZoneSpec, Zone> zones) {
        this.zones = zones;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Set<Mage> getAvailableMages() {
        return availableMages;
    }

    public void setAvailableMages(Set<Mage> availableMages) {
        this.availableMages = availableMages;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", name='" + name + '\'' +
                ", createdByUser=" + createdByUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Game game = (Game) o;

        if (gameId != game.gameId) {
            return false;
        }
        if (maxPlayers != game.maxPlayers) {
            return false;
        }
        if (minPlayers != game.minPlayers) {
            return false;
        }
        if (createdByUser != null ? !createdByUser.equals(game.createdByUser) : game.createdByUser != null)
            return false;
        if (name != null ? !name.equals(game.name) : game.name != null) {
            return false;
        }
        return status == game.status;

    }



    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + minPlayers;
        result = 31 * result + maxPlayers;
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }


}

