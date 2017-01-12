package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.phase.PhaseInterface;

/* Created by kay on 2/14/14. */
/**
 * Represents a UI Axction of quickcast
 */
public class QuickcastUiAction extends CastUiAction {

    /**
     * Construct an instance
     * @param phase Phase
     * @param player Player
     * @param gameObject Game object
     * @param actionConsumer Action consumer
     */
    public QuickcastUiAction(PhaseInterface phase, Player player, GameObject gameObject,
                             ActionConsumer<CastAction> actionConsumer
                             )
    {
        super(phase, player, gameObject, actionConsumer);
    }

}
