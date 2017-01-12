package us.heptet.magewars.domain.game;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;

import static org.testng.Assert.*;

/**
 * Created by jade on 11/09/2016.
 */
public class GameElementBaseTest {

    private GameElementBase gameElementBase;

    @BeforeMethod
    public void setUp() throws Exception {
        gameElementBase = new GameElementBase();

    }

    @Test(expectedExceptions = {InvalidGameStateException.class})
    public void testDistanceFromGameElement() throws Exception {
        gameElementBase.distanceFromGameElement(gameElementBase);
    }
}