package us.heptet.magewars.game.stage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import us.heptet.magewars.domain.game.exceptions.NoNextPhaseAvailable;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.exceptions.PhaseNotReadyToComplete;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.state.GameStateChange;
import us.heptet.magewars.game.state.GameStateChangeProcessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 1/12/14. */
/**
 * Abstract base class for a game stage.
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=ReadyStage.class,name="ReadyStage"), @JsonSubTypes.Type(value=ActionStage.class,name="ActionStage")})
public abstract class Stage implements Serializable {
    static transient Logger logger = Logger.getLogger(Stage.class.getName());

    static {
        logger.setLevel(Level.FINEST);
    }
    private transient GameSituation gameSituation;
    protected String name;
    private PhaseInterface currentPhase;

    StageType stageType;
    private List<PhaseInterface> phases = new ArrayList<>();
    private List<PhaseInterface> completedPhases = new ArrayList<>();

    public Stage() {
    }

    /**
     * Create a stage
     * @param gameSituation The {@link GameSituation}.
     */
    public Stage(GameSituation gameSituation)
    {
        this.gameSituation = gameSituation;
    }

    public void setStageType(StageType stageType) {
        this.stageType = stageType;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    public PhaseInterface getCurrentPhase() {
        return currentPhase;
    }

    private void setCurrentPhase(PhaseInterface currentPhase) {
        if(gameSituation != null) {
            gameSituation.getGameControl().setCurrentPhase(currentPhase);
        }
        this.currentPhase = currentPhase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /***
     * This method performs a great deal of the processing related to advancing between phases.
     * @param changeProcessor The game state change processor.
     * @return The new current phase.
     *
     */
    public PhaseInterface nextPhase(GameStateChangeProcessor changeProcessor) {
        logger.finest("******** NEXTPHASE ********");
        PhaseInterface curPhase = getCurrentPhase();
        logger.finest("current phase is " + (curPhase == null ? "null" : curPhase.toString()));
        logger.finest("Checking phase is ready to complete.");

        if(curPhase != null && !curPhase.isReadyToComplete()) // should not check both
        {
            logger.warning("Phase null or not ready to complete.");
            throw new PhaseNotReadyToComplete();
        }

        logger.finest("Phase ready to complete.");
        if(curPhase == null && phases.isEmpty())
        {
            throw new NoNextPhaseAvailable();
        }
        if(curPhase != null)
        {
            logger.fine("Completing current phase");
            // completePhase is now called when players are all ready to complete.
            // the logic of this method thus will need to change.
            curPhase.completePhase();

            Iterator<GameStateChange> gameStateChangeIterator = curPhase.getGameStateChangeIterator();
            assert gameStateChangeIterator != null;
            changeProcessor.process(gameStateChangeIterator, curPhase.getProperties());

            completedPhases.add(curPhase);
        }

        if(!phases.isEmpty())
        {
            PhaseInterface newPhase = phases.remove(0);

            newPhase.startPhase();
            logger.fine("New phase is " + newPhase.toString());

            setCurrentPhase(newPhase);
            logger.finest("Value of currentPhase is " + newPhase.toString());
            logger.finest("Calling executePhase");
            newPhase.executePhase();
            Iterator gameStateChangeIterator = newPhase.getGameStateChangeIterator();
            assert gameStateChangeIterator != null;
            assert changeProcessor != null : "no GameStateChangeProcessor instance";
            changeProcessor.process(gameStateChangeIterator, newPhase.getProperties());
            logger.finest("Value of currentPhase is " + newPhase.toString());
        }
        else
        {
            // this code needs resolution
            logger.finest("Setting current phase to null");
            setCurrentPhase(null);
        }
        return getCurrentPhase();
    }

    void processPhase()
    {

    }

    /**
     * Complete the stage
     */
    public abstract void completeStage();

    /**
     * Start the stage
     * @param changeProcessor
     * @return
     */
    public abstract PhaseInterface startStage(GameStateChangeProcessor changeProcessor);

    /**
     * Initialize the stage phases
     */
    public abstract void initPhases();

    @Override
    public String toString() {
        return "Stage{" + getName() + "}";
    }

    @JsonIgnore
    public GameSituation getGameSituation() {
        return gameSituation;
    }

    @JsonIgnore
    public void setGame(GameSituation gameSituation) {
        this.gameSituation = gameSituation;
    }

    @JsonIgnore
    public boolean isReadyToComplete() {
        return currentPhase == null && phases.isEmpty();
    }

    @JsonIgnore
    List<PhaseInterface> getPhases() {
        return phases;
    }

    @JsonIgnore
    public void setPhases(List<PhaseInterface> phases) {
        this.phases = phases;
    }

    @JsonIgnore
    public List<PhaseInterface> getCompletedPhases() {
        return completedPhases;
    }

    @JsonIgnore
    public void setCompletedPhases(List<PhaseInterface> completedPhases) {
        this.completedPhases = completedPhases;
    }
}
