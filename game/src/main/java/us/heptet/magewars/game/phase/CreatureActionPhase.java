package us.heptet.magewars.game.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.action.CreatureAction;

/* Created by kay on 2/10/14. */

/**
 * A creature action phase.
 * @param <T> Type of creature action.
 */
public class CreatureActionPhase<T extends CreatureAction> extends Phase<T> implements CreatureActionPhaseInterface<T> {
    private ArenaCreature arenaCreature;

    private boolean initialMovePassed;
    private boolean initialMoveTaken;

    /**
     * Instantiate a creature action phase.
     * @param gameSituation Game situation
     */
    public CreatureActionPhase(GameSituation gameSituation)
    {
        super(gameSituation);
        setName("Creature Action Phase");
        setReadyToComplete(false);
    }

    public CreatureActionPhase() {
        /* no - op */
    }

    @Override
    public void startPhase() {
        /* no op */
    }

    @Override
    public void passAction() {
        setReadyToComplete(true);
    }

    @Override
    public void completePhase() {
        super.completePhase();
        getGameSituation().advancePlayerControl();
    }

    @JsonIgnore
    @Override
    public ArenaCreature getArenaCreature() {
        return arenaCreature;
    }

    @Override
    public void setArenaCreature(ArenaCreature arenaCreature) {
        if (arenaCreature != null) {
            setReadyToComplete(true);
        }
        this.arenaCreature = arenaCreature;
    }

    @JsonIgnore
    @Override
    public boolean getInitialMoveCompleted() {
        return getInitialMovePassed() || getInitialMoveTaken();
    }

    @JsonIgnore
    @Override
    public boolean getQuickActionEnabled() {
        return getInitialMoveCompleted();
    }

    @JsonIgnore
    @Override
    public boolean getInitialMovePassed() {
        return initialMovePassed;
    }

    @Override
    public void setInitialMovePassed(boolean initialMovePassed) {
        this.initialMovePassed = initialMovePassed;
    }

    @JsonIgnore
    @Override
    public boolean getInitialMoveTaken() {
        return initialMoveTaken;
    }

    @Override
    public void setInitialMoveTaken(boolean initialMoveTaken) {
        this.initialMoveTaken = initialMoveTaken;
    }
}
