package us.heptet.magewars.domain.game.setup;

import us.heptet.magewars.domain.game.Mage;

import java.io.Serializable;
import java.util.Set;

/* Created by kay on 6/7/2014. */
/**
 * Interface for classes which encapsulate the Game set-up or configuration.
 *
 */
public interface GameSetup extends Serializable {
    /***
     * Returns the number of players set up for this game.
     * @return number of players
     */
    int getNumPlayers();

    /**
     * Use the default mages available for each player. A short-cut method to avoid excess configuration in testing and prototyping.
     */
    void useDefaults();

    /**
     * Set the number of players.
     * @param numPlayers    The number of players in the game.
     */
    void setNumPlayers(int numPlayers);

    /**
     * Get the set of available mages configured for the game.
     * @return Collection of available mages.
     */
    Set<Mage> getAvailableMages();

    /**
     * Set the available mages configured for the game.
     * @param availableMages Mages that are available.
     */
    void setAvailableMages(Set<Mage> availableMages);

    /**
     * Get a boolean indicating whether or not setup is complete.
     * @return boolean, true if setup is complete.
     */
    boolean isSetupComplete();

    /**
     * Get the player setup interface for a given player index.
     * @param playerIndex Player index.
     * @return Player setup
     */
    PlayerSetup getPlayerSetup(int playerIndex);

    /**
     * Get the mage card for a given player index.
     * @param playerIndex Player index.
     * @return Mage card.
     */
    Mage getPlayerMageCard(int playerIndex);

}
