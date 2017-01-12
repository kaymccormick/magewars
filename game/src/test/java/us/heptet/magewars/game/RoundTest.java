package us.heptet.magewars.game;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.state.GameStateChangeProcessor;
import us.heptet.magewars.game.state.GameStateChangeProcessorImpl;
import us.heptet.magewars.test.GameTestHelper;

import static org.testng.Assert.*;

/**
 * Created by jade on 26/09/2016.
 */
public class RoundTest {

    private GameTestHelper gameTestHelper;
    private Round round;
    private GameStateChangeProcessor proc;

    @BeforeMethod
    public void setUp() throws Exception {
        gameTestHelper = new GameTestHelper();
        GameSituation gameSituation = gameTestHelper.createGameSituation(gameTestHelper.createGameControl());
        round = new Round(gameSituation);
        proc = new GameStateChangeProcessorImpl(gameSituation);
    }

    @Test
    public void testNextStage() throws Exception {
        PhaseInterface phase = round.nextStage(proc);
    }
}