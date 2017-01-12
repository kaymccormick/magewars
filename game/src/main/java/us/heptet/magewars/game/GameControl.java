package us.heptet.magewars.game;

import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.factory.RoundFactory;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.domain.game.setup.GameSetup;
import us.heptet.magewars.game.stage.Stage;
import us.heptet.magewars.game.state.GameStateChangeProcessor;

/* Created by kay on 4/15/2014. */
/**
 * Interface for controlling a game. Primary object that can/should be constructed through dependency injection (DI) to
 * set-up a game, since it has the correct constructor injection dependencies.
 */
public interface GameControl extends EventDispatcher {
    // used in Game.startGame and GameView.nextPhase (which should be refactored to another class)
    /***
     * Initiate a new {@link Round}
     * @return The new current phase.
     */
    PhaseInterface initiateRound();
    // used in tests
    // used in GameView

    /***
     * Start the game.
     * @param gameSetup Game Setup - configuration for the game.
     */
    void startGame(GameSetup gameSetup);

    /***
     * Retrieve the current round.
     * @return The current round.
     */
    Round getCurrentRound();

    /***
     * Get the current phase.
     * @return The current phase.
     */
    PhaseInterface getCurrentPhase();

    /***
     * Get the current stage.
     * @return The current stage.
     */
    Stage getCurrentStage();

    /* This is called by startStage method of ActionStage and ReadyStage */
    /***
     * Set the current stage. Should not be called except for by the game internals.
     * @param stage The new current stage.
     */
    void setCurrentStage(Stage stage);

    /***
     * Advance to the next phase.
     * @return The new current phase.
     */
    PhaseInterface nextPhase();

    /***
     * Advance to the next stage.
     * @return The new currnet phase (not stage!)
     */
    PhaseInterface nextStage();

    /***
     * Set the current phase.
     * @param phase New, current phase.
     */
    void setCurrentPhase(PhaseInterface phase);

    /***
     * Obtain a reference to the GameStateChangeProcessor.
     * @return The {@link GameStateChangeProcessor}
     */
    GameStateChangeProcessor getGameStateChangeProcessor();

    /***
     * Set the GameStageChangeProcessor.
     * @param gameStateChangeProcessor The {@link GameStateChangeProcessor}
     */
    void setGameStateChangeProcessor(GameStateChangeProcessor gameStateChangeProcessor);

    /***
     * Set the factory instance for creating {@link Round} instances.
     * @param roundFactory A round factory instance.
     */
    void setRoundFactory(RoundFactory roundFactory);

    /***
     * Get a reference to the GameSetup used to set-up the game.
     * @return The {@link GameSetup} instance.
     */
    GameSetup getGameSetup();

    /***
     * Get a reference to the GameSituation.
     * @return The {@link GameSituation} instance.
     */
    GameSituation getGameSituation();

    /***
     * Get a reference to the EventManager.
     * @return {@link EventManager} instance.
     */
    EventManager getEventManager();

    /**
     * Set a boolean indicating if the game is a client or not.
     * @param client true if the game is a client.
     */
    void setClient(boolean client);
}
