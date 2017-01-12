package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.game.phase.CreatureActionPhase;
import us.heptet.magewars.game.phase.PhaseInterface;

/**
 * Created by jade on 26/09/2016.
 */
public abstract class CreatureUiActionTest<T extends CreatureUiAction, Y extends CreatureAction> extends UiActionTest<T, Y> {
    @Override
    protected GameObject createGameObject() {
        return gameTestHelper.arenaCreature(player);
    }

    @Override
    PhaseInterface createPhase() {
        return new CreatureActionPhase(gameSituation);
    }
}
