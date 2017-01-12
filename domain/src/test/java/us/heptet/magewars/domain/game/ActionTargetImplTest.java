package us.heptet.magewars.domain.game;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static us.heptet.magewars.domain.game.test.fixtures.CardsFixtures.*;

import static org.testng.Assert.*;

/**
 * Legacy tests using fixtures.
 */
public class ActionTargetImplTest {

    private ActionTargetImpl actiontarget;
    private Mockery mockery;

    @Test
    public void testZoneTarget() throws Exception {
        AcquiredActionTarget acquiredActionTarget = new AcquiredActionTarget();
        ZoneImpl zone = new ZoneImpl(0, 0);
        //GameObject gameObject = standardGameObject();
        ArenaCreature creature = standardArenaCreature();
        creature.moveObject(zone);
        ActionTarget actionTarget = ActionTargetImpl.zoneTarget();
        assertTrue(actionTarget.isValidTarget(zone, zone, acquiredActionTarget));
        assertFalse(actionTarget.isValidTarget(zone, creature, acquiredActionTarget));

    }

    @Test
    public void testCreatureTarget() throws Exception {
        ZoneImpl zone = new ZoneImpl(0, 0);
        //GameObject gameObject = standardGameObject();
        ArenaCreature creature = standardArenaCreature();
        // gotta have a location!
        creature.moveObject(zone);
        ActionTarget actionTarget = ActionTargetImpl.creatureTarget();
        AcquiredActionTarget acquiredActionTarget = new AcquiredActionTarget();
        assertFalse(actionTarget.isValidTarget(zone, zone, acquiredActionTarget));
        assertTrue(actionTarget.isValidTarget(zone, creature, acquiredActionTarget));
    }

    @BeforeMethod
    public void setUp() throws Exception {
        actiontarget = new ActionTargetImpl();
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
    }

    @Test
    public void testAdditionalActionTarget() throws Exception {
        ActionTargetImpl next = new ActionTargetImpl();
        assertEquals(actiontarget, actiontarget.additionalActionTarget(next));
        assertEquals(next, actiontarget.getNextActionTarget());
        assertEquals(actiontarget, next.getPreviousActionTarget());
        assertNull(next.getNextActionTarget());
    }

    @Test
    public void testGetSourceGameObject() throws Exception {
        SourceGameObjectFunction func = mockery.mock(SourceGameObjectFunction.class);
        AcquiredActionTarget acquiredActionTarget = mockery.mock(AcquiredActionTarget.class);
        actiontarget.setSourceGameObjectFunction(func);
        mockery.checking(new Expectations(){{
            oneOf(func).apply(acquiredActionTarget);
        }});
        actiontarget.getSourceGameObject(acquiredActionTarget);

        mockery.assertIsSatisfied();
    }

    @Test
    public void testWithRange() throws Exception {
        actiontarget.withRange(new Range(1, 2));
        assertEquals(1, actiontarget.getRange().getMinRange());
        assertEquals(2, actiontarget.getRange().getMaxRange());
    }

    @Test
    public void testIsValidTarget() throws Exception {
        ZoneImpl zone = new ZoneImpl(0, 0);
        AcquiredActionTarget acquiredActionTarget = new AcquiredActionTarget();
        assertFalse(actiontarget.isValidTarget(zone, standardGameObject(), acquiredActionTarget));
    }

    @Test
    public void testGetPreviousActionTarget() throws Exception {
        assertNull(actiontarget.getPreviousActionTarget());
    }

    @Test
    public void testCheckRangeWithSourceGameObjectFunction() throws Exception {
        Zone zone = new ZoneImpl(0, 0);

        AcquiredActionTarget acquiredActionTarget = new AcquiredActionTarget();
        SourceGameObjectFunction sourceGameObjectFunction = mockery.mock(SourceGameObjectFunction.class);
        actiontarget.setSourceGameObjectFunction(sourceGameObjectFunction);
        mockery.checking(new Expectations(){{
            oneOf(sourceGameObjectFunction).apply(acquiredActionTarget); will(returnValue(zone));
        }});
        GameObject gameElement = standardGameObject();
        gameElement.moveObject(zone);
        actiontarget.checkRange(zone, gameElement, acquiredActionTarget);
        mockery.assertIsSatisfied();
    }
}