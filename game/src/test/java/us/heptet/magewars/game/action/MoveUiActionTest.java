package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.phase.CreatureActionPhase;

/**
 * Created by jade on 26/09/2016.
 */
public class MoveUiActionTest extends CreatureUiActionTest<MoveUiAction, MoveAction> {
    @Override
    public void accept(MoveAction action) {
        super.accept(action);
    }

    @Override
    MoveUiAction createInstance() {
        return new MoveUiAction((CreatureActionPhase)phase, player, (ArenaCreature)gameObject, actionConsumer);
    }

    @Override
    protected ActionConsumer createConsumer() {
        return this;
    }


}
