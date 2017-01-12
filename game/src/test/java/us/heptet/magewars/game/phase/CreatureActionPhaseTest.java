package us.heptet.magewars.game.phase;

import org.jmock.Mockery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static us.heptet.magewars.game.fixtures.GameObjectsFixtures.standardGame;

/* Created by kay on 3/29/2014. */
/**
 *
 */
public class CreatureActionPhaseTest {

    private Mockery mockery;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    // FIXME - we need players for this test
    @Test(groups={"broken"})
    public void testStartPhase() throws Exception {
        // this won't work without starting the game now
        CreatureActionPhase creatureActionPhase = new CreatureActionPhase(standardGame());
        creatureActionPhase.startPhase();
    }

    @Test(groups = {"unimp"})
    public void testCompletePhase() throws Exception {

    }
}
