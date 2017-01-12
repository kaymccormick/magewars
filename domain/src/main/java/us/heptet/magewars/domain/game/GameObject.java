package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import java.util.Iterator;

/* Created by kay on 3/24/14. */
/**
 * Basic game object - extends GameElement and Enchantable.
 */
public interface GameObject extends GameElement, Enchantable {

    /***
     * Move the object to a new zone.
     * @param zone Zone to which the creature should be moved.
     */
    void moveObject(Zone zone);

    /***
     * Get the associated PlayerCard.
     * @return The underlying PlayerCard.
     */
    PlayerCard getPlayerCard();

    /***
     * Reset the object as part of the reset phase.
     */
    void resetObject();

    /***
     * Get the attacks available to the object.
     * @return Attack iterator.
     */
    Iterator<Attack> getAttacks();

    /***
     * Get the player that controls the object. I don't know why there is Override here. mistake?
     * @return The controlling player.
     */
    @Override
    @JsonIdentityReference(alwaysAsId = true)
    Player getControllingPlayer();

    /**
     * Retrieve the move range for the object.
     * @return Range instance specifying the allowable range.
     */
    Range getMoveRange();
}
