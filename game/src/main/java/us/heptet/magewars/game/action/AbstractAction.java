package us.heptet.magewars.game.action;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;
import us.heptet.magewars.game.exceptions.ActionException;

/**
 * Abstract base class for a game action.
 */
public abstract class AbstractAction implements Action {
    private Player player;
    /**
     * Any action targets that have been "acquired," that is, selected.
     */
    protected AcquiredActionTargets acquiredActionTargets = new AcquiredActionTargets();
    private boolean readyToConsume;
    private boolean passAction;
    private GameObject gameObject;

    public AbstractAction() {
        /* Default constructor */
    }

    /**
     * Supertype constructor for concrete types
     * @param player Player
     * @param gameObject Game object
     */
    public AbstractAction(Player player, GameObject gameObject)
    {
        setPlayer(player);
        setGameObject(gameObject);
    }

    @Override
    public void executeAction()
    {
        if(!checkActionTargets())
        {
            throw new ActionException("target(s) invalid", this);
        }
    }

    @JsonIdentityReference(alwaysAsId = true)
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }


    @JsonIgnore
    @Override
    public AcquiredActionTargets getAcquiredActionTargets() {
        return acquiredActionTargets;
    }

    @JsonIgnore
    @Override
    public void setAcquiredActionTargets(AcquiredActionTargets acquiredActionTargets) {
        this.acquiredActionTargets = acquiredActionTargets;
    }

    @Override
    public boolean isReadyToConsume() {
        return readyToConsume;
    }

    @Override
    public void
    setReadyToConsume(boolean readyToConsume) {
        this.readyToConsume = readyToConsume;
    }

    @Override
    public boolean isPassAction() {
        return passAction;
    }

    @Override
    public void setPassAction(boolean passAction) {
        this.passAction = passAction;
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    /**
     * This method assumes that other sorts of checking has been done, it only checks to ensure all non-optional targets have been fulfilled.
     * @return boolean, true if the action targets "Check out"
     */
    @Override
    public boolean checkActionTargets() {
        for(AcquiredActionTarget target: acquiredActionTargets.getTargets()) {
            ActionTarget actionTarget = target.getActionTarget();
            if(actionTarget == null)
            {
                throw new InvalidGameStateException("actionTarget of " + target + " should not be null");
            }
            if (!actionTarget.isOptional() && target.getTarget() == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAction action = (AbstractAction) o;

        if (readyToConsume != action.readyToConsume) return false;
        if (passAction != action.passAction) return false;
        if (!player.equals(action.player)) return false;
        if (!acquiredActionTargets.equals(action.acquiredActionTargets)) return false;
        return gameObject.equals(action.gameObject);

    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + acquiredActionTargets.hashCode();
        result = 31 * result + (readyToConsume ? 1 : 0);
        result = 31 * result + (passAction ? 1 : 0);
        result = 31 * result + gameObject.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AbstractAction{" +
                "player=" + player +
                ", acquiredActionTargets=" + acquiredActionTargets +
                ", readyToConsume=" + readyToConsume +
                ", passAction=" + passAction +
                ", gameObject=" + gameObject +
                '}';
    }
}
