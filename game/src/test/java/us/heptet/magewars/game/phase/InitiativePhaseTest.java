package us.heptet.magewars.game.phase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.GameSituation;

import static org.testng.Assert.*;

/**
 * Created by jade on 16/07/2016.
 * This could serve as a template for a generic "Phase Test" class.
 */
public class InitiativePhaseTest {

    private Mockery mockery;
    private PhaseInterface phase;
    private GameSituation gameSituation;
    private InitiativePhase initiativePhase;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();

        gameSituation = mockery.mock(GameSituation.class);
        mockery.checking(new Expectations(){{
            ignoring(gameSituation);
        }});
        initiativePhase = new InitiativePhase(gameSituation);

        mockery.assertIsSatisfied();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testExecutePhase() throws Exception {
        initiativePhase.executePhase();
        mockery.assertIsSatisfied();
    }

    @Test(groups={"unimp"})
    public void testStartPhase() throws Exception {

    }

}