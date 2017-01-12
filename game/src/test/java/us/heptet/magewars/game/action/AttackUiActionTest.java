package us.heptet.magewars.game.action;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Attack;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.phase.CreatureActionPhase;

/* Created by jade on 04/07/2016. */
/**
 *
 */
public class AttackUiActionTest {
    AttackUiAction attackUiAction;
    private CreatureActionPhase phase;
    private Player player;
    private ArenaCreature gameObject;
    private Attack attack;
    private ActionConsumer<AttackAction> actionConsumer;

    Mockery mockery;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);

        phase = mockery.mock(CreatureActionPhase.class);
        player = mockery.mock(Player.class);
        gameObject = mockery.mock(ArenaCreature.class);
        attack = mockery.mock(Attack.class);
        ActionConsumer<AttackAction>  actionConsumer = mockery.mock(ActionConsumer.class);
        mockery.checking(new Expectations() {{
            oneOf(attack).getNumDice();
        }});
        attackUiAction = new AttackUiAction(phase, player, gameObject, attack, actionConsumer);

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitiateAction() throws Exception {
        mockery.checking(new Expectations() {{
            oneOf(attack).getRange();
            oneOf(gameObject).getLocation();
        }});
        attackUiAction.initiateAction();
        mockery.assertIsSatisfied();
    }

}