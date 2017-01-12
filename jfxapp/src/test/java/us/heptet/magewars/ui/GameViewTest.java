package us.heptet.magewars.ui;

import javafx.scene.Parent;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import us.heptet.magewars.testgroup.TestFx;
import us.heptet.magewars.ui.javafx.AppConfig;
import us.heptet.magewars.ui.view.GameView;

/* Created by kay on 3/30/2014. */
/**
 *
 */

@ContextConfiguration(classes = AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Category(TestFx.class)
public class GameViewTest extends GuiTest implements ApplicationContextAware {
    private GameView gameView;
    private ApplicationContext ctx;


    @Override
    protected Parent getRootNode() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
