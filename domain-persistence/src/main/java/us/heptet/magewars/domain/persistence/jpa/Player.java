package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.*;
import java.io.Serializable;

/* Created by kay on 5/5/2014. */
/**
 * Entity class for a player.
 */
@Entity
public class Player implements Serializable {
    @SequenceGenerator(name="player_playerid_seq", sequenceName = "player_playerid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_playerid_seq")
    @Id
    private int playerId;

    @JoinColumn(name="gameId")
    @ManyToOne
    private Game game;

    private int playerSlot;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "userId")
    private User user;

    @JoinColumn(name = "mageid", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Mage mage;

    @ManyToOne
    private SpellBook spellBook;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mage getMage() {
        return mage;
    }

    public void setMage(Mage mage) {
        this.mage = mage;
    }

    public int getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(int playerSlot) {
        this.playerSlot = playerSlot;
    }

    public SpellBook getSpellBook() {
        return spellBook;
    }

    public void setSpellBook(SpellBook spellBook) {
        this.spellBook = spellBook;
    }

    @Override
    public String toString() {
        return "Player{" +
                "game=" + game +
                ", playerSlot=" + playerSlot +
                ", user=" + user +
                ", mage=" + mage +
                '}';
    }
}
