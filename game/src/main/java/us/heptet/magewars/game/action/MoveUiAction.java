package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.phase.CreatureActionPhase;
import us.heptet.magewars.game.SelectionState;

/* Created by kay on 2/20/14. */
/**
 * Represents a UI Action of "Move"
 */
public class MoveUiAction extends CreatureUiAction<MoveAction> {

    /**
     * Create an instance
     * @param phase Creature Action Phase
     * @param player Player
     * @param arenaCreature Game object
     * @param actionConsumer Action consumer.
     */
    public MoveUiAction(CreatureActionPhase phase, Player player, ArenaCreature arenaCreature,
                        ActionConsumer<MoveAction> actionConsumer) {
        super(phase, player, arenaCreature, actionConsumer);
        MoveAction action = new MoveAction(player, arenaCreature);
        action.setCreature(arenaCreature);

        setAction(action);
    }

    @Override
    public void initiateAction() {
        super.initiateAction();

        GameObject arenaCreature = getSourceGameObject();
        Zone sourceZone = arenaCreature.getLocation();
        SelectionState state = SelectionState.createSelectionState(
                this,
                ActionTargetImpl.zoneTarget().withRange(arenaCreature.getMoveRange()),
                sourceZone
        );

        setSelectionState(state);

        MoveAction action = getAction();
        action.setSourceZone(sourceZone);
    }

    @Override
    public String toString()
    {
        return "MoveUiAction";
    }
}
