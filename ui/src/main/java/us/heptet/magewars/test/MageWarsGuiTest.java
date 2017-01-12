package us.heptet.magewars.test;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.testfx.framework.junit.ApplicationTest;

import java.net.URL;

/* Created by kay on 3/30/2014. */
/**
 * This class is related to UI testing. It should be in another module, perhaps.
 *
 * @deprecated
 */
@Deprecated
public abstract class MageWarsGuiTest extends ApplicationTest implements /*StageSetupCallback,*/ ApplicationContextAware {
    protected ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent sceneRootNode = getRootNode(stage);
        Scene scene = new Scene(sceneRootNode, getDesiredWidth(), getDesiredHeight());
        URL cssUrl = getClass().getResource("protoapp.css");
        if(cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        stage.setX(25);
        stage.setY(25);
        stage.setScene(scene);
        stage.show();
    }

    protected abstract Parent getRootNode(Stage primaryStage);
    protected double getDesiredWidth()
    {
        return 640;
    }
    protected double getDesiredHeight()
    {
        return 480;
    }
}
