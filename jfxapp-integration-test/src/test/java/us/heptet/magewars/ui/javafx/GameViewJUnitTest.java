package us.heptet.magewars.ui.javafx;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.*;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;
import us.heptet.magewars.game.Game;
import us.heptet.magewars.domain.game.setup.GameSetup;
import us.heptet.magewars.test.MageWarsGuiTest;
import us.heptet.magewars.testgroup.Broken;
import us.heptet.magewars.testgroup.TestFx;
import us.heptet.magewars.ui.GameError;
import us.heptet.magewars.ui.GameErrorHandler;
import us.heptet.magewars.ui.SpellBookViewer;
import us.heptet.magewars.ui.TestGameErrorException;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.view.GameListView;
import us.heptet.magewars.ui.view.GameView;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import static org.loadui.testfx.GuiTest.captureScreenshot;
import static org.loadui.testfx.GuiTest.find;

/* Created by kay on 3/30/2014. */
/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Category({TestFx.class})
public class GameViewJUnitTest
        extends MageWarsGuiTest {

    private boolean performAnimations;
    private GameController gameController;
    private GameSituation gameSituation;

    @Override
    public void start(Stage stage) throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        boolean installed = SLF4JBridgeHandler.isInstalled();
        assert installed;

        GameControl gameControl = ctx.getBean(GameControl.class);
        gameSituation = gameControl.getGameSituation();
        

        GameListView gameListView = null;

        final GameView gameView = ctx.getBean(JfxGameView.class);
        this.gameView = (JfxGameView)gameView;

        GameSetup gameSetup = gameControl.getGameSetup();
        gameSetup.setNumPlayers(2); // default number of players
        gameSetup.useDefaults(); // fixme use default mages for now

        gameView.setGameSituation(gameSituation);

        // our implementation of GameController
        gameController = ctx.getBean(GameController.class);
        gameController.setView(gameView);
        logger.debug("setting game control to " + gameControl);

        gameController.setPerformAnimations(performAnimations);

        Scene scene = new Scene((Parent)gameView, getDesiredWidth(), getDesiredHeight());
        URL cssUrl = getClass().getResource("protoapp.css");
        if(cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        stage.setX(25);
        stage.setY(25);
        stage.setScene(scene);
        stage.show();

        stage.setAlwaysOnTop(true);

        gameController.showMainMenu();
    }

    Game game;

    class CreatureActionNodeState {
        String idSuffix;
        boolean disabled;

        CreatureActionNodeState(String idSuffix, boolean disabled) {
            this.idSuffix = idSuffix;
            this.disabled = disabled;
        }
    }

    enum Directions
    {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    @Override
    protected double getDesiredWidth() {
        return 1500;
    }

    @Override
    protected double getDesiredHeight() {
        return 850;
    }

    private static Logger logger = LoggerFactory.getLogger(GameViewJUnitTest.class);

    private static class TestGameErrorHandler implements GameErrorHandler
    {
        List<GameError> gameErrors = new ArrayList<GameError>();
        @Override
        public boolean handle(GameError error) {
            gameErrors.add(error);
            return false;
        }

        public List<GameError> getGameErrors() {
            return gameErrors;
        }
    }

    TestGameErrorHandler handler = new TestGameErrorHandler();
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Category(Broken.class)
    public void testPlayGame() throws Exception {
        // This code should be irrelevant!

        CardSet cardSet = new BaseCardSet();
        SpellBookManager spellBookManager = new SpellBookManager(cardSet);
        BeastMaster beastMaster  = new BeastMaster();
        Warlock warlock = new Warlock();

        // removed code that initialized spell books

        logger.info("Clicking 'start game'");

        clickOn("Start Game");

        logger.info("Typing '4'");
        type(KeyCode.DIGIT4);
        if(handler.getGameErrors().size() > 0)
        {
            throw new TestGameErrorException(handler.getGameErrors().get(0).getException());
        }

        // new phase is not being handles properly.
        Button nextPhaseButton = gameView.getNextPhaseButton();
        if(nextPhaseButton == null)
        {
            throw new Exception("Unable to find next phase button.");
        }
        assert gameView.getNextPhaseButton() != null;
        logger.debug("button is {}", nextPhaseButton);
//        clickOn(gameView.getNextPhaseButton());
        long sleepMs = 3000;//250;
        long shortSleepMs = 0;//100;

        CreatureActionNodeState[] caNodeState = new CreatureActionNodeState[] {
                new CreatureActionNodeState("move-button", false),
                new CreatureActionNodeState("do-not-move-button", false),
                new CreatureActionNodeState("quick-attack-menu-button", true),
                new CreatureActionNodeState("quick-spell-menu-button", true),
                new CreatureActionNodeState("guard-button", true),
                new CreatureActionNodeState("do-nothing-button", false),
                new CreatureActionNodeState("full-attack-menu-button", true),
                new CreatureActionNodeState("full-spell-menu-button", true),
                new CreatureActionNodeState("full-special-action-button", true)
        };

        for(int turn = 0; turn < 10; turn++)
        {
            sleep(sleepMs);
            Node nextPhase = find("#Initiative-Phase-next-phase-button");
            assert !nextPhase.isDisabled();
            logger.info("Clicking next phase button during Initiative Phase");
            logger.debug("button = {}", nextPhase);
            clickOn("#Initiative-Phase-next-phase-button");
            sleep(sleepMs);

            nextPhase = find("#Reset-Phase-next-phase-button");
            if(nextPhase == null)
            {
                String msg = "unable to find rest phase next phase button.";
                logger.warn(msg);
                throw new Exception(msg);
            }
            assert !nextPhase.isDisabled();
            logger.info("Clicking next phase button during Reset Phase");
            clickOn("#Reset-Phase-next-phase-button");
            sleep(sleepMs);

            nextPhase = find("#Channel-Phase-next-phase-button");
            if(nextPhase == null)
            {
                String msg = "unable to find channel phase next phase button.";
                logger.warn(msg);
                throw new Exception(msg);
            }
            assert !nextPhase.isDisabled();
            logger.info("Clicking next phase button during Channel Phase");
            clickOn("#Channel-Phase-next-phase-button");
            sleep(sleepMs);

            nextPhase = find("#Upkeep-Phase-next-phase-button");
            assert !nextPhase.isDisabled();
            logger.info("Clicking next phase button during Upkeep Phase");
            clickOn("#Upkeep-Phase-next-phase-button");
            sleep(sleepMs);

            boolean player1Full = false;
            logger.info("Clicking card (1, 1, 1)");
//            screenshot();
            try {
                Node card1 = find("#cardview-1-1-1");
                /*
                logger.debug("card1 is {}", card1);
                if(!card1.getCard().isQuickSpell())
                    player1Full = true;
                    */
                clickOn(card1);
                sleep(shortSleepMs);

                logger.info("Clicking card (1, 2, 1)");
                Node card2 = find("#cardview-1-2-1");
                /*
                if(!card2.getCard().isQuickSpell())
                    player1Full = true;
                    */
                clickOn(card2);
                sleep(shortSleepMs);
            } catch(Exception ex)
            {

            }
/*            final List<PlanSpellsEvent> events = new ArrayList<PlanSpellsEvent>();
            gameView.addEventFilter(PlanSpellsEvent.PLAN_SPELLS, new EventHandler<PlanSpellsEvent>() {
                @Override
                public void handle(PlanSpellsEvent planSpellsEvent) {
                    events.add(planSpellsEvent);
                    logger.debug("test filtered {}", planSpellsEvent);
                }
            });
*/
            logger.info("Clicking 'OK' button");
            // we fail here when the "planning spells" dialog/view has not been shown.
            clickOn("OK");

//            assert (events.size() != 0);

            boolean fPlayerTwoNeedSelect = false;
            if(fPlayerTwoNeedSelect) {
                boolean player2full = false;
                logger.info("Clicking card (1, 1, 1)");
//            screenshot();

                Node card1 = find("#cardview-1-1-1");
                logger.debug("card1 is {}", card1);
                /*if(!card1.getCard().isQuickSpell())
                    player2full = true;*/
                clickOn(card1);
                sleep(shortSleepMs);

                logger.info("Clicking card (1, 2, 1)");
                Node card2 = find("#cardview-1-2-1");
                /*if(!card2.getCard().isQuickSpell())
                    player2full = false;*/
                sleep(shortSleepMs);

                logger.info("Clicking 'OK' button");
                clickOn("OK");
            }
            nextPhase = find("#Planning-Phase-next-phase-button");
            assert !nextPhase.isDisabled();

            logger.info("Clicking next phase during planning phase");
            clickOn("#Planning-Phase-next-phase-button");
            sleep(sleepMs);

            nextPhase = find("#Deployment-Phase-next-phase-button");
            assert !nextPhase.isDisabled();

            logger.info("Clicking next phase during deployment phase");
            clickOn("#Deployment-Phase-next-phase-button");
            sleep(sleepMs);


            Player player = gameSituation.getActingPlayer();
            String idPart = "player-" + (player.getPlayerIndex() + 1) + "-" + player.getMageArenaCreature().getPlayerCard().getCard().getCardEnum().toString().toLowerCase().replace('_', '-');


            boolean fPassQuickcast = true;
            boolean fClicked = false;
            boolean player1QuickcastUsed = false;
            if(fPassQuickcast) {
                logger.info("Right click {} quickcast marker", idPart);
                rightClickOn("#" + idPart + "-quickcast-marker");
            }
            else {
                quickcast(player);
                player1QuickcastUsed = true;
            }

//            logger.info("Typing '4'");
//            type('4');

            player = gameSituation.getActingPlayer();
            idPart = "player-" + (player.getPlayerIndex() + 1) + "-" + player.getMageArenaCreature().getPlayerCard().getCard().getCardEnum().toString().toLowerCase().replace('_', '-');

            // perform a quickcast
            boolean player2QuickcastUsed = false;
            if(fPassQuickcast) {
                logger.info("Right click {} quickcast marker", idPart);
                rightClickOn("#" + idPart + "-quickcast-marker");
            }
            else {
                quickcast(player);
                player2QuickcastUsed = true;
            }

            nextPhase = find("#First-Quickcast-Phase-next-phase-button");
            assert !nextPhase.isDisabled();
            logger.info("Clicking next phase during first quickcast phase");
            clickOn("#First-Quickcast-Phase-next-phase-button");
            //sleep(3000);

//            logger.info("clicking #zone0-0");
//            clickOn("#zone0-0");
//            logger.info("typing '1'");
//            type('1');

            player = gameSituation.getActingPlayer();
            idPart = "player-" + (player.getPlayerIndex() + 1) + "-" + player.getMageArenaCreature().getPlayerCard().getCard().getCardEnum().toString().toLowerCase().replace('_', '-');

            logger.info("Clicking {} action marker", idPart);
            clickOn("#" + idPart + "-action-marker");
            logger.info("Clicked {} action marker?", idPart);

            for(CreatureActionNodeState state:caNodeState)
            {
                String id = "creature-action-view-" + state.idSuffix;
                Node n =  null;
                try {
                    n = find("#" + id);
                } catch(Exception ex)
                {
                    StringWriter sw = new StringWriter();
                    PrintWriter p = new PrintWriter(sw);
                    ex.printStackTrace(p);
                    String s = sw.toString();
                    logger.info(sw.toString());
                    logger.error("unable to find node {}", id );
                }
                assert n != null;
                if(n.isDisable() != state.disabled)
                {
                    logger.info("Expected disable state of {} to be {}", id, state.disabled);
                    CreatureActionViewImpl cav = ((ArenaCreatureView)gameView._testGetViewManager().getGameObjectView(gameView.getGame().getActingPlayer().getMageArenaCreature()))._testGetCreatureActionView();


                    logger.debug("initialMoveTaken = {}; quickActionTaken = {}; fullActionTaken = {}", cav.getInitialMoveTaken(), cav.getQuickActionTaken(), cav.getFullActionTaken());
                }
                assert n.isDisable() == state.disabled;
            }



            // find zone base don bla bla bla
            movePlayer(player);

            logger.info("Click do nothing");
            clickOn("Do Nothing");

            nextPhase = find("#Creature-Action-Phase-next-phase-button");
            assert !nextPhase.isDisabled();
            logger.info("Clicking next phase during creature action phase");
            clickOn("#Creature-Action-Phase-next-phase-button");

            //clickOn("#zone0-0");
            //type('4');

            player = gameSituation.getActingPlayer();
            idPart = "player-" + (player.getPlayerIndex() + 1) + "-" + player.getMageArenaCreature().getPlayerCard().getCard().getCardEnum().toString().toLowerCase().replace('_', '-');

            logger.info("Clicking {} action marker", idPart);
            clickOn("#" + idPart + "-action-marker");

            movePlayer(player);

            logger.info("Click do nothing");
            clickOn("Do Nothing");

            nextPhase = find("#Creature-Action-Phase-next-phase-button");
            assert !nextPhase.isDisabled();

            logger.info("Clicking next phase during creature action phase");
            clickOn("#Creature-Action-Phase-next-phase-button");

            if(!player1QuickcastUsed)
            {
                player = gameSituation.getActingPlayer();
                idPart = "player-" + (player.getPlayerIndex() + 1) + "-" + player.getMageArenaCreature().getPlayerCard().getCard().getCardEnum().toString().toLowerCase().replace('_', '-');

                if(fPassQuickcast) {
                    logger.info("Right click {} quickcast marker", idPart);
                    rightClickOn("#" + idPart + "-quickcast-marker");
                }
                else {
                    quickcast(player);
                    player1QuickcastUsed = true;
                }
            }

            if(!player2QuickcastUsed)
            {
                player = gameSituation.getActingPlayer();
                idPart = "player-" + (player.getPlayerIndex() + 1) + "-" + player.getMageArenaCreature().getPlayerCard().getCard().getCardEnum().toString().toLowerCase().replace('_', '-');

                if(fPassQuickcast) {
                    logger.info("Right click {} quickcast marker", idPart);
                    rightClickOn("#" + idPart + "-quickcast-marker");
                }
                else {
                    quickcast(player);
                    player2QuickcastUsed = true;
                }
            }


            nextPhase = find("#Final-Quickcast-Phase-next-phase-button");
            assert !nextPhase.isDisabled();

            logger.info("Clicking next phase during final quickcast phase");
            clickOn("#Final-Quickcast-Phase-next-phase-button");


        }
        /*File file = captureScreenshot();
        logger.debug("capturing screenshot...");
        logger.info("Screenshot captured to {}", file.getAbsolutePath());

        Node node = find("Full Spell");
        assert node.isDisabled() == true;
*/
        //clickOn(, );
        //sleep(5000);
    }

    private void movePlayer(Player player) {
        logger.info("Clicking 'Move' button");
        Button moveButton = find("#creature-action-view-move-button");
        logger.debug("text of move button is {}", moveButton.getText());
        moveButton = find("Move");
        logger.debug("move button is {}", moveButton);
        assert !moveButton.isDisabled();
        clickOn(moveButton);

        Zone curLoc = player.getMageArenaCreature().getLocation();
        int row = curLoc.getRow();
        int col = curLoc.getCol();
        boolean leftA, rightA, downA, upA;

        int numCols = 4;
        int numRows = 3;
        rightA = curLoc.getCol() < (numCols - 1);
        leftA = curLoc.getCol() > 0;
        upA = curLoc.getRow() > 0;
        downA = curLoc.getRow() < (numRows - 1);

        EnumSet<Directions> directions = EnumSet.noneOf(Directions.class);
        if(upA)
            directions.add(Directions.UP);
        if(leftA)
            directions.add(Directions.LEFT);
        if(downA)
            directions.add(Directions.DOWN);
        if(rightA)
            directions.add(Directions.RIGHT);

        Random random = new Random();
        int i = random.nextInt(directions.size());
        Directions direction = (Directions)directions.toArray()[i];
        logger.debug("Direction is {}", direction);

        switch(direction) {
            case LEFT:
                col--;
                break;
            case UP:
                row--;
                break;
            case RIGHT:
                col++;
                break;
            case DOWN:
                row++;
                break;
        }


        String zoneId = "zone" + col + "-" + row;
        logger.info("Clicking {}", zoneId);
        clickOn("#" + zoneId);

        int newCol = player.getMageArenaCreature().getLocation().getCol();
        int newRow = player.getMageArenaCreature().getLocation().getRow();
        logger.debug("old zone is ({}, {})", col, row);
        logger.debug("new zone is ({}, {})", newCol, newRow);
        assert(col == newCol && row == newRow);

    }

    private void screenshot() {
        File file = captureScreenshot();
        logger.debug("capturing screenshot...");
        logger.info("Screenshot captured to {}", file.getAbsolutePath());
    }

    private void quickcast(Player player) {
        String idPart = "player-" + (player.getPlayerIndex() + 1) + "-" + player.getMageArenaCreature().getPlayerCard().getCard().getCardEnum().toString().toLowerCase().replace('_', '-');
        logger.info("Clicking {} quickcast marker", idPart);


        clickOn("#" + idPart + "-quickcast-marker");

        logger.info("Clicking {} first spell", idPart);
        boolean fClicked = false;
        try {
            clickOn("#" + idPart + "-first-spell-card-view");
            fClicked = true;
        } catch (Exception ex) {
            logger.debug("Can't find node");
        }
        if (fClicked) {
            logger.info("Clicking {}", idPart);
            Node cardView = find("#" + idPart + "-card-view");
            Transform transform = cardView.getLocalToSceneTransform();
            //transform.
            //Point3D
            //transform.impl_transform()
            //Point2D point2D = gameView.getStage().getScene()
            Point2D point = cardView.localToScene(10, 80);

            logger.debug("{}", point);
            logger.debug("{}, {}", point.getX(), point.getY());

            logger.debug("{}, {}", gameView.getStage().getX(), gameView.getStage().getY());
            double X = point.getX() + gameView.getStage().getX(), Y = point.getY() + gameView.getStage().getY();
            logger.debug("{}, {}", X, Y);
            Point2D point2 = new Point2D(X, Y);//pointP.getX() + );//cardView.localToScene(10, 80);
            logger.debug("{}", point2);

            clickOn(point2);

            //clickOn(cardView.localToScene(10, 80));
            logger.debug("{}", transform);
        }
    }

    private JfxGameView gameView;

    @Test
    public void testGameViewSetZoomLevel() throws Exception {
        //rightClickOn()
//        gameView.setZoomLevel(1, false);
    }


    protected Parent getRootNode(Stage stage) {
        if(gameView == null) {
            game = ctx.getBean(Game.class);

            UiFactory uiFactory = ctx.getBean(UiFactory.class);

            SpellBookViewer spellBookViewer = new SpellBookViewer(uiFactory);
            gameView = ctx.getBean(JfxGameView.class);
            //gameView = new GameView(stage, game, viewManager, spellBookViewer, uiFactory);
            gameView.setPerformAnimations(false);
            gameView.addGameErrorListener(handler);
            //gameView.showMainMenu();
        }
        return gameView;
    }
}
