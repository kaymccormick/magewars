package us.heptet.magewars.game;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.action.UiAction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by jade on 13/07/2016.
 */
public class SelectionStateTest {
    private static Logger logger = LoggerFactory.getLogger(SelectionStateTest.class);
    private Mockery mockery;
    private SelectionState selectionState;
    private UiAction uiAction;
    private Zone zone;
    private GameElement gameElement;
    private LinkedList<AcquiredActionTarget> uiActionTargets;

    private ActionTarget zoneTarget = ActionTargetImpl.zoneTarget().withRange(new Range(0, 1));
    private ActionTarget creatureTarget = ActionTargetImpl.creatureTarget().withRange(new Range(0, 1));

    private Map<String, GameElement> gameElementMap;

    @BeforeMethod
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        uiAction = mockery.mock(UiAction.class);

        zone = us.heptet.magewars.domain.game.GameElementFactory.createZone(0, 0);

        gameElement = mockery.mock(GameElement.class);
        gameElementMap = new HashMap<>();
        gameElementMap.put("mock", gameElement);

        selectionState = SelectionState.createSelectionState(uiAction, zoneTarget, zone);

        uiActionTargets = new LinkedList<AcquiredActionTarget>();

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateSelectionState() throws Exception {
        mockery.assertIsSatisfied();
    }

    // does it make sense to test this outside of another method?
/*
    @Test(groups="broken")
    public void testIsSelectable() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameElement).distanceFromZone(zone);
        }});
        assertFalse(selectionState.isSelectable(gameElement));
    }
*/

    /*
     * Added ability to test with multiple game Element types - data provider
     * functions are kinda confusing.
     */
    @DataProvider(name = "acquireSelectionTargetDataProvider")
    public Object[][] createAcquireSelectionTargetData()
    {
        return new Object[][] {
                { zoneTarget, "mock", true, GameElementType.ZONE, 1 },
                { creatureTarget, "mock", false, GameElementType.ZONE, 1 },
                { zoneTarget, "mock", false, GameElementType.CREATURE, 1 },
                { creatureTarget, "mock", true, GameElementType.CREATURE, 1 },
                { zoneTarget, "mock", false, GameElementType.ZONE, 2 },
                { creatureTarget, "mock", false, GameElementType.ZONE, 2 },
                { zoneTarget, "mock", false, GameElementType.CREATURE, 2 },
                { creatureTarget, "mock", false, GameElementType.CREATURE, 2 }

        };
    }

    @Test(dataProvider = "acquireSelectionTargetDataProvider")
    public void testAcquireSelectionTarget(ActionTarget actionTarget, String gameElementKey, boolean isSelectable, GameElementType elementType, int distance) throws Exception {
        // when gameElementKey is "mock", targetGameElement ends up being "gameElement"
        logger.info("Testing with args: {}, {}, {}, {}, {}", actionTarget, gameElementKey, isSelectable, elementType, distance);

        GameElement targetGameElement = gameElementMap.get(gameElementKey);

        selectionState.setActionTarget(actionTarget);

        mockery.checking(new Expectations() {{
            allowing(uiAction).getActionTargets();
            will(returnValue(uiActionTargets));

            // unsure if this should be set to "targetGameElement" or "gameElement"
            allowing(gameElement).getGameElementType();
            will(returnValue(elementType));

            atLeast(1).of(gameElement).distanceFromGameElement(with(any(GameElement.class)));
            will(returnValue(distance));

            if(isSelectable) {
                oneOf(uiAction).selectionCompleted();
            }
        }});
        try {
            selectionState.acquireSelectionTarget(targetGameElement);
        } catch (Exception ex) {
            if (isSelectable) {
                throw ex;
            }
        }
        if (isSelectable) {
            assert uiActionTargets.size() == 1;
            assertEquals(uiActionTargets.get(0).getTarget(), targetGameElement);
        }
        mockery.assertIsSatisfied();

    }

    @Test
    public void testSetSelecting() throws Exception {
        SelectableRegion mock = mockery.mock(SelectableRegion.class);
        selectionState.setHoveredRegion(mock);
        selectionState.setSelecting(true);
        mockery.assertIsSatisfied();
    }

    @Test
    public void testSetSelectingToFalse() throws Exception {
        SelectableRegion mock = mockery.mock(SelectableRegion.class);
        selectionState.setHoveredRegion(mock);
        mockery.checking(new Expectations() {{
            oneOf(mock).defaultState();
        }});
        selectionState.setSelecting(false);
        mockery.assertIsSatisfied();
    }

    @Test
    public void testSetSelectingFalseToFalse() throws Exception {
        SelectableRegion mock = mockery.mock(SelectableRegion.class);
        selectionState.setSelecting(false);
        selectionState.setHoveredRegion(mock);
        selectionState.setSelecting(false);
        mockery.assertIsSatisfied();
    }

}