package us.heptet.magewars.ui;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.game.action.UiAction;
import us.heptet.magewars.game.phase.CreatureActionPhase;

/**
 * Created by jade on 27/09/2016.
 */
public interface CreatureActionView extends Control{
    /***
     * Prepare the creature action view for use.
     */
    @SuppressWarnings("unchecked")
    void prepare();

    /***
     * Get the underlying arena creature.
     * @return
     */
    ArenaCreature getArenaCreature();

    /***
     * Set the underlying arena creature.
     * @param arenaCreature
     */
    void setArenaCreature(ArenaCreature arenaCreature);

    /***
     * Get the relevant Creature Action Phase.
     * @return
     */
    CreatureActionPhase getCreatureActionPhase();

    /***
     * Set the relevant Creature Action Phase..
     * @param creatureActionPhase
     */
    void setCreatureActionPhase(CreatureActionPhase creatureActionPhase);

    /**
     * Get the acting player.
     * @return
     */
    Player getActingPlayer();

    /***
     * Get the current UI Action ({@link UiAction})
     * @return
     */
    UiAction getCurrentAction();

    /**
     * Have creature? ??
     * @return
     */
    Boolean getHaveCreature();

    /**
     * Is the view prepared?
     * @return
     */
    boolean isPrepared();

    /**
     * Get the phase action handler
     * @return
     */
    PhaseActionHandler getPhaseActionHandler();

    /**
     * Set the phase action handler
     * @param phaseActionHandler
     */
    void setPhaseActionHandler(PhaseActionHandler phaseActionHandler);

    /**
     * Has the initial move action been passed?
     * @return
     */
    boolean getInitialMovePassed();

    /**
     * Has the initial move action been taken?
     * @return
     */
    boolean getInitialMoveTaken();

    /**
     * Has the quick action been taken?
     * @return
     */
    boolean getQuickActionTaken();

    /**
     * Has a full action been taken?
     * @return
     */
    boolean getFullActionTaken();

    /**
     * Is a quick action ready?
     * @return
     */
    Boolean getQuickActionReady();

}
