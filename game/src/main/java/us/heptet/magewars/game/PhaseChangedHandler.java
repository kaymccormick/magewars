package us.heptet.magewars.game;

import us.heptet.magewars.game.phase.PhaseInterface;

/* Created by kay on 4/1/2014. */
/**
 * pre-eventing phase changed handler interface.
 */
@FunctionalInterface
public interface PhaseChangedHandler {
    // is generic necessary?

    /***
     * Handle phase changes.
     * @param newPhase new phase
     *
     * @param <P> type of phase.
     */
    <P extends PhaseInterface> void phaseChanged(P newPhase);
}
