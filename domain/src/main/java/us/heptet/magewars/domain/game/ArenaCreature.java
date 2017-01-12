package us.heptet.magewars.domain.game;

import java.util.List;

/* Created by kay on 3/27/14. */
/**
 * Interface for a Creature that is or will be placed in the Arena. The underlying
 * creature is part of a PlayerCard attaching it to a Player. My gut tells me this
 * should be a generic type with a type parameter.
 */
public interface ArenaCreature extends GameObject, Attackable {
    /***
     * Get the spells the arena creature has.
     * @return List of Spell instances.
     */
    List<Spell> getSpells();

    /***
     * Does the arena creature have quickcast ability?
     * @return boolean value, true if the arena creature has quickcast ability.
     */
    boolean hasQuickcastAbility();

    /***
     * If the arena creature has quickcast ability, is quickcast available or has it been used?
     * If the arena creature does not have quickcast ability, this will return false.
     * @return boolean value, true if quickcast is available.
     */
    boolean quickcastAvailable();

    /***
     * Direct the creature to channel mana.
     */
    void channel();

    /***
     * Is the creature active? Can it move?
     * @return boolean, true if the creature is active.
     */
    boolean isActive();

    /***
     * Set the quick available flag.
     * @param quickcastAvailable boolean indicating if quickcast is available
     */
    void setQuickcastAvailable(boolean quickcastAvailable);

    /**
     * Get the current life value for the creature.
     * @return Life value is a positive integer.
     */
    int getLife();
}
