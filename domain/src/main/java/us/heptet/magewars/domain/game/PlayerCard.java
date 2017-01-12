package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/* Created by kay on 3/24/14. */
/***
 * Class that represents a Card that has an associated Player, or a particular instance of the Card in the game
 * model. It does not, by itself, represent a Card in the arena - this is the function of {@link ArenaCreature},
 * {@link GameObject} and related types.
 *
 * To create an instance of this type, use {@link GameElementFactory}.
 * @param <T> The underlying Card type.
 */
public class PlayerCard<T extends Card> implements Spell, Serializable {
    private T card;
    private Player player;

    public PlayerCard() {
        /* Public constructor */
    }

    PlayerCard(Player player, T card)
    {
        setPlayer(player);
        setCard(card);
    }

    @Override
    public String toString() {
        return "PlayerCard{" +
                "Card=" + card +
                ", player=" + player.getPlayerIndex() +
                '}';
    }


    @JsonIdentityReference(alwaysAsId = true)
    public T getCard() {
        return card;
    }

    public void setCard(T card) {
        this.card = card;
    }

    @JsonIdentityReference(alwaysAsId = true)
    public Player getPlayer() {
        return player;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    /***
     * A convenience method to retrieve the {@link GameElementType} associated with the {@link Card}.
     * @return The game element type.
     */
    @JsonIgnore
    public GameElementType getGameElementType() {
        return getCard().getGameElementType();
    }

    /***
     * A convenience method to retrieve the {@link ActionSpeed} of the {@link Card}
     * @return The speed of the card.
     */
    @JsonIgnore
    @Override
    public ActionSpeed getSpeed() {
        return getCard().getSpeed();
    }

    @JsonIgnore
    @Override
    public boolean isQuickspell() {
        return getCard().isQuickSpell();
    }

    @JsonIgnore
    @Override
    public String getSpellName() {
        return getCard().getCardName();
    }

    @JsonIgnore
    @Override
    public ActionTarget getActionTarget() {
        return getCard().getActionTarget();
    }

    // Not sure playercard should have this method .. hmm
    @Override
    public void castSpell(AcquiredActionTargets targets) {
        getCard().castCard(this, targets);
    }


    @JsonIgnore // now handled via identity info
    public Integer getPlayerIndex()
    {
        return getPlayer().getPlayerIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerCard<?> that = (PlayerCard<?>) o;

        if (!card.equals(that.card)) return false;
        return player.equals(that.player);

    }

    @Override
    public int hashCode() {
        int result = card.hashCode();
        result = 31 * result + player.hashCode();
        return result;
    }
}
