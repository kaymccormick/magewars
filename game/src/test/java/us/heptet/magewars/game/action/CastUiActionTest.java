package us.heptet.magewars.game.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.phase.QuickcastPhase;

/**
 * Created by jade on 26/09/2016.
 */
public class CastUiActionTest extends UiActionTest<CastUiAction, CastAction> {
    private static final Logger logger = LoggerFactory.getLogger(CastUiActionTest.class);

    @BeforeMethod
    @Override
    public void setUp() throws Exception {
        super.setUp();
        uiActionUnderTest.setSpell(gameTestHelper.enchantmentPlayerCard(player));
    }

    @Override
    CastUiAction createInstance() {
        return new CastUiAction(phase, player, gameObject, actionConsumer);
    }

    @Override
    protected ActionConsumer createConsumer() {
        return this;
    }

    @Override
    protected GameObject createGameObject() {
        return player.getMageArenaCreature();
    }

    @Override
    PhaseInterface createPhase() {
        return new QuickcastPhase(gameSituation, "Test quickcast phase", true);
    }

}
