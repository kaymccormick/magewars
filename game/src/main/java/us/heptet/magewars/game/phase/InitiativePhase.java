package us.heptet.magewars.game.phase;

import us.heptet.magewars.game.GameSituation;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 1/12/14. */
/**
 * Type representing the Initiative Phase.
 */
public class InitiativePhase extends Phase {
    private static transient Logger logger = Logger.getLogger(InitiativePhase.class.getName());

    static {
        logger.setLevel(Level.FINEST);
    }

    public InitiativePhase() {
        /* no-op */
    }

    /**
     * Create the initiative phase.
     * @param gameSituation Game situation
     */
    public InitiativePhase(GameSituation gameSituation)
    {
        super(gameSituation);
        phaseType = ReadyPhaseType.INITIATIVE;
        setName("Initiative Phase");
    }

    @Override
    public void executePhase()
    {
        super.executePhase();
        logger.fine("calling getGameSituation().changeInitiative()");
        getGameSituation().changeInitiative();
    }

    @Override
    public void startPhase() {
        /* Nothing happens on Phase start for this phase. Should we call super? */
    }
}
