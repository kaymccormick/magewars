package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 4/7/2014. */
/**
 * The interface for a target of an action, such as an attack or spell. Some
 * actions can have multiple targets, so this interface allows navigating linked-list
 * style between elements. It is important to emphasize the "potentiality" of the target.
 * When a target has been acquired, it is represented by {@link AcquiredActionTarget}. The {@link ActionTarget}
 * interface allows for the "specification" of a valid target - whether or not it is
 * optional, the allowable range, and a functional interface that, when invoked, returns the "source"
 * of the action - used for determining distance for the range checks.
 *
 *
 * @see us.heptet.magewars.domain.game.AcquiredActionTarget
 */
public interface ActionTarget extends Serializable {
    /***
     * Fluent API for chaining to an additional action target. Returns the original action target, i.e. the one upon
     * which the method has been called, and not the argument.
     * @param next the Next ActionTarget.
     * @return The ActionTarget instance.
     */
    ActionTarget additionalActionTarget(ActionTarget next);

    /***
     * Fluent setter to supply a range for the action target.
     * @param range Range
     * @return The ActionTarget instance.
     */
    ActionTarget withRange(Range range);

    /***
     * Return the "source game object," that is, the object from which distance calculations
     * are based for range checks. This can vary, and may not be the spell-caster. For some chained
     * spells it is the previous target.
     * @param acquiredActionTarget The acquired action target for this action target - this allows the
     *                             function to navigate to the previous targets.
     * @return The "source game object."
     */
    GameElement getSourceGameObject(AcquiredActionTarget acquiredActionTarget);

    /***
     * Determine if the supplied game element is a valid target for the action target.
     *
     * @param zone The source zone.
     * @param gameElement The potential target.
     * @param pendingTarget The pending acquired action target.
     * @return boolean indicating if the given GameElement is a valid target.
     */
    boolean isValidTarget(Zone zone, GameElement gameElement, AcquiredActionTarget pendingTarget);

    /***
     * Get the previous action target in the chain of action targets.
     * @return The previous ActionTarget.
     */
    ActionTarget getPreviousActionTarget();

    /***
     * Set the previous action target in the chain of action targets.
     * @param previousActionTarget The previous ActionTarget.
     */
    void setPreviousActionTarget(ActionTarget previousActionTarget);

    /***
     * Get the next action taeget in the chain of action targets.
     * @return The next ActionTarget.
     */
    ActionTarget getNextActionTarget();

    /***
     * Get a boolean indicating whether or not this action target is optional, that is, if
     * the action can be initiated and/or completed without having acquired a target.
     * @return Boolean indicating if the target is optionsl. true means that it is.
     */
    boolean isOptional();

    /***
     * Get the range of the action target.
     * @return Range of valid targets.
     */
    Range getRange();

    /***
     * Retrieve a functional interface, which, when invoked, will return the game object which
     * acts as the "source" for the purposes of determining distance in range checking.
     * @return Source game object function.
     */
    SourceGameObjectFunction getSourceGameObjectFunction();

    /**
     * Set the "source game object function".
     * @param sourceGameObjectFunction Source game object function.
     */
    void setSourceGameObjectFunction(SourceGameObjectFunction sourceGameObjectFunction);

    /**
     * Fluent API setter for the source game object function.
     * @param sourceGameObjectFunction Source game object function.
     * @return The ActionTarget instance.
     */
    ActionTarget withSourceGameObjectFunction(SourceGameObjectFunction sourceGameObjectFunction);

    /**
     * Check the range of gameElement argument, given the pending action target and the zone.
     * @param zone The zone.
     * @param gameElement The game element to check.
     * @param pendingTarget The pending target.
     * @return boolean, true if the distance is within bounds
     */
    boolean checkRange(Zone zone, GameElement gameElement, AcquiredActionTarget pendingTarget);
}
