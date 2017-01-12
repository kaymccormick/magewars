package us.heptet.magewars.ui.javafx;

import java.io.StringWriter;

import javafx.scene.Scene;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import us.heptet.magewars.game.FrontEndPhaseActionHandler;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.ui.*;
import us.heptet.magewars.ui.view.GameView;
import us.heptet.magewars.ui.view.PlanSpellsView;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Created by kay on 2/24/14. */

/**
 * This is a JFX Specific class.
 * should we qualify this class more properly?
 */
@Component
@Lazy
class JfxGameView extends StackPane implements GameView {
    private static Logger logger = LoggerFactory.getLogger(JfxGameView.class);
    private Controller controller;

    private FrontEndPhaseActionHandler frontEndPhaseActionHandler =
            new FrontEndPhaseActionHandler();

    private GameSituation gameSituation;

    @Autowired
    private ArenaView arenaView;

    private Button nextPhaseButton;
    private Group overlay = new Group();
    private Group normalPositioningGroup = new Group();
    private PlayerViews[] playerViewArray;
    private ScrollPane scrollPane;
    private Stage stage;

    private SimpleBooleanProperty performAnimations = new SimpleBooleanProperty(true);
    private SimpleIntegerProperty viewingPlayerIndex = new SimpleIntegerProperty();
    private SimpleDoubleProperty scaleRatio;

    List<GameErrorHandler> gameErrorListeners = new ArrayList<>();
    private Map<String, PhaseMarquee> phaseMarqueeMap = new HashMap<>();
    boolean preCreateMarquees;
    boolean doubleSizeOverlay = false;
    private Scale scale;
    private ViewManager viewManager;
    private final Button passButton;
    private PhaseMarquee curPhaseMarquee;
    private static final boolean F_SET_SCROLL_PANE_MAX_VALS_1 = false;
    private ZoomUtil zoomUtil;
    private final Region arenaViewNode;


    // we don't really want to inject game, I don't think ...

    /**
     * Create the JfxGameView
     * @param arenaView ArenaView
     */
    @Inject
    JfxGameView(ArenaView arenaView) {
        addGameErrorListener(new MyGameErrorHandler());

        DebugEventFilter<Event> debugEventFilter = new DebugEventFilter<>();
        addEventFilter(Event.ANY, debugEventFilter);

        // This tells us the game is operating for player 1 - this
        // currently does nothing, game operates in hotseat mode
        viewingPlayerIndex.set(0);

        BorderPane borderPane = new BorderPane();
        getChildren().add(borderPane);

        overlay.setPickOnBounds(false);
        overlay.getChildren().add(normalPositioningGroup);
        overlay.setManaged(false);


        preCreateMarquees = false;

        getChildren().add(overlay);
        borderPane.setId("borderpane");
        borderPane.setPadding(new Insets(0));

        VBox statusBox = new VBox(0);

        ObservableList<Node> graphics = FXCollections.observableArrayList();
        ImageView initiative = new ImageView(new Image("file:initiative.png"));
        initiative.setFitHeight(30);
        initiative.setPreserveRatio(true);
        graphics.add(initiative);
        graphics.add(new Region());
        Label player1Label = new Label("Player 1");

        Label player2Label = new Label("Player 2");
        statusBox.getChildren().addAll(player1Label, player2Label);

        List<Button> phaseButtons = new ArrayList<>();

        // Pass Action
        passButton = new Button("Pass");
        phaseButtons.add(passButton);
        passButton.setOnAction(e -> controller.onPassAction());

        Pane centerPane = new StackPane();
        centerPane.setId("centerpane");
        borderPane.setCenter(centerPane);

        Pane arenaViewPane = new StackPane();
        arenaViewPane.setId("arena-view-pane");

        scrollPane = new ScrollPane();
        scrollPane.setContent(arenaViewPane);


        assert arenaView != null;
        this.arenaView = arenaView;

        arenaViewNode = (Region) arenaView.getControl();
        arenaView.setId("arena-view");



        arenaViewPane.getChildren().add(arenaViewNode);
        scrollPane.vmaxProperty().bind(arenaViewNode.heightProperty());
        scrollPane.hmaxProperty().bind(arenaViewNode.widthProperty());


        double padding = 0;
        arenaViewPane.setPadding(new Insets(padding));

        scrollPane.setPannable(true);
        scrollPane.setVmin(0.0);
        scrollPane.setHmin(0.0);
        scrollPane.setHvalue(0);
        scrollPane.setVvalue(0);

        if(F_SET_SCROLL_PANE_MAX_VALS_1) {
            scrollPane.setHmax(arenaView.getWidth());
            scrollPane.setVmax(arenaView.getHeight());
        }

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        StackPane centerStackPane = new StackPane();
        centerStackPane.getChildren().add(scrollPane);
        centerStackPane.setId("centerstackpane");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(centerStackPane);
        anchorPane.setId("centeranchorpane");
        centerStackPane.setPrefWidth(centerPane.getWidth());
        AnchorPane.setTopAnchor(centerStackPane, 0.0);
        AnchorPane.setRightAnchor(centerStackPane, 0.0);
        AnchorPane.setBottomAnchor(centerStackPane, 0.0);
        AnchorPane.setLeftAnchor(centerStackPane, 0.0);
        centerPane.getChildren().add(anchorPane);

        // This handles hiding stats when shift is released
        addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SHIFT) {
                overlay.getChildren().remove(playerViewArray[viewingPlayerIndex.get()].getMageStatusBoard());
            }
        });

        // This is to handle scaling (zoom) hotkeys, and shift to display
        // stats (separate stats view!!)
        addEventFilter(KeyEvent.KEY_PRESSED, new KeyPressEventFilter(arenaView));
    }

    private void handlePhaseChanged(PhaseInterface newValue) {
        logger.debug("handlePhaseChanged");
        if (newValue == null) {
            logger.warn("new value is null");
            return;
        }
        final PhaseMarquee phaseMarquee;

        if (preCreateMarquees) {
            phaseMarquee = phaseMarqueeMap.get(newValue.getName());
            if (phaseMarquee == null) {
                logger.error("No marquee for phase " + newValue.getName());
                return;
            }
            phaseMarquee.setOpacity(1);
        } else {
            phaseMarquee = createPhaseMarquee(newValue.getName());
        }

        curPhaseMarquee = phaseMarquee;

        phaseMarquee.setLayoutY(0);

        phaseMarquee.getText().setOpacity(1);
        phaseMarquee.getButton().setOpacity(1);

        phaseMarquee.getButton().requestFocus();

        if (getPerformAnimations()) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), phaseMarquee);
            translateTransition.setByX(getWidth());

            FadeTransition fadeTransition = new FadeTransition(new Duration(1000), phaseMarquee.getArrow());
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            SequentialTransition sequentialTransition = new SequentialTransition();
            sequentialTransition.getChildren().addAll(fadeTransition);

            translateTransition.play();
        } else {
            double newTranslateX = phaseMarquee.getTranslateX() + getWidth();
            logger.debug("{} newTranslateX = {}", phaseMarquee, newTranslateX);
            phaseMarquee.setTranslateX(newTranslateX);
            phaseMarquee.setOpacity(1);
        }
    }

    private PhaseMarquee createPhaseMarquee(String phaseName) {
        final PhaseMarquee phaseMarquee = new PhaseMarquee(phaseName);
        phaseMarquee.setPickOnBounds(false);
        overlay.getChildren().add(phaseMarquee);
        phaseMarquee.autosize();
        phaseMarquee.setOpacity(1);
        if (doubleSizeOverlay) {
            phaseMarquee.layoutXProperty().bind(widthProperty().subtract(phaseMarquee.widthProperty()));
        } else {
            phaseMarquee.layoutXProperty().bind(phaseMarquee.widthProperty().multiply(-1.0));
        }

        nextPhaseButton = phaseMarquee.getButton();
        logger.debug("setting on action for next phase button in marquee");
        phaseMarquee.getButton().setOnAction(new ActionEventEventHandler(phaseMarquee));
        return phaseMarquee;
    }

    /*** This is called by our click on the next phase buttpn */
    private void nextPhase() {
        controller.onNextPhaseInitiated();
    }

    @Override
    public void onNewPhase(us.heptet.magewars.game.events.PhaseEvent gameEvent) {
        logger.info("in onNewPhase");
        handlePhaseChanged(gameEvent.getPhase());
        /* here we can check to see if we should display and enable the "pass action" button */
        if (gameEvent.getPhase() != null && gameEvent.getPhase().isCanPassAction()) {
            overlay.getChildren().add(passButton);
            passButton.setLayoutY(curPhaseMarquee.getLayoutBounds().getMaxY() + 30);
            passButton.setLayoutX(curPhaseMarquee.getLayoutBounds().getMaxX() - 200);
        }

    }

    @Override
    public void onAddObject(us.heptet.magewars.game.events.GameEvent gameEvent) {
        /* Not yet implemented */
        logger.warn("onAddObject unimplemented.");
    }

    @Override
    public void onChangeInitiative(us.heptet.magewars.game.events.GameEvent gameEvent) {
        /* Not yet implemented */
        logger.warn("onChangeInitiative unimplemented.");
    }

    @Override
    public void onShown() {

        zoomUtil = new ZoomUtil(arenaView.getZoneView(0, 0).getWidth(), arenaView.getZoneView(0, 0).getHeight());
        zoomUtil.setNumCols(gameSituation.getArena().getNumCols());
        zoomUtil.setNumRows(gameSituation.getArena().getNumRows());
        zoomUtil.viewportBounds.bind(scrollPane.viewportBoundsProperty());
        scaleRatio = zoomUtil.scaleRatioProperty();
        scale = new Scale();
        scale.xProperty().bind(scaleRatio);
        scale.yProperty().bind(scaleRatio);
        arenaViewNode.getTransforms().add(scale);
    }

    /* Our "main menu" is a basic start button */
    @Override
    public void createMainMenu() {
        VBox menu = new VBox(0);
        Button startButton = new Button("Start Game");
        menu.getChildren().add(startButton);
        startButton.setOnAction(actionEvent ->
            controller.onStartButtonClicked()
        );

        normalPositioningGroup.getChildren().add(menu);

    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (doubleSizeOverlay) {
            overlay.resize(getWidth() * 2, getHeight());
            overlay.setLayoutX(-1 * getWidth());
            overlay.setLayoutY(0);
            normalPositioningGroup.setLayoutX(getWidth());
        }
    }


    void addGameErrorListener(GameErrorHandler listener) {
        gameErrorListeners.add(listener);
    }

    public Button getNextPhaseButton() {
        return nextPhaseButton;
    }

    public GameSituation getGame() {
        return gameSituation;
    }

    public boolean getPerformAnimations() {
        return performAnimations.get();
    }

    public void setPerformAnimations(boolean performAnimations) {
        this.performAnimations.set(performAnimations);
    }

    ViewManager _testGetViewManager() {
        return viewManager;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void showSpellbookViewer(PlanSpellsView view) {
        overlay.getChildren().add((Node) view.getControl());
    }

    // called by protoapp
    @Override
    public void setGameSituation(GameSituation gameSituation) {

        this.gameSituation = gameSituation;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Object getControl() {
        return this;
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
        viewManager.setPhaseActionHandler(frontEndPhaseActionHandler);
    }

    private static class MyGameErrorHandler implements GameErrorHandler {
        @Override
        public boolean handle(GameError error) {
            StringWriter s = new StringWriter();
            PrintWriter p = new PrintWriter(s);
            error.getException().printStackTrace(p);

            BorderPane borderPane = new BorderPane();
            TextArea errorText = new TextArea(s.toString());
            errorText.setOnKeyPressed(event -> event.consume());
            errorText.setOnKeyTyped(Event::consume);

            borderPane.setCenter(errorText);
            Button closeButton = new Button("Close");
            borderPane.setBottom(closeButton);
            Scene errorScene = new Scene(borderPane);

            final Stage stage1 = new Stage(StageStyle.UTILITY);
            stage1.initModality(Modality.APPLICATION_MODAL);
            closeButton.setOnAction(event -> stage1.close());

            stage1.setScene(errorScene);
            stage1.showAndWait();
            return true;
        }
    }

    private class KeyPressEventFilter implements EventHandler<KeyEvent> {
        private final ArenaView arenaView;

        public KeyPressEventFilter(ArenaView arenaView) {
            this.arenaView = arenaView;
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.SHIFT) {
                overlay.getChildren().add(playerViewArray[viewingPlayerIndex.get()].getMageStatusBoard());
            }
            if ((keyEvent.getText().compareTo("=") == 0) && keyEvent.isControlDown()) {
                zoomUtil.zoomIn();
                keyEvent.consume();
            } else if ((keyEvent.getText().compareTo("-") == 0) && keyEvent.isControlDown()) {
                zoomUtil.zoomOut();
                keyEvent.consume();
            } else if (keyEvent.getText().length() > 0 && "1234".contains(keyEvent.getText())) {
                int num = Integer.parseInt(keyEvent.getText());
                zoomUtil.showNumUnits(num);
                keyEvent.consume();
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                ZoneView zone2 = arenaView.getZoneView(1, 0);
                Region zoneViewRegion2 = (Region) zone2.getControl();
                scrollPane.setViewportBounds(zoneViewRegion2.getLayoutBounds());
                scrollPane.setHvalue(zoneViewRegion2.boundsInParentProperty().get().getMinX() * scaleRatio.get());
                keyEvent.consume();

            } else if (keyEvent.getCode() == KeyCode.UP) {
                scrollPane.setVvalue(scrollPane.getVvalue() - 1);
                keyEvent.consume();
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                scrollPane.setHvalue(scrollPane.getHvalue() - 1);
                keyEvent.consume();
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                scrollPane.setVvalue(scrollPane.getVvalue() + 1);
                keyEvent.consume();
            }
        }
    }

    private class ActionEventEventHandler implements EventHandler<ActionEvent> {
        private final PhaseMarquee phaseMarquee;

        public ActionEventEventHandler(PhaseMarquee phaseMarquee) {
            this.phaseMarquee = phaseMarquee;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            logger.debug("marquee button handler, Next Phase clicked.");
            if (JfxGameView.this.getPerformAnimations()) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(500),
                        phaseMarquee);
                tt.setByX(phaseMarquee.getWidth());
                tt.setOnFinished(actionEvent1 -> {
                    logger.debug("calling into jfx game view nextPhase");
                    try {
                        JfxGameView.this.nextPhase();
                    } catch (Exception ex) {
                        logger.error("Caught exception when executing nextPhase: {}", ex.getMessage(), ex);
                        TranslateTransition tt2 = new TranslateTransition(Duration.millis(500),
                                phaseMarquee);
                        tt2.setByX(-1 * phaseMarquee.getWidth());
                        tt2.play();
                    }
                    phaseMarquee.setTranslateX(0.0);

                });
                tt.play();
            } else {
                phaseMarquee.setTranslateX(0);
                logger.debug("calling into jfx game view nextPhase");
                JfxGameView.this.nextPhase();
            }
        }
    }
}
