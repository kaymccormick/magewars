package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.AcquiredActionTarget;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.SelectionState;
import us.heptet.magewars.game.UiActionCompletedHandler;
import us.heptet.magewars.game.phase.PhaseInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 2/14/14. */

/***
 * The UiAction class represents an in-game action as it relates to the user interface.
 * @param <T> type of the lower-level Action subtype.
 */
public abstract class UiAction<T extends Action> {
    private static Logger logger = Logger.getLogger(UiAction.class.getName());
    private PhaseInterface phase;
    private SelectionState selectionState;
    private Player player;
    /**
     * Targets acquired for the action to be performed on. Usually only one target, but some spells
     * support multiple targets.
     */
    protected List<AcquiredActionTarget> actionTargets = new ArrayList<>();
    /**
     * Whether or not the action is in progress.
     */
    private boolean inProgress;
    // unclear what "source" means in this context
    /**
     * The game object that is the source of the action. Typically this means the object or creature
     * that has been selected to take the action.
     */
    private GameObject sourceGameObject;

    private T action;
    private UiActionCompletedHandler handler;
    private ActionConsumer<T> actionConsumer;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Create a UiAction instance.
     * @param phase Phase.
     * @param player player.
     * @param gameObject game object.
     * @param actionConsumer action consumer functionalinterface.
     */
    public UiAction(PhaseInterface phase,
                    Player player,
                    GameObject gameObject,
                    ActionConsumer<T> actionConsumer
    ) {
        setPlayer(player);
        setSourceGameObject(gameObject);
        setPhase(phase);
        assert actionConsumer != null;
        this.actionConsumer = actionConsumer;
    }

    /***
     * Initiate the UI action - currently sets the "in progress" flag.
     */
    public void initiateAction() {
        setInProgress(true);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    /***
     * Cancel the action. Sets in-progress to false.
     */
    public void cancelAction() {
        setInProgress(false);
    }

    /***
     * Selection-completed method. Calls the handler.
     */
    public void selectionCompleted() {


        // should this be bound instead?
        T myAction = getAction();

        /* This seems weary */
        myAction.getAcquiredActionTargets().addAll(getActionTargets());
        myAction.setReadyToConsume(true);

        actionConsumer.accept(myAction);
        getSelectionState().setSelecting(false);
        if (handler != null) {
            logger.fine("Calling handler for completed action " + this);
            handler.uiActionCompleted(this, true);
        }

    }

    public List<AcquiredActionTarget> getActionTargets() {
        return actionTargets;
    }

    public void setActionTargets(List<AcquiredActionTarget> actionTargets) {
        this.actionTargets = actionTargets;
    }

    /**
     * Add an acquired action target.
     * @param target The acquired action target.
     */
    public void addActionTarget(AcquiredActionTarget target)
    {
        actionTargets.add(target);
    }

    public GameObject getSourceGameObject() {
        return sourceGameObject;
    }


    public void setSourceGameObject(GameObject sourceGameObject) {
        this.sourceGameObject = sourceGameObject;
    }

    public T getAction() {
        return action;
    }

    public void setAction(T action) {
        this.action = action;
    }

    public SelectionState getSelectionState() {
        return selectionState;
    }

    public void setSelectionState(SelectionState selectionState) {
        this.selectionState = selectionState;
    }

    public PhaseInterface getPhase() {
        return phase;
    }

    private void setPhase(PhaseInterface phase) {
        this.phase = phase;
    }

    public void setHandler(UiActionCompletedHandler handler) {
        this.handler = handler;
    }
}
