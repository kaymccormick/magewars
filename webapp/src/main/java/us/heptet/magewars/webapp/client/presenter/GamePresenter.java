package us.heptet.magewars.webapp.client.presenter;

import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.stage.Stage;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.view.GameView;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 5/23/2014. */
/**
 * This implements controller business. Can this extend
 */

/* What is the "jfx" equivalent of this class? */
public class GamePresenter implements Presenter, GameView.Presenter /*, us.heptet.magewars.ui.view.GameView.Controller*/ {
    private static Logger logger = Logger.getLogger(GamesPresenter.class.getName());
    private GameSituation game;
    private GameView view;
    private us.heptet.magewars.ui.view.GameView.Controller gameController;
    private us.heptet.magewars.ui.view.GameView view1;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Create a GamePresenter instance.
     * @param view Game view.
     * @param gameController Game Controller.
     * @param eventManager Event manager.
     */
    // this doesn't make sense, really, because GameController and GamePresenter implement the same interface, GameView.controller
    public GamePresenter(GameView view, us.heptet.magewars.ui.view.GameView.Controller gameController, EventManager eventManager) {
        this.view = view;
        this.gameController = gameController;
        this.view.setPresenter(this);
    }

    /***
     *
     * Create a GamePresenter instance.
     * @param view Game view.
     * @param gameController Game Controller.
     * @param gameId The game ID.
     * @param eventManager Event manager.
     */
    public GamePresenter(GameView view, us.heptet.magewars.ui.view.GameView.Controller gameController, int gameId, EventManager eventManager) {
        this(view, gameController, eventManager);
    }

    @Override
    public void go(Container container) {
        container.clear();

        container.add(view);
    }

    @Override
    public void onNavigateAway() {
        /* No need to do anything */
    }


    @Override
    public void onStageChanged(Stage stage) {
        gameController.onStageChanged(stage);
    }

    // It does not look this will ever be called.
    @Override
    public void onPhaseChanged(PhaseInterface phase) {
        gameController.onPhaseChanged(phase);
    }

    @Override
    public void onStartButtonClicked() {
        gameController.onStartButtonClicked();
    }

    @Override
    public void onNextPhaseInitiated() {
        assert false;
    }

    @Override
    public void setView(us.heptet.magewars.ui.view.GameView view) {
        view1 = view;
    }

    @Override
    public us.heptet.magewars.ui.view.GameView getView() {
        return view1;
    }

    @Override
    public void onReadyButtonClick() {
        gameController.onReadyButtonClick();
    }

    @Override
    public void onPassAction() {

    }

    public GameSituation getGame() {
        return game;
    }

    public void setGame(GameSituation game) {
        this.game = game;
    }
}
