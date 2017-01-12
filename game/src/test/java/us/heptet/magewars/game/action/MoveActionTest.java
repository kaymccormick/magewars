package us.heptet.magewars.game.action;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.exceptions.ActionException;

import static us.heptet.magewars.game.fixtures.GameObjectsFixtures.standardArenaCreature;
import static us.heptet.magewars.game.fixtures.GameObjectsFixtures.standardPlayer;

/* Created by kay on 4/22/2014. */
/**
 *
 */
public class MoveActionTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test(expectedExceptions = ActionException.class)
    public void testExecuteActionNoTargets() throws Exception {
        MoveAction moveAction = new MoveAction(standardPlayer(), standardArenaCreature());
        moveAction.executeAction();
    }

    @Test(groups = {"broken"})
    public void testExecuteAction() throws Exception {
        MoveAction moveAction = new MoveAction(standardPlayer(), standardArenaCreature());
        moveAction.executeAction();


    }
}
