package us.heptet.magewars.domain.game;

import us.heptet.magewars.domain.game.Mage;

import java.util.Set;

/* Created by kay on 6/20/2014. */
/**
 * Interface that provides a method to retreive the "available mages" as a set.
 */
@FunctionalInterface
public interface AvailableMages {
    /***
     * Get the set of mages available in the game.
     * @return Set of Mage instances
     */
    Set<Mage> getAvailableMageSet();
}
