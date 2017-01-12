package us.heptet.magewars.game.attack;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.EqualsTester;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;
import us.heptet.magewars.game.action.AttackAction;
import us.heptet.magewars.game.exceptions.ActionException;
import us.heptet.magewars.test.GameTestHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;
import static us.heptet.magewars.game.fixtures.GameObjectsFixtures.*;

/* Created by kay on 3/29/2014. */
/**
 *
 */
public class AttackActionTest {
    private static final Logger logger = LoggerFactory.getLogger(AttackActionTest.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Mockery mockery;
    private AttackAction actionUnderTest;
    private Player mockPlayer;
    private ArenaCreature mockGameObject;
    private Attack mockAttack;
    private GameTestHelper gameTestHelper;
    private Player player1;
    private ArenaCreature arenaCreature;
    private AttackAction actionWithoutMocks;

    @BeforeMethod
    public void setUp() throws Exception {
        gameTestHelper = new GameTestHelper();
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        mockPlayer = mockery.mock(Player.class);

        // not sure we should just randomly mock GameObject!!
        mockGameObject = mockery.mock(ArenaCreature.class);
        player1 = gameTestHelper.player1();
        arenaCreature = gameTestHelper.arenaCreature(player1);

        mockAttack = mockery.mock(Attack.class);
        mockery.checking(new Expectations(){{
            atLeast(1).of(mockAttack).getNumDice();
        }});
        actionUnderTest = new AttackAction(mockPlayer, mockGameObject, mockAttack);
        actionWithoutMocks = new AttackAction(player1, arenaCreature, standardAttack());

        mockery.assertIsSatisfied();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    // should probably be more specific here and use a real exception
    @Test(expectedExceptions = ActionException.class)
    public void testExecuteActionNoTargets() throws Exception {

        actionWithoutMocks.executeAction();
    }

    @Test(expectedExceptions = ActionException.class, groups={"broken"})
    public void testExecuteActionUnattackable() throws Exception {

        AcquiredActionTargets actionTargets = new AcquiredActionTargets();

        actionTargets.add(new AcquiredActionTarget(ActionTargetImpl.creatureTarget(), arenaCreature));

        actionWithoutMocks.setAcquiredActionTargets(actionTargets);
        actionWithoutMocks.executeAction();
    }

    @Test
    public void testExecuteActionWithMocks() throws Exception {
        ArenaCreature gameElement = mockery.mock(ArenaCreature.class, "gameElement");
        AcquiredActionTarget target = new AcquiredActionTarget(ActionTargetImpl.creatureTarget(), gameElement);
        AcquiredActionTargets acquiredActionTargets = new AcquiredActionTargets(target);
        actionUnderTest.setAcquiredActionTargets(acquiredActionTargets);
        mockery.checking(new Expectations(){{
            oneOf(gameElement).getArmor();
            oneOf(gameElement).damage(0);
        }});
        actionUnderTest.executeAction();
        mockery.assertIsSatisfied();

    }

    // this needs to check them!
    @Test
    public void testExecuteActionWithMultipleTargets() throws Exception {
        ArenaCreature gameElement = mockery.mock(ArenaCreature.class, "gameElement");
        ActionTarget actionTarget = mockery.mock(ActionTarget.class);
        ActionTarget actionTarget2 = mockery.mock(ActionTarget.class, "actionTarget2");

        AcquiredActionTarget target = new AcquiredActionTarget(actionTarget, gameElement);
        AcquiredActionTargets acquiredActionTargets = new AcquiredActionTargets(target);

        AcquiredActionTarget target2 = new AcquiredActionTarget(actionTarget2);
        acquiredActionTargets.add(target2);
        actionUnderTest.setAcquiredActionTargets(acquiredActionTargets);
        mockery.checking(new Expectations(){{
            oneOf(gameElement).getArmor();
            oneOf(gameElement).damage(0);
            oneOf(actionTarget).isOptional(); will(returnValue(false));
            oneOf(actionTarget2).isOptional(); will(returnValue(true));
        }});
        actionUnderTest.executeAction();
        mockery.assertIsSatisfied();

    }

    @Test
    public void testSerializeAction() throws Exception {
        AttackAction toSerialize = new AttackAction(standardPlayer(), standardArenaCreature(), standardAttack());
        String value = objectMapper.writeValueAsString(toSerialize);
        logger.debug("Action serialized: " + toSerialize);
        logger.debug("Serialzed JSON: " + value);
    }

    @Test
    public void testSerializeDeserializeAction() throws Exception {
        AttackAction toSerialize = actionWithoutMocks;
        Map<String, Object> inject = new HashMap<>();
/*
        inject.put("players", Collections.singletonList(player1));
        inject.put("cardSet", gameTestHelper.getCardSet());
        objectMapper.setInjectableValues(new InjectableValues.Std(inject));
*/

        String value = objectMapper.writeValueAsString(toSerialize);
        logger.debug("Action serialized: " + toSerialize);
        logger.debug("Serialzed JSON: " + value);
        AttackAction readValue = gameTestHelper.getObjectMapper().readValue(value, AttackAction.class);
        assertEquals(readValue, toSerialize);
    }

    // should this be another exception?
    @Test(expectedExceptions = InvalidGameStateException.class)
    public void testCheckActionTargets() throws Exception {
        actionUnderTest.getAcquiredActionTargets().add(new AcquiredActionTarget());
        actionUnderTest.checkActionTargets();

    }


    @Test
    public void testCheckActionTargetsMandatoryTargetNoTarget() throws Exception {
        actionUnderTest.getAcquiredActionTargets().add(new AcquiredActionTarget(ActionTargetImpl.creatureTarget(false)));
        assertFalse(actionUnderTest.checkActionTargets());

    }

    @Test
    public void testEquals() throws Exception {
        new EqualsTester().addEqualityGroup(actionWithoutMocks, actionWithoutMocks).testEquals();
    }
}
