package us.heptet.magewars.game.phase;

import us.heptet.magewars.game.GameSituation;

/* Created by kay on 1/12/14. */
/**
 * The upkeep phase.
 */
public class UpkeepPhase extends Phase {

    public UpkeepPhase() {
        /* for serialization */
    }

    /**
     * Create the upkeep phase.
     * @param gameSituation
     */
    public UpkeepPhase(GameSituation gameSituation)
    {
        super(gameSituation);
        setName("Upkeep Phase");
        phaseType = ReadyPhaseType.UPKEEP;
   }

    @Override
    public void startPhase() {
        /* no-op */
    }
}
