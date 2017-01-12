package us.heptet.magewars.game.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.action.Action;
import us.heptet.magewars.game.state.GameStateChange;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/*
 * Created by jade on 12/07/2016.
 */

/***
 * Interface implemented by all game phases. Hierarchy of the game is {@link us.heptet.magewars.game.Round} -&gt; {@link us.heptet.magewars.game.stage.Stage} -&gt; PhaseInterface.
 * @param <T> Type parameter to indicate the base type of action in use during the phase.
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=InitiativePhase.class,name="InitiativePhase")})
public interface PhaseInterface<T extends Action> extends Serializable {
    /***
     * Get the type of phase.
     * @return Type of phase.
     */
    @JsonIgnore
    ReadyPhaseType getPhaseType();

    /***
     * Get the name of the phase.
     * @return The name of the phase.
     */
    String getName();

    /***
     * Complete the phase.
     */
    void completePhase();

    /***
     * Request phase complete.
     */
    void requestCompletePhase();

    /***
     * Execute the phase.
     */
    void executePhase();

    /***
     * Get a boolean which indicates if the phase is ready to complete. For now, includes
     * readiness status of players. Probably shouldn't.
     * @return boolean, is phase ready to complete
     */
    boolean isReadyToComplete();

    /***
     * Set the boolean indicating if the phase is ready to complete.
     * @param readyToComplete boolean, true if phase is ready to complete
     */
    void setReadyToComplete(boolean readyToComplete);

    /***
     * Start the phase.
     */
    void startPhase();

    /***
     * Consume an action in the phase.
     * @param action The action instance.
     */
    void consumeAction(T action);

    /***
     * "Pass" the action.
     */
    void passAction();

    /***
     * Get boolean indicating if the phase is an action phase.
     * @return Boolean, true if it is an action phase.
     */
    boolean isActionPhase();

    /**
     * Get a boolean indicating if an action in the phase can be passed.
     * @return boolean, true if action can be passed
     */
    boolean isCanPassAction();

    /**
     * Get an iterator
     * @return iterator
     */
    @JsonIgnore
    Iterator<GameStateChange> getGameStateChangeIterator();

    /***
     * Set a property related to input processed during the phase.
     * @param player Optional player to which the input relates.
     * @param key Key for the input
     * @param value Value of the input
     * @param <V> Type of value
     */
    @JsonIgnore
    <V> void setProperty(Player player, String key, V value);

    /***
     * Get array of property maps related to the phase. Index 0 is
     * non-player-specific input, index 1 is input for player 1 (player
     * with index of 0), index 2 is input for player 2 (player with index of 1),
     * and so on.
     * @return Array of proerty maps.
     */
    @JsonIgnore
    Map<String, Object>[] getProperties();

    /***
     * Set the player ready flag for the given player.
     * @param player The player.
     * @param readiness Their readiness as a boolean: true indicates the player is ready for the stage to complete.
     */
    void setPlayerReady(Player player, boolean readiness);

    /**
     * Return a boolean indicating if all players have signalled they are ready to complete the phase.
     * @return boolean, true if players are ready to complete
     */
    boolean isPlayersReadyToComplete();
}
