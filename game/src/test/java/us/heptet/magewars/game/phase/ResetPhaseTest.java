package us.heptet.magewars.game.phase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/* Created by kay on 3/29/2014. */
/**
 *
 */
public class ResetPhaseTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    // FIXME the addobject method called here is only ever called in this test. we need to determine if this is the correct method
    //
/*
    @Test(groups = {"broken"})
    public void testExecutePhase() throws Exception {
        PlayerInfo playerInfo = new PlayerInfoImpl();
        GameSetup gameSetup = new GameSetupImpl();
        GameSituation gameSituation = new Game();
        GameObjectStub gameObject = new GameObjectStub();

        gameSituation.getArena().addObject(gameObject);
        ResetPhase resetPhase = new ResetPhase(gameSituation);
        //should this fail because we haven't initiated the phase, even though
        // it's a no-op?
        resetPhase.executePhase();
        Assert.assertTrue(gameObject.isObjectReset());
    }
*/
}
