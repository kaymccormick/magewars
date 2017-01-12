package us.heptet.magewars.ui;

import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.game.fixtures.GameObjectsFixtures;
import us.heptet.magewars.test.MageWarsGuiTest;
import us.heptet.magewars.testgroup.TestFx;
import us.heptet.magewars.ui.javafx.CreatureActionViewImpl;

import java.io.File;
import java.net.URL;

import static org.loadui.testfx.GuiTest.captureScreenshot;

/* Created by kay on 3/30/2014. */
/**
 *
 */
@Category(TestFx.class)
public class CreatureActionViewImplJUnitTest extends MageWarsGuiTest {
    private static Logger logger = LoggerFactory.getLogger(CreatureActionViewImplJUnitTest.class);
    private CreatureActionViewImpl creatureActionViewImpl = new CreatureActionViewImpl();
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreatureActionView() throws Exception {
        creatureActionViewImpl.setArenaCreature(GameObjectsFixtures.standardArenaCreature());
        creatureActionViewImpl.setActingPlayer(creatureActionViewImpl.getArenaCreature().getControllingPlayer());
        File file = captureScreenshot();
        String path = file.getAbsolutePath().replace('\\', '/');

        //URL url = new URL("file", "/", path);

        //URI uri = new URI("file", "/", path, null);
        //URL =
        logger.info("Screenshot captured to {}", new URL("file", "/", path));
        //if(Desktop.isDesktopSupported())
        //{

//            Desktop.getDesktop().open(file);
  //      }
    }

    @Override
    protected Parent getRootNode(Stage primaryStage) {
        creatureActionViewImpl = new CreatureActionViewImpl();
        return creatureActionViewImpl;
    }
}
