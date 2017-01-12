package us.heptet.magewars.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.SelectionState;
import us.heptet.magewars.game.fixtures.GameObjectsFixtures;
import us.heptet.magewars.testgroup.TestFx;
import us.heptet.magewars.ui.javafx.*;

/* Created by kay on 4/4/2014. */
/**
 *
 */
@Category(TestFx.class)
public class ArenaObjectViewTest extends ApplicationTest {
    private CreatureActionView creatureActionView;
    private CardViewFactory cardViewFactory;
    private static final Logger logger = LoggerFactory.getLogger(ArenaObjectViewTest.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    public void start(Stage stage) throws Exception {

        CardSet cardSet = new BaseCardSet();

        CardImageManager cardImageManager = new CardImageManager(cardSet);
        SimpleObjectProperty<SelectionState> selectionState = new SimpleObjectProperty<>();
        cardViewFactory = new CardViewFactoryImpl2(cardImageManager);
        creatureActionView = new CreatureActionViewImpl();
        ViewManager viewManager = new ViewManager(cardViewFactory, creatureActionView);
        //CardViewManager cardViewManager = new
        PlayerCard<Card> playerCard = us.heptet.magewars.domain.game.GameElementFactory.createPlayerCard(GameObjectsFixtures.standardPlayer(),
                cardSet.getCreatureStream().findFirst().get());

        GameObject gameObject =  us.heptet.magewars.domain.game.GameElementFactory.createGameObjectBase(playerCard);

        ArenaObjectView< GameObject > arenaObjectView =
                (ArenaObjectView < GameObject >)viewManager.getGameObjectView(gameObject).getControl();

        double width = arenaObjectView.getLayoutBounds().getWidth();
        double height = arenaObjectView.getLayoutBounds().getHeight();

        Scene scene = new Scene(arenaObjectView);
        stage.setScene(scene);

        stage.show();
        logger.info("size is {}x{}", width, height);

    }

    @Test
    public void name() throws Exception {
        Thread.sleep(3000);

    }
}
