package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Class represents an ActionTarget that has been "acquired" -- a GameElement that has been selected
 * for use as the target of a particular Action.
 *
 *
 */
public class AcquiredActionTarget implements Serializable {
    private GameElement target;
    private AcquiredActionTarget previousAcquiredActionTarget;
    private ActionTarget actionTarget;

    public AcquiredActionTarget() {
        /* Empty constructor for serializable type */
    }

    /***
     * Create an instance with the specified target.
     * @param actionTarget Action target associated with the target.
     * @param target Target that has been acquired.
     */
    public AcquiredActionTarget(ActionTarget actionTarget, GameElement target) {
        this.actionTarget = actionTarget;
        this.target = target;
    }

    /**
     * Initiailze with the associated action target.
     * @param actionTarget Associated action target.
     */
    public AcquiredActionTarget(ActionTarget actionTarget) {
        this.actionTarget = actionTarget;
    }

    public GameElement getTarget() {
        return target;
    }

    public void setTarget(GameElement target) {
        this.target = target;
    }

    public AcquiredActionTarget getPreviousAcquiredActionTarget() {
        return previousAcquiredActionTarget;
    }

    public void setPreviousAcquiredActionTarget(AcquiredActionTarget previousAcquiredActionTarget) {
        this.previousAcquiredActionTarget = previousAcquiredActionTarget;
    }

    @JsonIgnore
    public ActionTarget getActionTarget() {
        return actionTarget;
    }

    public void setActionTarget(ActionTarget actionTarget) {
        this.actionTarget = actionTarget;
    }
}
