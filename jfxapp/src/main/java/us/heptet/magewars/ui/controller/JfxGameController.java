package us.heptet.magewars.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.ui.view.GameView;

/* Created by kay on 6/17/2015. */
/**
 * Subclass game controller for Jfx Game
 */
public class JfxGameController extends GameController {
    private static Logger logger = LoggerFactory.getLogger(JfxGameController.class);

    /**
     * Construct an instance.
     * @param view
     * @param gameControl
     * @param planSpellsController
     */
    public JfxGameController(GameView view, GameControl gameControl, PlanSpellsController planSpellsController) {
        super(view, gameControl, planSpellsController);
    }

    @Override
    public void onStartButtonClicked() {
        logger.debug("onStartButtonClicked: handling start button clicked.");
        assert gameControl != null;
        gameControl.startGame(gameControl.getGameSetup());

    }

}
