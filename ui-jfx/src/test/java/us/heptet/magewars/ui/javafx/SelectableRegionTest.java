package us.heptet.magewars.ui.javafx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.domain.game.ZoneImpl;
import us.heptet.magewars.ui.SelectableRegionBase;

/**
 * Created by jade on 05/09/2016.
 */
public class SelectableRegionTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        SelectableRegionBase<Zone> selectableZone = new SelectableRegionBase<Zone>(new ZoneImpl());
        Scene myScene = new Scene(selectableZone);
        stage.setScene(myScene);
        stage.show();
    }

    @Test
    public void name() throws Exception {
        Thread.sleep(10000);

    }
}
