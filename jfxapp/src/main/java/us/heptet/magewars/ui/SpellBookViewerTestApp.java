package us.heptet.magewars.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.ui.javafx.AppConfig;
import us.heptet.magewars.ui.config.UiConfig;
import us.heptet.magewars.ui.controller.PlanSpellsController;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.view.PlanSpellsView;

/* Created by kay on 3/8/14. */
/**
 * This class is hopelessly broken due to refactoring.
 */
public class SpellBookViewerTestApp extends Application {
    private static Logger logger = LoggerFactory.getLogger(SpellBookViewerTestApp.class);
    private AnnotationConfigApplicationContext ctx;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class, UiConfig.class);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // This is all broken and this whole mess of objects needs to be injected
/*
        Mage[] mages = new Mage[game.getNumPlayers()];
        for(int i = 0; i < game.getNumPlayers(); i++)
        {
            mages[i] = game.getAvailableMages().get(i == 0 ? 1 : 0);
        }
*/
        // refuctor
        //game.setPlayerMages(mages);
        //game.startGame();

        PlanSpellsController planSpellsController = ctx.getBean(PlanSpellsController.class);
        UiFactory uiFactory = ctx.getBean(UiFactory.class);

        //PlanSpellsView spellBookViewer = uiFactory.createPlanSpellsView();
        //SpellBookViewer spellBookViewer = ctx.getBean(SpellBookViewer.class);

        GameControl gameControl = ctx.getBean(GameControl.class);
        GameSituation gameSituation = gameControl.getGameSituation();

        //Player player = new Player();

        // Player array is unavailable at this time.
        planSpellsController.showForPlayer(gameSituation.getPlayer(0));

        PlanSpellsView spellBookViewer = planSpellsController.getView();
        spellBookViewer.prepare();
        Scene scene = new Scene((Region)(spellBookViewer.getControl()  ));
        ((Region)spellBookViewer.getControl()).setStyle("-fx-background-color: black;");
        scene.getStylesheets().add(getClass().getResource("protoapp.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ctx.close();
    }
}
