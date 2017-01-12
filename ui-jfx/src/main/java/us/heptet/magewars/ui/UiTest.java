package us.heptet.magewars.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.testfx.framework.junit.ApplicationTest;

/* Created by kay on 4/9/2014. */
/**
 * Legacy abstract class for UI Tests, used with the legacy version of TestFX. Should be eliminated from all tests.
 *
 * @deprecated
 */
@Deprecated
public abstract class UiTest extends ApplicationTest implements ApplicationContextAware {
    /**
     * Created by kay on 3/30/2014.
     */
    protected ApplicationContext ctx;
    protected int desiredHeight = 480;
    protected int desiredWidth = 640;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }


    protected abstract Parent getRootNode(Stage primaryStage);
    protected double getDesiredWidth()
    {
        return desiredWidth;
    }
    protected double getDesiredHeight()
    {
        return desiredHeight;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent sceneRootNode = getRootNode(primaryStage);
        Scene scene = new Scene(sceneRootNode, getDesiredWidth(), getDesiredHeight());

        primaryStage.setX(25);
        primaryStage.setY(25);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

