package us.heptet.magewars.game.stage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.exceptions.NoNextPhaseAvailable;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.exceptions.PhaseNotReadyToComplete;
import us.heptet.magewars.game.phase.CreatureActionPhaseInterface;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.phase.QuickcastPhase;
import us.heptet.magewars.game.state.GameStateChange;
import us.heptet.magewars.game.state.GameStateChangeProcessor;
import us.heptet.magewars.test.matchers.GameEventMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by jade on 15/07/2016.
 */
public class ActionStageTest {
    private static Logger logger = LoggerFactory.getLogger(ActionStageTest.class);

    private Mockery mockery;
    private ActionStage actionStage;
    private GameSituation gameSituation;
    private Arena arena;
    private GameControl gameControl;
    private GameStateChangeProcessor changeProcessor;
    LinkedList<GameStateChange> stateChanges;

    @BeforeMethod
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        gameSituation = mockery.mock(GameSituation.class);

        actionStage = new ActionStage(gameSituation);

        arena = mockery.mock(Arena.class);

        actionStage.setCreateCreatureActionPhase(() -> mockery.mock(CreatureActionPhaseInterface.class));
        actionStage.setCreateQuickcastPhase((name, isFirst) -> {
            QuickcastPhase mock = mockery.mock(QuickcastPhase.class);
            mockery.checking(new Expectations() {{
                allowing(mock).getName();
                will(returnValue(name));
                allowing(mock).isFirstPhase();
                will(returnValue(isFirst));


            }});
            return mock;
        });

        gameControl = mockery.mock(GameControl.class);
        stateChanges = new LinkedList<>();
        // we're throwing away z
        changeProcessor = (x, z) -> x.forEachRemaining((y) -> stateChanges.add(y));

        mockery.checking(new Expectations(){{
            allowing(gameSituation).getArena(); will(returnValue(arena));
            allowing(gameSituation).getInitiativeIndex();
            allowing(gameSituation).getActingPlayerIndex();
            allowing(gameSituation).setActingPlayerIndex(with(any(Integer.class)));
            allowing(gameSituation).getGameControl(); will(returnValue(gameControl));
            allowing(gameControl).setCurrentPhase(with(any(PhaseInterface.class)));
            allowing(gameSituation).getNumPlayers();
            allowing(gameControl).setCurrentStage(with(any(Stage.class)));
        }});

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitPhasesNoObjects() throws Exception {
        mockery.checking(new Expectations(){
            {
                allowing(arena).getAllObjects();
            }});
        actionStage.initPhases();
        mockery.assertIsSatisfied();

    }

    @Test
    public void testInitPhasesObjects() throws Exception {
        List<Object> allObjects = new ArrayList<>();
        ArenaCreature arenaCreature = mockery.mock(ArenaCreature.class);
        allObjects.add(arenaCreature);
        ArenaCreature arenaCreatureInActive = mockery.mock(ArenaCreature.class, "arenaCreatureInactive");
        allObjects.add(arenaCreatureInActive);
        mockery.checking(new Expectations(){
            {
                allowing(arenaCreature).isActive(); will(returnValue(true));
                allowing(arenaCreatureInActive).isActive(); will(returnValue(false));
                allowing(arena).getAllObjects(); will(returnValue(allObjects));
            }});
        actionStage.initPhases();
        logger.debug("{}", actionStage.getPhases());
        assertEquals(3, actionStage.getPhases().size());
        mockery.assertIsSatisfied();

    }

    @Test(expectedExceptions = NoNextPhaseAvailable.class)
    public void testStartStageWithoutInitPhases() throws Exception {
        PhaseInterface phaseInterface = actionStage.startStage(changeProcessor);
        mockery.assertIsSatisfied();
    }

    @Test()
    public void testStartStage() throws Exception {
        mockery.checking(new Expectations(){
            {
                allowing(arena).getAllObjects();
            }});
        actionStage.initPhases();
        PhaseInterface phaseInterface = actionStage.startStage(changeProcessor);
        assertTrue(phaseInterface instanceof QuickcastPhase);
        logger.info("{}", phaseInterface);
        mockery.assertIsSatisfied();

    }

    @Test(expectedExceptions = PhaseNotReadyToComplete.class)
    public void testNextPhaseNotReadyToComplete() throws Exception {
        mockery.checking(new Expectations(){
            {
                allowing(arena).getAllObjects();
            }});

        actionStage.initPhases();
        PhaseInterface phaseInterface = actionStage.startStage(changeProcessor);
        logger.info("{}", phaseInterface);
        phaseInterface = actionStage.nextPhase(changeProcessor);
        logger.info("{}", phaseInterface);
        mockery.assertIsSatisfied();

    }


    @Test()
    public void testNextPhase() throws Exception {
        mockery.checking(new Expectations(){
            {
                allowing(arena).getAllObjects();
                allowing(gameControl).fireEvent(with(GameEventMatcher.matchesEventType(PhaseEvent.COMPLETE_PHASE)));
            }});

        actionStage.initPhases();
        PhaseInterface phaseInterface = actionStage.startStage(changeProcessor);
        phaseInterface.setReadyToComplete(true);
        logger.info("{}", phaseInterface);
        phaseInterface = actionStage.nextPhase(changeProcessor);
        logger.info("{}", phaseInterface);
        mockery.assertIsSatisfied();

    }

    @Test
    public void testSerialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info("{}", objectMapper.writeValueAsString(actionStage));

    }

    @Test
    public void testDeserializeSerialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(actionStage);
        Stage deserStage = objectMapper.readValue(value, ActionStage.class);
        logger.info("{}", deserStage);

    }


    /*
    @Test
    public void testCompleteStage() throws Exception {

    }
*/
}