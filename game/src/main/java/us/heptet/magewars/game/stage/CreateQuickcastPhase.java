package us.heptet.magewars.game.stage;

import us.heptet.magewars.game.phase.QuickcastPhase;

/**
 * Created by jade on 15/07/2016.
 */

/**
 * Interface for creating quickcast phases.
 */
@FunctionalInterface
public interface CreateQuickcastPhase {
    /**
     * Create a quickcast phase (unused?)
     * @param name Name of quickcast phase
     * @param isFirst Is it the first quickcast phase?
     * @return Quickcast phase
     */
    QuickcastPhase createQuickcastPhase(String name, boolean isFirst);
}
