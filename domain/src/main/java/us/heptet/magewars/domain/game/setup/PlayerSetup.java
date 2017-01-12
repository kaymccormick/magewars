package us.heptet.magewars.domain.game.setup;

import java.io.Serializable;

/**
 * Created by jade on 20/09/2016.
 */
public interface PlayerSetup extends Serializable {
    /**
     * Set the "card enum name" for the player.
     * @param cardEnumName Card enum name.
     */
    void setMageCardEnumName(String cardEnumName);

    /**
     * Set the spellbook card count for a particular card-enum-name.
     * @param cardEnumName Card enum name.
     * @param count Count of card.
     */
    void setPlayerSpellbookCardCount(String cardEnumName, int count);

    /**
     * Determine if setup is complete.
     * @return boolean, true if setup is complete.
     */

    boolean isSetupComplete();

    /**
     * Get the "card enum name" for the player.
     * @return String representing the mage card enum name.
     */
    String getMageCardEnumName();

    /**
     * Get the username for the player.
     * @return The username
     */
    String getUserName();

    /**
     * Set te username for the player.
     * @param userName Username
     */
    void setUserName(String userName);
}
