package us.heptet.magewars.game.stage;

import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.phase.*;
import us.heptet.magewars.game.state.GameStateChangeProcessor;

import java.util.List;

/* Created by kay on 1/12/14. */
/**
 * The ready stage.
 */
public class ReadyStage extends Stage {
    /**
     * Default constructor
     */
    public ReadyStage() {
        /* no-op */
    }

    /**
     * Create an instance
     * @param gameSituation Game situation
     */
    public ReadyStage(GameSituation gameSituation)
    {
        super(gameSituation);

        name = "Ready Stage";
        stageType = StageType.READY;

        List<PhaseInterface> phases = getPhases();

        phases.add(new InitiativePhase(gameSituation));
        phases.add(new ResetPhase(gameSituation));
        phases.add(new ChannelPhase(gameSituation));
        phases.add(new UpkeepPhase(gameSituation));
        phases.add(new PlanningPhase(gameSituation));
        phases.add(new DeploymentPhase(gameSituation));
    }

    @Override
    public void completeStage() {
        /* do nothing */

    }

    @Override
    public PhaseInterface startStage(GameStateChangeProcessor changeProcessor) {
        getGameSituation().getGameControl().setCurrentStage(this);
        return nextPhase(changeProcessor);
    }

    @Override
    public void initPhases() {
        /* do nothing?? */
    }
}
