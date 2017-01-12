package us.heptet.magewars.game.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.AcquiredActionTargets;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;

import java.io.Serializable;

/**
 * Interface implemented by "actions" within the game system, and a component of {@link UiAction}.
 * @see UiAction
 */
public interface Action extends Serializable {
    /**
     * By default this method is called by Phase.consumeAction. consumeAction does not appear to be called by game machinery ATM.
     *
     */
    void executeAction();

    /**
     * Set the player associated with the aciton.
     * @param player The player to be associated with the action.
     */
    void setPlayer(Player player);

    /***
     * Accessor method returning the boolean indicating whether or not the action is ready to consume.
     * @return Boolean value; true if the action is ready to consume.
     */
    boolean isReadyToConsume();

    /***
     * Accessor method to set the boolean indicating whether or not the action is ready to consume.
     * @param readyToConsume Boolean value; true if the action is ready to consume.
     */
    void setReadyToConsume(boolean readyToConsume);

    /**
     * Get a boolean indicating if the action can be passed.
     * @return Boolean, true if the action can be passed.
     */
    boolean isPassAction();

    /***
     * Set the "can cass action" boolean
     * @param passAction Boolean, true if the action can be passed.
     */
    void setPassAction(boolean passAction);

    /***
     * Retrieve the game object associated with this action.
     * @return Game object.
     */
    @JsonIgnore
    GameObject getGameObject();

    /***
     * Set the game object associated with this action.
     * @param gameObject Game object.
     */
    void setGameObject(GameObject gameObject);

    /***
     * Get the acquired action targets for this action.
     * @return Acquired action targets.
     */
    @JsonIgnore
    AcquiredActionTargets getAcquiredActionTargets();

    /***
     * Set the acquired action targets for this action.
     * @param acquiredActionTargets Acquired action targets.
     */
    void setAcquiredActionTargets(AcquiredActionTargets acquiredActionTargets);

    /**
     * Check the acquired action targets for validity.
     * @return true if the acquired action targets are valid.
     */
    boolean checkActionTargets();
}
