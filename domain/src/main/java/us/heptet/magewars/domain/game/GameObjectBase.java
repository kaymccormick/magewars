package us.heptet.magewars.domain.game;



import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.exceptions.InvalidGameElementTypeException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* Created by kay on 3/27/14. */
/**
 * Base class for a GameObject.
 */
public class GameObjectBase extends GameElementBase implements GameObject  {
    private PlayerCard playerCard;
    private Zone location;
    private List<GameObjectEnchantment> enchantments = new ArrayList<>();

    protected GameObjectBase() {
        /* Empty for serialization */
    }

    protected GameObjectBase(PlayerCard playerCard) {
        super(playerCard.getPlayer(), playerCard.getGameElementType());
        setPlayerCard(playerCard);
    }

    @Override
    public PlayerCard getPlayerCard() {
        return playerCard;
    }

    @Override
    public void resetObject() {
        /* no-op */
    }


    @Override
    @JsonIgnore
    public Iterator<Attack> getAttacks() {
        return getPlayerCard().getCard().getAttacks();
    }

    @JsonIdentityReference(alwaysAsId = true)
    @Override
    public Player getControllingPlayer() {
        return getPlayerCard().getPlayer();
    }

    @Override
    public Range getMoveRange() {
        // objects cant move?
        return new Range(0, 0);
    }

    public void setPlayerCard(PlayerCard playerCard) {
        this.playerCard = playerCard;
    }

    @Override
    public Zone getLocation() {
        return location;
    }

    public void setLocation(Zone location) {
        this.location = location;
    }

    @Override
    public void moveObject(Zone zone) {
        // should this do more than setlocation?
        setLocation(zone);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addEnchantment(PlayerCard enchantment, boolean revealed) {
        if(enchantment.getGameElementType() != GameElementType.ENCHANTMENT)
        {
            throw new InvalidGameElementTypeException("Supplied PlayerCard (" + enchantment.getCard().getCardName() + ") is not an enchantment", GameElementType.ENCHANTMENT, enchantment.getGameElementType());
        }
        GameObjectEnchantment objectEnchantment =
                GameElementFactory.createGameObjectEnchantment(this, enchantment);

        objectEnchantment.setRevealed(revealed);
        enchantments.add(objectEnchantment);
    }

    @Override
    public List<GameObjectEnchantment> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(List<GameObjectEnchantment> enchantments) {
        this.enchantments = enchantments;
    }

    @Override
    public String toString() {
        return "GameObjectBase{" +
                "playerCard=" + playerCard +
                ", location=" + (location != null ? ("(" + location.getCol() + "," + location.getRow() +
                ")" ) : "null") + "} " + super.toString();
    }
}
