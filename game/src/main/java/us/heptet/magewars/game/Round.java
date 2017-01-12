package us.heptet.magewars.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.stage.ActionStage;
import us.heptet.magewars.game.stage.ReadyStage;
import us.heptet.magewars.game.stage.Stage;
import us.heptet.magewars.game.state.GameStateChangeProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 1/12/14. */
/**
 * A round in the game. Consists of stages ReadyStage and ActionStage, each of which consists of
 * multiple Phase objects.
 */
public class Round {
    private static Logger logger = Logger.getLogger(Round.class.getName());
    private Stage stage;
    private List<Stage> completedStages = new ArrayList<>();
    private List<Stage> stages = new ArrayList<>();
    private boolean readyToComplete;
    private Object dao;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Create an instance.
     * @param gameSituation The game situation.
     */
    public Round(GameSituation gameSituation)
    {
        stages.add(new ReadyStage(gameSituation));
        stages.add(new ActionStage(gameSituation));

    }

    /***
     * Advance to the next stage.
     * @param changeProcessor The processor.
     * @return The new and now-current phase.
     */
    public PhaseInterface nextStage(GameStateChangeProcessor changeProcessor)
    {
        logger.fine("in nextStage");
        Stage curStage = getStage();
        if(curStage != null)
        {
            logger.fine("Current stage is " + curStage.toString());
            curStage.completeStage();
            completedStages.add(curStage);
        }
        if(stages.isEmpty())
        {
            setStage(null);
            return null;
        }

        Stage nextStage;
        nextStage = stages.remove(0);

        logger.fine("next stage is " + nextStage);
        if(nextStage != null) // possible?
        {
            nextStage.initPhases();
            setStage(nextStage);
            return nextStage.startStage(changeProcessor);
        }
        throw new InvalidGameStateException("null stage in list");
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public boolean isReadyToComplete()
    {

        return stage == null && stages.isEmpty();
    }

    @JsonIgnore
    public Object getDao() {
        return dao;
    }

    public void setDao(Object dao) {
        this.dao = dao;
    }

    @Override
    public String toString() {
        return "Round{" +
                "stage=" + stage +
                ", completedStages=" + completedStages +
                ", stages=" + stages +
                ", readyToComplete=" + readyToComplete +
                ", dao=" + dao +
                '}';
    }
}
