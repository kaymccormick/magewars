package us.heptet.magewars.domain.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 11/09/2016.
 */
public class GameObjectBaseTest {
    private static Logger logger = LoggerFactory.getLogger(GameObjectBase.class);
    @Test
    public void testToString() throws Exception {
        String str = new GameObjectBase().toString();
        logger.debug("my toString is {}", str);
        assertNotNull(str);
    }

}