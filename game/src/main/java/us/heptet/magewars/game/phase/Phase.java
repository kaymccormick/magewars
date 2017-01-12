package us.heptet.magewars.game.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.exceptions.ActionNotReady;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.action.Action;
import us.heptet.magewars.game.state.GameStateChange;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/* Created by kay on 1/12/14. */

/**
 * Abstract base class for a game phase, a generic type with a type argument declaring the type of Action performed
 * @param <T> Type of action for the phase.
 */
public abstract class Phase<T extends Action> implements PhaseInterface<T> {
    private static transient Logger logger = Logger.getLogger(Phase.class.getName());
    private transient boolean canPassAction;
    private transient List<Boolean> playerCanPassAction = new ArrayList<>();
    // is this a proper default?
    private transient boolean readyToComplete = true; // is this a reasonable default?
    private transient boolean actionPhase ;
    private transient GameSituation gameSituation;
    private transient String name;
    private transient List<GameStateChange> gameStateChangeList = new LinkedList<>();
    private transient EventDispatcher eventDispatcher;

    protected boolean completePhaseWhenPlayersReady = true;

    protected transient Map<Player, PlayerPhaseState> playerPhaseStateMap = new HashMap<>();

    private transient Map<String, Object>[] properties;

    static {
        logger.setLevel(Level.FINEST);
    }

    transient ReadyPhaseType phaseType;

    /**
     * Default constructor.
     */
    public Phase() {
        /* For serialization */
    }
    /**
     * Super-type constructor.
     * @param gameSituation The game situation.
     */
    public Phase(GameSituation gameSituation)
    {
        this.gameSituation = gameSituation;
        eventDispatcher = gameSituation.getGameControl();
        /* Re-implement using collections? */
        properties = new HashMap[gameSituation.getNumPlayers() + 1];
        for(int i = 0; i < gameSituation.getNumPlayers(); i++)
        {
            properties[i] = new HashMap<>();
            playerCanPassAction.add(new Boolean(false));
            Player player = gameSituation.getPlayer(i);
            playerPhaseStateMap.put(player, new PlayerPhaseStateImpl(player));
        }
        properties[gameSituation.getNumPlayers()] = new HashMap<>();
    }

    @Override
    @JsonIgnore
    public ReadyPhaseType getPhaseType() {
        return phaseType;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + getName() + "}";
    }


    @Override
    public void requestCompletePhase()
    {
        eventDispatcher.fireEvent(new PhaseEvent(PhaseEvent.COMPLETE_PHASE, this, true));
    }

    @JsonIgnore
    public GameSituation getGameSituation() {
        return gameSituation;
    }

    @JsonIgnore
    public void setGame(GameSituation gameSituation) {
        this.gameSituation = gameSituation;
    }

    @Override
    public void executePhase()
    {
        logger.fine("In no-op executePhase for " + this);
    }

    @Override
    public void consumeAction(T action) {
        logger.fine("*** consumeAction");
        logger.fine("Checking action ready to consume");
        if(!action.isReadyToConsume())
        {
            logger.fine("Action not ready (to consume)");
            throw new ActionNotReady(action);
        }
        action.executeAction();
    }

    @Override
    public void passAction()
    {
        /* No-op by default */
    }

    @Override
    @JsonIgnore
    public Iterator<GameStateChange> getGameStateChangeIterator() {
        return gameStateChangeList.iterator();
    }

    @JsonIgnore
    @Override
    public <T1> void setProperty(Player player, String key, T1 value) {
        properties[player == null ? 0 : player.getPlayerIndex() + 1].put(key, value);
    }

    /**
     * Add a game state change.
     * @param gameStateChange The game state change.
     */
    public void addGameStateChange(GameStateChange gameStateChange)
    {
        gameStateChangeList.add(gameStateChange);
    }

    @JsonIgnore
    @Override
    public Map<String, Object>[] getProperties() {
        return properties;
    }

    @Override
    @JsonIgnore
    public void setPlayerReady(Player player, boolean readiness) {
        playerPhaseStateMap.get(player).setPlayerReadyToComplete(readiness);
        if(completePhaseWhenPlayersReady && isReadyToComplete())
        {
            requestCompletePhase();
        }
    }

    @Override
    public void completePhase() {
        /* do nothing by default */
    }

    @Override
    public boolean isCanPassAction() {
        return canPassAction;
    }

    public void setCanPassAction(boolean canPassAction) {
        this.canPassAction = canPassAction;
    }

    @JsonIgnore
    public List<Boolean> getPlayerCanPassAction() {
        return playerCanPassAction;
    }

    @JsonIgnore
    public void setPlayerCanPassAction(List<Boolean> playerCanPassAction) {
        this.playerCanPassAction = playerCanPassAction;
    }

    @JsonIgnore
    @Override
    public boolean isReadyToComplete() {
        return readyToComplete && (!completePhaseWhenPlayersReady || isPlayersReadyToComplete());
    }

    @JsonIgnore
    @Override
    public boolean isPlayersReadyToComplete() {
        return !playerPhaseStateMap.values().stream().map(PlayerPhaseState::getPlayerReadyToComplete).anyMatch(s -> s == null || !s);
    }

    @Override
    public void setReadyToComplete(boolean readyToComplete) {
        this.readyToComplete = readyToComplete;
    }

    @JsonIgnore
    @Override
    public boolean isActionPhase() {
        return actionPhase;
    }

    public void setActionPhase(boolean actionPhase) {
        this.actionPhase = actionPhase;
    }

    @JsonIgnore
    public void setGameSituation(GameSituation gameSituation) {
        this.gameSituation = gameSituation;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<GameStateChange> getGameStateChangeList() {
        return gameStateChangeList;
    }

    @JsonIgnore
    public void setGameStateChangeList(List<GameStateChange> gameStateChangeList) {
        this.gameStateChangeList = gameStateChangeList;
    }

    @JsonIgnore
    public void setProperties(Map<String, Object>[] properties) {
        this.properties = properties;
    }

}
