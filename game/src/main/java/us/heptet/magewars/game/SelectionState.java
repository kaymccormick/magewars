package us.heptet.magewars.game;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;
import us.heptet.magewars.domain.game.exceptions.NotSelectableException;
import us.heptet.magewars.game.action.UiAction;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 2/13/14. */

/**
 * Class that represents the state of a selection. Constructed with parameters that included the {@link UiAction}
 * taking place, the {@link ActionTarget} used to capture the selection, and the relevant {@link Zone}.
 */
public class SelectionState {
    private static Logger logger = Logger.getLogger(SelectionState.class.getName());
    private UiAction<?> uiAction;
    /***
     * A selectable region.
     */
    private SelectableRegion hoveredRegion;
    private boolean selecting;
    private Zone zone;
    private ActionTarget actionTarget;
    private AcquiredActionTarget pendingAcquiredActionTarget;

    static {
        logger.setLevel(Level.FINEST);
    }

    private SelectionState(UiAction uiAction, ActionTarget actionTarget, Zone zone1)  {

        logger.finest("Creating selection state (uiAction = " + uiAction + "; actionTarget = " + actionTarget + "; zone = " + zone1 + ")");
        setUiAction(uiAction);
        setZone(zone1);
        assert actionTarget != null;
        Range range = actionTarget.getRange();
        assert range != null;

        setActionTarget(actionTarget);
        setSelecting(true);
        AcquiredActionTarget acquiredActionTarget = new AcquiredActionTarget(actionTarget);
        setPendingAcquiredActionTarget(acquiredActionTarget);
        logger.finest(this.toString());

    }

    /**
     * Create a selection state instance
     * @param uiAction Ui Action
     * @param actionTarget Action Target
     * @param zone1 This might be a hack for not having the source game object function up and running.
     * @return Selection state
     */
    public static SelectionState createSelectionState(UiAction uiAction, ActionTarget actionTarget, Zone zone1) {
        return new SelectionState(uiAction, actionTarget, zone1);
    }

    /* this is the ultimate method that uses "TargetType" to determine selectability
    of a game element to give hover feedback.
    it delegates partially to GameElement.isValidTarget, but does its own zone range checking.
    ownership or control of the object is not checked - this is a failing.
     */

    /**
     * Determine if the given element is selectable given the action target.
     * @param obj GameElement to test
     * @return boolean; true if the element is selectable.
     */
    public boolean isSelectable(GameElement obj) {
        logger.finest("ENTERING isSelectable");
        if (!isSelecting()) {
            logger.finest("Not selecting.");
            return false;
        }

        logger.finest("Game object = " + obj);

        ActionTarget actionTarget1 = getActionTarget();

        /* This is redundant but right now is for debugging */
        boolean rangeCheck = actionTarget1.checkRange(getZone(), obj, getPendingAcquiredActionTarget());
        logger.fine("Result from range check is " + rangeCheck);

        boolean isValidTarget;
        isValidTarget = actionTarget1.isValidTarget(getZone(), obj, getPendingAcquiredActionTarget());

        boolean result = isSelecting() && isValidTarget;
        logger.finest("Result is " + result);
        return result;
    }

    /**
     * Acquire the game element as a selection target
     * @param gameElement Game element to be "acquired"
     */
    @SuppressWarnings("unchecked")
    public void acquireSelectionTarget(GameElement gameElement) {
        // this does not check to ensure the target is selectable
        if (!isSelectable(gameElement)) {
            throw new NotSelectableException();
        }
        ActionTarget target = getActionTarget();
        getPendingAcquiredActionTarget().setTarget(gameElement);
        // Refactor to addActionTarget?
        getUiAction().getActionTargets().add(getPendingAcquiredActionTarget());

        ActionTarget nextActionTarget = target.getNextActionTarget();
        if (nextActionTarget != null) {
            logger.finest("preparing to select additional action target");
            setActionTarget(nextActionTarget);
            AcquiredActionTarget newAcquiredActionTarget = new AcquiredActionTarget(nextActionTarget);
            newAcquiredActionTarget.setPreviousAcquiredActionTarget(getPendingAcquiredActionTarget());
            setPendingAcquiredActionTarget(newAcquiredActionTarget);
        } else {
            logger.finest("Notifying UiAction that action target(s) have been selected.");
            getUiAction().selectionCompleted();
        }

    }

    @Override
    public String toString() {
        return "SelectionState [" + (isSelecting() ? "selecting" : "not selecting") + "]";
    }

    /**
     * Method defers to actionTarget ... means that actionTarget must be updated!
     * @return Range
     */
    public Range getRange() {
        return getActionTarget().getRange();
    }

    public void setActionTarget(ActionTarget actionTarget) {
        Range range = actionTarget.getRange();
        if(range == null)
        {
            throw new InvalidGameStateException("range should not be null on argument");
        }
        this.actionTarget = actionTarget;
    }

    public UiAction getUiAction() {
        return uiAction;
    }

    public void setUiAction(UiAction<?> uiAction) {
        this.uiAction = uiAction;
    }

    public SelectableRegion getHoveredRegion() {
        return hoveredRegion;
    }

    public void setHoveredRegion(SelectableRegion hoveredRegion) {
        this.hoveredRegion = hoveredRegion;
    }

    public boolean isSelecting() {
        return selecting;
    }

    public void setSelecting(boolean selecting) {
        SelectableRegion hovered = getHoveredRegion();
        if (!selecting && this.selecting && hovered != null) {
            hovered.defaultState();
        }

        this.selecting = selecting;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public ActionTarget getActionTarget() {
        return actionTarget;
    }

    public AcquiredActionTarget getPendingAcquiredActionTarget() {
        return pendingAcquiredActionTarget;
    }

    public void setPendingAcquiredActionTarget(AcquiredActionTarget pendingAcquiredActionTarget) {
        this.pendingAcquiredActionTarget = pendingAcquiredActionTarget;
    }
}
