package us.heptet.magewars.domain.game;

/* Created by kay on 3/27/14. */
/**
 * Default implementation of the ActionTarget interface.
 * This class might should be abstract.
 */
public class ActionTargetImpl implements ActionTarget {
    private ActionTarget previousActionTarget;
    private ActionTarget nextActionTarget;
    private boolean optional;
    private Range range;
    private SourceGameObjectFunction sourceGameObjectFunction;

    /**
     * Create an instance.
     */
    public ActionTargetImpl() {
        setRange(new Range(0, 1));
    }

    // MW-25 - Static methods in us.heptet.magewars.domain.game.ActionTargetImpl should live somewhere else. july 6 2016

    /***
     * Return an action target that selects zones.
     * @return ActionTarget instance
     */
    public static ActionTarget zoneTarget()
    {
        return GameElementFactory.createActionTargetMatchGameElementType(GameElementType.ZONE, false);
    }

    /**
     * Return an action target that selects zones with the provided optional flag.
     * @param optional If true, the target is optional.
     * @return ActionTarget instance
     */
    public static ActionTarget zoneTarget(boolean optional)
    {
        return GameElementFactory.createActionTargetMatchGameElementType(GameElementType.ZONE, optional);
    }


    /***
     * Return an action target that selects creatures.
     * @return ActionTarget instance
     */
    // MW-25 - Static methods in us.heptet.magewars.domain.game.ActionTargetImpl should live somewhere else. july 6 2016
    public static ActionTarget creatureTarget()
    {
        return GameElementFactory.createActionTargetMatchGameElementType(GameElementType.CREATURE, false);
    }

    /**
     * Return an action target that selects creatures with the provided optional flag.
     * @param optional If true, the target is optional.
     * @return ActionTarget instance
     */
    public static ActionTarget creatureTarget(boolean optional)
    {
        return GameElementFactory.createActionTargetMatchGameElementType(GameElementType.CREATURE, optional);
    }


    @Override
    public ActionTarget additionalActionTarget(ActionTarget next)
    {
        this.nextActionTarget = next;
        next.setPreviousActionTarget(this);
        return this;
    }

    @Override
    public ActionTarget withRange(Range range)
    {
        setRange(range);
        return this;
    }

    @Override
    public GameElement getSourceGameObject(AcquiredActionTarget acquiredActionTarget)
    {
        return getSourceGameObjectFunction().apply(acquiredActionTarget);
    }

    @Override
    public boolean isValidTarget(Zone zone, GameElement gameElement, AcquiredActionTarget pendingTarget) {
        return false;
    }

    @Override
    public ActionTarget getPreviousActionTarget() {
        return previousActionTarget;
    }

    @Override
    public void setPreviousActionTarget(ActionTarget previousActionTarget) {
        this.previousActionTarget = previousActionTarget;
    }

    @Override
    public ActionTarget getNextActionTarget() {
        return nextActionTarget;
    }

    public void setNextActionTarget(ActionTarget nextActionTarget) {
        this.nextActionTarget = nextActionTarget;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    @Override
    public SourceGameObjectFunction getSourceGameObjectFunction() {
        return sourceGameObjectFunction;
    }

    @Override
    public void setSourceGameObjectFunction(SourceGameObjectFunction sourceGameObjectFunction) {
        this.sourceGameObjectFunction = sourceGameObjectFunction;
    }

    @Override
    public ActionTarget withSourceGameObjectFunction(SourceGameObjectFunction sourceGameObjectFunction)
    {
        setSourceGameObjectFunction(sourceGameObjectFunction);
        return this;
    }

    @Override
    public boolean checkRange(Zone zone, GameElement gameElement, AcquiredActionTarget pendingTarget) {
        /* do we have all the info we need here? */
        GameElement sourceGameObject;
        if(getSourceGameObjectFunction() == null)
        {
            sourceGameObject = zone;
        }
        else
        {
            sourceGameObject = getSourceGameObject(pendingTarget);
        }

        int distance = gameElement.distanceFromGameElement(sourceGameObject);
        return distance >= getRange().getMinRange() && distance <= getRange().getMaxRange();
    }

}
