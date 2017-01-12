package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.phase.CreatureActionPhase;

/* Created by kay on 2/27/14. */
/**
 * Creature UI Action
 * @param <T> Type parameter for type extending CreatureAction which is used
 *           for underlying actions.
 */
public abstract class CreatureUiAction<T extends CreatureAction> extends UiAction<T> {
    private CreatureActionPhase creatureActionPhase;

    /**
     * Construct the instance
     * @param phase CreatureActionPhase
     * @param player Player
     * @param gameObject Game object
     * @param actionConsumer action consumer
     */
    public CreatureUiAction(CreatureActionPhase phase,
                            Player player,
                            GameObject gameObject,
                            ActionConsumer<T> actionConsumer

    ) {
        super(phase, player, gameObject, actionConsumer);
        setCreatureActionPhase(phase);
    }

    public CreatureActionPhase getCreatureActionPhase() {
        return creatureActionPhase;
    }

    public void setCreatureActionPhase(CreatureActionPhase creatureActionPhase) {
        this.creatureActionPhase = creatureActionPhase;
    }

}
