package us.heptet.magewars.game.phase;

import us.heptet.magewars.domain.game.Player;

/* Created by jade on 10/09/2016. */

/**
 * Interface for access to per-player state in a phase.
 */
public interface PlayerPhaseState {
    /**
     * Retrieve player
     * @return Player
     */
    Player getPlayer();

    /**
     * Retrieve boolean indicating "player ready to complete."
     * @return boolean indicating if the player
     */
    Boolean getPlayerReadyToComplete();

    /**
     * Set boolean indicating "player ready to complete."
     * @param readiness Boolean indicating the player is ready to complete
     */
    void setPlayerReadyToComplete(Boolean readiness);
}
