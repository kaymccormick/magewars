package us.heptet.magewars.game.events;

/**
 * Created by jade on 13/09/2016.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.phase.ReadyPhaseType;

/**
 * An event involving a game phase.
 */
public class PhaseEvent extends GameEvent {
    public static final EventType<PhaseEvent> ANY =
            new EventType<>(GameEvent.ANY, "PHASE");

    public static final EventType<PhaseEvent> NEW_PHASE =
            new EventType<>(PhaseEvent.ANY, "NEW_PHASE");

    public static final EventType<PhaseEvent> READY_NEXT_PHASE =
            new EventType<>(PhaseEvent.ANY, "READY_NEXT_PHASE");

    /**
     * This event is fired by Phase.completePhase to trigger processing
     * of the phase.
     */
    public static final EventType<PhaseEvent> COMPLETE_PHASE =
            new EventType<>(PhaseEvent.ANY, "COMPLETE_PHASE");


    private PhaseInterface phase;
    private ReadyPhaseType readyPhaseType;
    private String stageName;
    private String phaseName;


    /**
     * Create a "blank" PhaseEvent.
     */
    public PhaseEvent() {
        super();
    }

    /**
     * Create a PhaseEvent with the given eventType.
     * @param eventType Event type
     */
    public PhaseEvent(EventType<? extends GameEvent> eventType) {
        super(eventType);
    }

    /**
     * create a PhaseEvent with eventType and Game ID
     * @param eventType Event type
     * @param gameId game id
     */
    public PhaseEvent(EventType<? extends GameEvent> eventType, Integer gameId) {
        super(eventType);
        setGameId(gameId);
    }


    /**
     * Create a PhaseEvent with the given eventType and the given phase.
     * @param eventType Event type
     * @param phase Phase
     */
    public PhaseEvent(EventType<PhaseEvent> eventType, PhaseInterface phase) {
        super(eventType);
        setPhase(phase);
    }

    /**
     * Create a PhaseEvent with the given eventType and the given phase.
     * @param eventType Event type
     * @param phase Phase
     * @param gameInternal Is event internal to game?
     */
    public PhaseEvent(EventType<PhaseEvent> eventType, PhaseInterface phase, boolean gameInternal) {
        super(eventType);
        setPhase(phase);
        this.gameInternal = gameInternal;
    }



    @Override
    public String toString() {
        return "PhaseEvent{" + eventType.toString() + "}{" + phase + "}" + super.toString();
    }

    @JsonIgnore
    public PhaseInterface getPhase() {
        return phase;
    }

    public void setPhase(PhaseInterface phase) {
        this.phase = phase;
        if(phase != null) {
            this.readyPhaseType = phase.getPhaseType();
            this.phaseName = phase.getName();
        }
    }

    public ReadyPhaseType getReadyPhaseType() {
        return readyPhaseType;
    }

    public void setReadyPhaseType(ReadyPhaseType readyPhaseType) {
        this.readyPhaseType = readyPhaseType;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
}
