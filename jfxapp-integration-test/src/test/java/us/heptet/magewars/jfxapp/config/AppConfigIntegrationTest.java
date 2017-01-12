package us.heptet.magewars.jfxapp.config;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.framework.junit.ApplicationTest;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.javafx.AppConfig;

/**
 * Created by jade on 06/09/2016.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class AppConfigIntegrationTest extends ApplicationTest implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Test
    public void name() throws Exception {
    }

    @Test
    public void getGameControllerBean() throws Exception {
        GameController gameController = applicationContext.getBean(GameController.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
