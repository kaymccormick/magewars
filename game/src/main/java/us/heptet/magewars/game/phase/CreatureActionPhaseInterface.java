package us.heptet.magewars.game.phase;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.game.action.CreatureAction;

/* Created by jade on 12/07/2016. */

/**
 * Interface for a creature action phase.
 * @param <T> Underlying action type for the phase
 */
public interface CreatureActionPhaseInterface<T extends CreatureAction> extends PhaseInterface<T> {
    /**
     * Get the arenaCreature
     * @return Arena Creature
     */
    ArenaCreature getArenaCreature();

    /**
     * Set the Arena Creature.
     * @param arenaCreature     Arena creature
     */
    void setArenaCreature(ArenaCreature arenaCreature);

    /**
     * Has the initial move been completed?
     * @return booean, true if the initial move has been completed.
     */
    boolean getInitialMoveCompleted();

    /**
     * Is the quick action enabled?
     * @return Boolean, true if quick action is enabled.
     */
    boolean getQuickActionEnabled();

    /**
     * Has the initial move been passed?
     * @return Boolean, true of the initial move has been "passed"
     */
    boolean getInitialMovePassed();

    /***
     * Set whether or not the initial move has been passed.
     * @param initialMovePassed Boolean, true if the intiial move should be marked as passed.
     */
    void setInitialMovePassed(boolean initialMovePassed);

    /**
     * Has the initial move been taken?
     * @return Boolean, true if the initial move has been taken.
     */
    boolean getInitialMoveTaken();

    /**
     * Set whether or not the initial move has been taken.
     * @param initialMoveTaken Boolean, true if the initial move should be marked as taken.
     */
    void setInitialMoveTaken(boolean initialMoveTaken);
}
