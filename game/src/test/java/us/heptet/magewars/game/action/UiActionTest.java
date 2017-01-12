package us.heptet.magewars.game.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.ActionConsumer;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.test.GameTestHelper;

/**
 * Created by jade on 26/09/2016.
 */
public abstract class UiActionTest<T extends UiAction, Y extends Action> implements ActionConsumer<Y> {
    private static final Logger logger = LoggerFactory.getLogger(UiActionTest.class);
    protected T uiActionUnderTest;
    protected PhaseInterface phase;
    protected Player player;
    protected GameObject gameObject;
    protected ActionConsumer actionConsumer;
    protected GameSituation gameSituation;
    GameTestHelper gameTestHelper;

    abstract T createInstance();

    @BeforeMethod
    public void setUp() throws Exception {
        gameTestHelper = createGameTestHelper();
        gameSituation = createGameSituation();
        phase = createPhase();
        player = createPlayer();
        gameObject = createGameObject();
        actionConsumer = createConsumer();


        uiActionUnderTest = createInstance();
        logger.info("object under test = {}", uiActionUnderTest);
        logger.info("class is {}", uiActionUnderTest.getClass().getName());

    }

    private GameSituation createGameSituation() {
        return gameTestHelper.createGameSituation(gameTestHelper.createGameControl());
    }

    protected abstract ActionConsumer createConsumer();

    protected abstract GameObject createGameObject();

    private GameTestHelper createGameTestHelper() {
        return new GameTestHelper();
    }

    private Player createPlayer() {
        return gameTestHelper.player1();
    }

    abstract PhaseInterface createPhase();

    @Test
    public void testInitiateAction() throws Exception {
        uiActionUnderTest.initiateAction();
    }

    @Test
    public void testSelectionCompleted() throws Exception {
        uiActionUnderTest.initiateAction();
        uiActionUnderTest.selectionCompleted();

    }

    @Override
    public void accept(Y action) {
        logger.info("accepting {}", action);
    }
}
