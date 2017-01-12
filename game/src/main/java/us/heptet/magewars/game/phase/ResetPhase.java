package us.heptet.magewars.game.phase;

import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.game.GameSituation;

import java.util.logging.Logger;

/* Created by kay on 1/12/14. */
/**
 * Reset phase
 */
public class ResetPhase extends Phase {
    private static Logger logger = Logger.getLogger(ResetPhase.class.getName());
    public ResetPhase() {
        /* for serialization */
    }

    /**
     * Create the reset phase.
     * @param gameSituation Game situation
     */
    public ResetPhase(GameSituation gameSituation)
    {
        super(gameSituation);
        phaseType = ReadyPhaseType.RESET;
        setName("Reset Phase");
    }

    @Override
    public void executePhase() {

        super.executePhase();
        GameSituation gameSituation = getGameSituation();

        for(Object o: gameSituation.getArena().getAllObjects())
        {
            logger.info(this + "resetting " + o.toString());
            ((GameObject) o).resetObject();
        }
    }

    @Override
    public void startPhase() {
        /* no-op */
    }
}
