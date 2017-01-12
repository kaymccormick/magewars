package us.heptet.magewars.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.bridge.SLF4JBridgeHandler;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.SpellBookManager;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.game.SelectionState;
import us.heptet.magewars.testgroup.Broken;
import us.heptet.magewars.testgroup.TestFx;
import us.heptet.magewars.ui.controller.PlanSpellsController;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.javafx.*;
import us.heptet.magewars.ui.factory.JavaFxUiFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.loadui.testfx.GuiTest.find;

/* Created by kay on 4/9/2014. */
/**
 *
 */
@Category({TestFx.class, Broken.class})
public class SpellBookViewerTest extends UiTest {
    private static Logger logger = Logger.getLogger(SpellBookViewerTest.class.getName());
    boolean doSleep = false;
    private CreatureActionView creatureActionView;
    private CardViewFactory cardViewFactory;

    static {
        logger.setLevel(Level.FINEST);
    }
    @Before
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
/*
    @Override
    public FxRobot sleep(long milliseconds) {
        if(doSleep)
            return super.sleep(milliseconds);
        return super.sleep(0);
    }
*/
    @Test
    public void testSpellBookViewer() throws Exception {
        sleep(1000);
        clickOn("#cardview-1-1-1");
        sleep(1000);
        clickOn("#cardview-1-2-1");
        sleep(1000);
        clickOn("#cardview-1-1-1");
        sleep(1000);
        clickOn("#cardview-1-2-1");
        sleep(1000);
        clickOn(">");
        sleep(1000);
        clickOn("#cardview-2-1-1");
        sleep(1000);
        clickOn("#cardview-2-2-1");
        sleep(1000);
        sleep(1000);
        sleep(1000);
        Node n = find("#spell-book-viewer");
        assert n.isVisible();
        clickOn("OK");

        assert !n.isVisible();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    protected Parent getRootNode(Stage primaryStage) {
        Parent p = null;
        try {
            CardSet cardSet = new BaseCardSet();
            CardImageManager cardImageManager = new CardImageManager(cardSet);
            //cardImageManager.getCardImage(card.getCard())
            SimpleObjectProperty<SelectionState> selectionStateSimpleObjectProperty = new
                    SimpleObjectProperty<>();
            cardViewFactory = new CardViewFactoryImpl2(cardImageManager);
            creatureActionView = new CreatureActionViewImpl();
            ViewManager viewManager = new ViewManager(cardViewFactory, creatureActionView);
            UiFactory uiFactory = new JavaFxUiFactory(cardImageManager, viewManager, viewManager);

            Player player = GameElementFactory.createPlayer(0);
            BeastMaster beastMaster = new BeastMaster();

            SpellBookManager spellBookManager = new SpellBookManager(cardSet);


            player.setMagePlayerCard(GameElementFactory.createPlayerCard(player, (Mage)beastMaster));


            SpellBookViewer spellBookViewer = new SpellBookViewer(uiFactory);
            spellBookManager.initializePlayerSpellBook(player);

            PlanSpellsController controller = new PlanSpellsController(spellBookViewer);
            spellBookViewer.setController(controller);


            controller.showForPlayer(player);
            //spellBookViewer.activatePage(0);
            /*ArrayList<SpellSlot> objects;

            objects = new ArrayList<>();
            objects.add(new SpellSlot("Spell 1"));
            objects.add(new SpellSlot("Spell 2"));


            spellBookViewer.setSpellSlotList(objects);

            sp8llBookViewer.prepare();*/
            //player,
            //beastMaster.getSpellBook());
            p =
                    (Parent) spellBookViewer.getControl();
            logger.info("getRootNode returning " + p.toString());
        } catch (Exception ex)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            logger.severe(sw.toString());
        }
        return p;
    }

    @Override
    protected double getDesiredWidth() {
        return 1800;
    }

    @Override
    protected double getDesiredHeight() {
        return 1000;
    }
}
