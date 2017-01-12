package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.*;
import java.io.Serializable;

/* Created by kay on 5/4/2014. */
/**
 * JPA Entity class to represent an Arena Object.
 */
@Entity
public class ArenaObject implements Serializable {
    @SequenceGenerator(name="arenaobject_arenaobjectid_seq", sequenceName = "arenaobject_arenaobjectid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arenaobject_arenaobjectid_seq")
    @Id
    private int arenaObjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "gameId", referencedColumnName = "gameId"),
    @JoinColumn(name = "zonerow", referencedColumnName = "zonerow"),
    @JoinColumn(name = "zonecol", referencedColumnName = "zonecol")})
    private Zone zone;

    /*** TODO this needs linkage */
    private int player;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Card card;

    public int getArenaObjectId() {
        return arenaObjectId;
    }

    public void setArenaObjectId(int arenaObjectId) {
        this.arenaObjectId = arenaObjectId;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "ArenaObject{" +
                "arenaObjectId=" + arenaObjectId +
                ", zone=" + zone +
                ", player=" + player +
                ", card=" + card +
                '}';
    }
}
