package us.heptet.magewars.game.factory;

import us.heptet.magewars.game.Round;

/* Created by jade on 29/07/2016. */

/**
 * Factory for round instances.
 */
@FunctionalInterface
public interface RoundFactory {
    /**
     * Create a round instance.
     * @return Round instance
     */
    Round createRound();
}
