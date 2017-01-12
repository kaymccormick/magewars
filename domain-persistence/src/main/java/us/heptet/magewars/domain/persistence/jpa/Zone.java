package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/* Created by kay on 5/4/2014. */
/**
 * Entity type for Zone.
 */
@Entity
public class Zone implements Serializable {
    @EmbeddedId ZonePK id;

    @JoinColumn(name="gameId")
    @MapsId("gamePK")
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private Game game;

    @JoinColumns({@JoinColumn(name = "gameId", referencedColumnName = "gameId"),
            @JoinColumn(name = "zonerow", referencedColumnName = "zonerow"),
            @JoinColumn(name = "zonecol", referencedColumnName = "zonecol")})
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ArenaObject> arenaObjects;

    /**
     * Create a zone instance
     */
    public Zone() {
        arenaObjects = new HashSet<>();
    }

    /**
     * Create a zone instance
     * @param game Game JPA instance
     * @param row row of zone
     * @param col column of zone
     */
    public Zone(Game game, int row, int col) {
        this();
        this.game = game;
        this.id = new ZonePK();
        this.id.zonecol = col;
        this.id.zonerow = row;
    }



    public Game getGame() {
        return game;
    }


    public void setGame(Game game) {
        this.game = game;
    }

    public Set<ArenaObject> getArenaObjects() {
        return arenaObjects;
    }

    public void setArenaObjects(Set<ArenaObject> arenaObjects) {
        this.arenaObjects = arenaObjects;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "row=" + id.zonerow +
                ", col=" + id.zonecol +
                ", game=" + game +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zone zone = (Zone) o;

        if (!id.equals(zone.id)) return false;
        if (!game.equals(zone.game)) return false;
        return arenaObjects != null ? arenaObjects.equals(zone.arenaObjects) : zone.arenaObjects == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + game.hashCode();
        result = 31 * result + (arenaObjects != null ? arenaObjects.hashCode() : 0);
        return result;
    }

    public int getZoneRow() {
        return id.zonerow;
    }

    public int getZoneCol() {
        return id.zonecol;
    }
}
