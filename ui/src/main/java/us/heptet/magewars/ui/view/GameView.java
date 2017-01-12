package us.heptet.magewars.ui.view;

import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.stage.Stage;

/* Created by kay on 6/17/2014. */
/**
 * Game view interface.
 */
public interface GameView extends View {
    /**
     * On add object handler.
     * @param gameEvent
     */
    void onAddObject(GameEvent gameEvent);

    /**
     * On change initiative.
     * @param gameEvent
     */
    void onChangeInitiative(GameEvent gameEvent);

    /**
     * Method called when the game view is shown (javafx-only)
     */
    void onShown();


    /**
     * Controller interface for GameView controllers.
     */
    interface Controller
    {
        /**
         * On Stage Changed
         * @param stage
         */
        void onStageChanged(Stage stage);

        /**
         * On Phase changed
         * @param phase
         */
        void onPhaseChanged(PhaseInterface phase);

        /**
         * On Start Button Clicked.
         */
        void onStartButtonClicked();

        /* This is called by (at least) the click on the next phase button in the jfx view, through an intermediaste
         * private method on JfxGameView */

        /**
         * On next phase initiated.
         */
        void onNextPhaseInitiated();

        /**
         * Set the view for this controller.
         * @param view
         */
        void setView(GameView view);

        /**
         * Get the view for this controller.
         * @return
         */

        GameView getView();

        /**
         * Handler for the ready button being clicked.
         */
        void onReadyButtonClick();

        /**
         * Pass the action.
         */
        void onPassAction();
    }

    /**
     * Create the main menu.
     */
    void createMainMenu();

    /**
     * Set the controller for this view.
     * @param controller
     */
    void setController(Controller controller);

    /**
     * Show the spell book viewer
     * @param view
     */
    void showSpellbookViewer(PlanSpellsView view);

    /**
     * Set the model (game Situation)
     * @param gameSituation
     */
    void setGameSituation(GameSituation gameSituation);

    /**
     * On new phase
     * @param gameEvent
     */
    void onNewPhase(PhaseEvent gameEvent);

}
