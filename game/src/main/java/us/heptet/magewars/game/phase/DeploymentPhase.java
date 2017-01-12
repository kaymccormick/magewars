package us.heptet.magewars.game.phase;

import us.heptet.magewars.game.GameSituation;

/* Created by kay on 1/12/14. */
/**
 * The deployment phase.
 */
public class DeploymentPhase extends Phase {
    public DeploymentPhase() {
        /* for serialization */
    }

    /**
     * Create the deployment phase.
     * @param gameSituation Game situation
     */
    public DeploymentPhase(GameSituation gameSituation)
    {
        super(gameSituation);
        setName("Deployment Phase");
    }

    @Override
    public void startPhase() {
        /* no-op */
    }
}
