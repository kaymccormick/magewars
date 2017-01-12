package us.heptet.magewars.ui.javafx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import us.heptet.magewars.domain.game.exceptions.GameException;
import us.heptet.magewars.game.*;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.view.GameListView;
import us.heptet.magewars.ui.view.GameView;

import java.net.URL;

/* Created by kay on 1/15/14. */
/**
 * Prototype application for JFX game.
 */
public class ProtoApp extends Application {
    private static Logger logger = LoggerFactory.getLogger(ProtoApp.class);
    // handling of resolution needs to be fixed, hacked it to work
    double cardWidthInches = 2.48;
    double cardHeightInches = 3.46;
    double dpi = 72;

    private ApplicationContext ctx;
    private boolean useDecoratedStage;
    private boolean startWithGameList;
    private GameListView gameListView;

    @Override
    public void init() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void start(Stage gameStage) throws Exception {
        double width = 640;
        double height = 480;
        boolean startFullScreen = false;
        boolean startMaximized = true;
        boolean performAnimations = true;
        String cssResourceName = "protoapp.css";


        // This can be handled by a class setPlayerCard through system property
        // once we figure out how to do it reliably
        // Calling this game is confusing because now Game really
        // refers to game listings, not the game engine

        if(!useDecoratedStage) {
            gameStage.initStyle(StageStyle.UNDECORATED);
        }
        gameStage.setFullScreen(startFullScreen);
        // only supported in javafx 8
        gameStage.setMaximized(startMaximized);



        // factory method?
        // Summon the game view.
        final GameView gameView = ctx.getBean(JfxGameView.class);

        GameControl gameControl = ctx.getBean(GameControl.class);
        GameSituation gameSituation = gameControl.getGameSituation();

        // set game situation
        gameView.setGameSituation(gameSituation);

        gameControl.getGameSetup().useDefaults();

        // our implementation of GameController
        final GameController gameController = ctx.getBean(GameController.class);
        gameController.setView(gameView);

        gameController.setPerformAnimations(performAnimations);

        if(startWithGameList && gameListView == null)
        {
            throw new GameException("startWtihGameList is true and gameListView is null");
        }
        Scene scene = new Scene((Parent)(startWithGameList ? gameListView.getControl() : gameView.getControl()), width, height);

        URL cssResource = getClass().getResource(cssResourceName);
        if(cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        }
        else
        {
            logger.error("Unable to getResource {}", cssResourceName);
        }

        // this creates our "start game" button
        gameController.showMainMenu();
        
        gameStage.setScene(scene);
        gameStage.setOnShown(x -> gameView.onShown());
        gameStage.show();
        logger.info("returned from show");
    }


    /**
     * Main method for launch methods that require it.
     * @param args Program arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
