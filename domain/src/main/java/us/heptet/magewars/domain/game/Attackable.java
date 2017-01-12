package us.heptet.magewars.domain.game;

/* Created by kay on 3/29/2014. */
/**
 * Interface for a game object which can be attacked with range or melee attacks.
 */
public interface Attackable {
    /***
     * Take the specified amount of damage.
     * @param damageAmount Amount of damage.
     */
    void damage(int damageAmount);

    /***
     * Retrieve the current effective armor strength.
     * @return Effective armor strength.
     */
    int getArmor();
}
