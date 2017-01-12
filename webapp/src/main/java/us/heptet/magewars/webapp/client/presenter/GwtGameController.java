package us.heptet.magewars.webapp.client.presenter;

import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.controller.PlanSpellsController;
import us.heptet.magewars.ui.view.GameView;

import javax.inject.Inject;

/**
 * Created by jade on 25/08/2016.
 */

/**
 * Our game controller for GWT
 */
public class GwtGameController extends GameController {
    /**
     * Default constructor
     */
    public GwtGameController() {
        /* no op */
    }

    /**
     * Injected constructor
     * @param view
     * @param gameControl
     * @param planSpellsController
     */
    @Inject
    public GwtGameController(GameView view, GameControl gameControl, PlanSpellsController planSpellsController) {
        super(view, gameControl, planSpellsController);
    }

    @Override
    protected void onAddObject(GameEvent gameEvent) {
        super.onAddObject(gameEvent);
        view.onAddObject(gameEvent);
    }
}
