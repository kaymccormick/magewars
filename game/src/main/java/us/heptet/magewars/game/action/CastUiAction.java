package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.ActionTarget;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.Spell;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.SelectionState;
import us.heptet.magewars.game.phase.PhaseInterface;

/* Created by kay on 4/2/2014. */
/**
 * A class representing the UI action of casting a spell.
 */
public class CastUiAction extends UiAction<CastAction> {
    private Spell spell;
    private ActionTarget actionTarget;

    /**
     * Construct an instance.
     * @param phase Phase
     * @param player Player
     * @param gameObject Game object
     * @param actionConsumer Action consumer
     */
    public CastUiAction(PhaseInterface phase, Player player, GameObject gameObject, ActionConsumer<CastAction> actionConsumer) {
        super(phase, player, gameObject, actionConsumer);

        CastAction action = new CastAction(player, gameObject);
        setAction(action);
    }

    @Override
    public void selectionCompleted() {
        getAction().setCard(spell);
        super.selectionCompleted();
    }

    @Override
    public void initiateAction() {
        super.initiateAction();
        actionTarget = getSpell().getActionTarget();
        assert actionTarget != null;
        SelectionState state = SelectionState.createSelectionState(
                this,
                actionTarget,
                getSourceGameObject().getLocation()
        );


        setSelectionState(state);
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }
}
