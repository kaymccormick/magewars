package us.heptet.magewars.ui.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.test.GameTestHelper;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.GameObjectViewFunction;
import us.heptet.magewars.ui.view.CardView;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by jade on 05/09/2016.
 */
public class ZoneViewImplTest extends ApplicationTest {
    private static Logger logger = LoggerFactory.getLogger(ZoneViewImplTest.class);

    private ZoneViewImpl zoneView;
    private ViewManager viewManager;
    private GameTestHelper gameTestHelper;

    @Before
    public void setUp() throws Exception {
    }

    @Override
    public void start(Stage stage) throws Exception {
        viewManager = new ViewManager(new CardViewFactoryImpl2(new CardImageManager(new BaseCardSet())), new CreatureActionViewImpl());
        zoneView = viewManager.getZoneView(new ZoneImpl(), new GameObjectViewFunction() {
            @Override
            public Control getGameObjectView(GameObject gameObject) {
                return null;
            }
        });
        Scene myScene = new Scene(zoneView);
        stage.setScene(myScene);
        stage.show();

        gameTestHelper = new GameTestHelper();
        for(int i = 0; i < 5; i++)
        {
            ArenaCreature arenaCreature = gameTestHelper.arenaCreature(gameTestHelper.player1());
            String cardName = arenaCreature.getPlayerCard().getCard().getCardName();
            CardView cardView = viewManager.createCardView(arenaCreature);
            Node node = (Node)cardView.getControl();
            node.layoutXProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    logger.info("[{}] x = {}", cardName, newValue);
                }
            });
            zoneView.add(arenaCreature, cardView);


            Bounds layoutBounds = node.getLayoutBounds();

            logger.info("{} {} {}", cardName, layoutBounds);

        }
    }

    @Test
    public void name() throws Exception {
        sleep(5, TimeUnit.SECONDS);
     }
}