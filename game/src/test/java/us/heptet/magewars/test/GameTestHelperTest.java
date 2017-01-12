package us.heptet.magewars.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.GameControl;

import static org.testng.Assert.*;

/**
 * Created by jade on 03/10/2016.
 */
public class GameTestHelperTest {

    private GameTestHelper gameTestHelper;

    @BeforeMethod
    public void setUp() throws Exception {
        gameTestHelper = new GameTestHelper();
    }

    @Test
    public void testCreateGameControl() throws Exception {
        GameControl gameControl = gameTestHelper.createGameControl();
    }
}