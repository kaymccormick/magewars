package us.heptet.magewars.game.phase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.fixtures.GameObjectsFixtures;

/**
 * Created by jade on 21/07/2016.
 * This servers as a template for a generic "Phase Test" class and is based on InitiativePhaseTest.
 */
public abstract class PhaseTest<T extends PhaseInterface> {

    protected Mockery mockery;
    protected GameSituation gameSituation;
    protected PhaseInterface thePhase;

    abstract T createPhase();

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();

        gameSituation = createGameSituation();
        mockery.checking(new Expectations(){{
            //ignoring(gameSituation);
        }});

        thePhase = createPhase();

        mockery.assertIsSatisfied();
    }

    protected GameSituation createGameSituation()
    {
        return GameObjectsFixtures.standardGame();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testExecutePhase() throws Exception {
        thePhase.executePhase();
        mockery.assertIsSatisfied();
    }

    @Test
    public void testStartPhase() throws Exception {
        thePhase.startPhase();
        mockery.assertIsSatisfied();
    }

}