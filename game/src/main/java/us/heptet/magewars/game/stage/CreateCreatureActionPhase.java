package us.heptet.magewars.game.stage;

import us.heptet.magewars.game.action.CreatureAction;
import us.heptet.magewars.game.phase.CreatureActionPhaseInterface;

/* Created by jade on 15/07/2016. */

/**
 * Factory for creature action phases.
 * @param <T> The type of creature action. extends {@link CreatureAction}
 */
@FunctionalInterface
public interface CreateCreatureActionPhase<T extends CreatureAction> {
    /**
     * Create a creature action phase.
     * @return The creature action phase.
     */
    CreatureActionPhaseInterface<T> createCreatureActionPhase();
}
