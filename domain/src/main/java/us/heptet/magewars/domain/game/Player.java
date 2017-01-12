package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import us.heptet.magewars.domain.server.PlayerResolver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 3/24/14. */
/**
 * Class representing a player in the game.
 */
@JsonIdentityInfo(property = "playerIndex", generator = ObjectIdGenerators.PropertyGenerator.class, resolver = PlayerResolver.class)
public class Player implements Serializable {
    private int playerIndex;
    private PlayerCard<Mage> magePlayerCard;
    private List<ArenaCreature> creatures = new ArrayList<>();
    private ArenaCreature mageArenaCreature;
    private SpellBook spellBook = null;

    public Player() {
        /* default constructor */
    }

    Player(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setMagePlayerCard(PlayerCard<Mage> magePlayerCard) {
        this.magePlayerCard = magePlayerCard;
    }

    public PlayerCard<Mage> getMagePlayerCard() {
        return magePlayerCard;
    }

    /***
     * Add a creature to the player to denote control or ownership. Book-keeping method.
     * @param creature ArenaCreature instance
     */
    public void addCreature(ArenaCreature creature)
    {
        getCreatures().add(creature);
    }

    public List<ArenaCreature> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<ArenaCreature> creatures) {
        this.creatures = creatures;
    }

    public ArenaCreature getMageArenaCreature() {
        return mageArenaCreature;
    }

    public void setMageArenaCreature(ArenaCreature mageArenaCreature) {
        this.mageArenaCreature = mageArenaCreature;
    }

    /***
     * Static factory method - do not call, instead call {@link GameElementFactory} createPlayer method.
     * @param i Player index.
     * @return Player instance
     */
    public static Player createPlayer(int i) {
        return new Player(i);
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
                "playerIndex=" + playerIndex +
                ", magePlayerCard=" + magePlayerCard +
                ", creatures=" + creatures +
                ", mageArenaCreature=" + mageArenaCreature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return playerIndex == player.playerIndex;

    }

    @Override
    public int hashCode() {
        return playerIndex;
    }
}
