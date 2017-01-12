package us.heptet.magewars.game.stage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.phase.CreatureActionPhase;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.phase.QuickcastPhase;
import us.heptet.magewars.game.state.GameStateChangeProcessor;


/* Created by kay on 2/10/14. */
/**
 * Action stage.
 */
public class ActionStage extends Stage {
    private transient CreateCreatureActionPhase createCreatureActionPhase = () -> new CreatureActionPhase(getGameSituation());
    private transient CreateQuickcastPhase createQuickcastPhase = (name, isFirst) -> new QuickcastPhase(getGameSituation(), name, isFirst);

    /**
     * Default constructor.
     */
    public ActionStage() {
        /* nothing to do */
    }

    /**
     * Create an instance.
     * @param gameSituation Game situation
     */
    public ActionStage(GameSituation gameSituation)
    {
        super(gameSituation);
        name = "Action Stage";
        stageType = StageType.ACTION;
    }

    @Override
    public void initPhases() {
        GameSituation gameSituation = getGameSituation();
        getPhases().add(new QuickcastPhase(gameSituation, "First Quickcast Phase", true));
        for(Object o: gameSituation.getArena().getAllObjects())
        {
            GameObject gameObject = (GameObject)o;
            if(gameObject instanceof ArenaCreature)
            {
                ArenaCreature arenaCreature = (ArenaCreature)gameObject;
                if(arenaCreature.isActive())
                {
                    getPhases().add(createCreatureActionPhase.createCreatureActionPhase());
                }
            }
        }
        getPhases().add(new QuickcastPhase(gameSituation, "Final Quickcast Phase", false));
    }

    @Override
    public PhaseInterface startStage(GameStateChangeProcessor changeProcessor) {
        getGameSituation().getGameControl().setCurrentStage(this);
        return nextPhase(changeProcessor);
    }

    @Override
    public PhaseInterface nextPhase(GameStateChangeProcessor changeProcessor) {
        if(getCurrentPhase() instanceof QuickcastPhase)
        {
            getGameSituation().setActingPlayerIndex(getGameSituation().getInitiativeIndex());
        }
        return super.nextPhase(changeProcessor);
    }

    @Override
    public void completeStage() {
        /* Nothing to do. */
    }

    @JsonIgnore
    public CreateCreatureActionPhase getCreateCreatureActionPhase() {
        return createCreatureActionPhase;
    }

    public void setCreateCreatureActionPhase(CreateCreatureActionPhase createCreatureActionPhase) {
        this.createCreatureActionPhase = createCreatureActionPhase;
    }

    @JsonIgnore
    public CreateQuickcastPhase getCreateQuickcastPhase() {
        return createQuickcastPhase;
    }

    public void setCreateQuickcastPhase(CreateQuickcastPhase createQuickcastPhase) {
        this.createQuickcastPhase = createQuickcastPhase;
    }
}
