package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.phase.CreatureActionPhase;
import us.heptet.magewars.game.SelectionState;

/* Created by kay on 2/22/14. */
/**
 * A class representing the user interface action attack, and which
 * encapsulates the lower-level game action of attack.
 */
public class AttackUiAction extends CreatureUiAction<AttackAction> {
    private Attack attack;

    /***
     * Initialize the object. Need to properly document this constructor, especially "gameObject" parameter.
     * @param phase Creature action phase
     * @param player Player
     * @param arenaCreature Game object
     * @param attack Attack
     * @param actionConsumer Action consumer
     */
    public AttackUiAction(CreatureActionPhase phase, Player player, ArenaCreature arenaCreature, Attack attack,
                          ActionConsumer<AttackAction> actionConsumer)
    {
        super(phase, player, arenaCreature, actionConsumer);
        setCreatureActionPhase(phase);
        setAttack(attack);
        AttackAction attackAction = new AttackAction(player, arenaCreature, attack);
        setAction(attackAction);
    }

    @Override
    public void initiateAction() {
        super.initiateAction();
        SelectionState state = SelectionState.createSelectionState(
                this,
                ActionTargetImpl.creatureTarget().withRange(getAttack().getRange()), // should not hard code creature target?
                getSourceGameObject().getLocation()
        );
        setSelectionState(state);
    }

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }
}
