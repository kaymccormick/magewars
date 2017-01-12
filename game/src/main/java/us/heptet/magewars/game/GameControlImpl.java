package us.heptet.magewars.game;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.game.exceptions.*;
import us.heptet.magewars.domain.game.factory.PlayerFactory;
import us.heptet.magewars.game.events.*;
import us.heptet.magewars.game.factory.PlayerCardFactory;
import us.heptet.magewars.game.factory.RoundFactory;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.domain.game.setup.GameSetup;
import us.heptet.magewars.game.spellbook.SpellBookInitializer;
import us.heptet.magewars.game.stage.Stage;
import us.heptet.magewars.game.state.GameStateChangeProcessor;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/***
 * Implementation of {@link GameControl} interface, the primary interface through which an application obtains access to a {@link GameSituation}.
 */
public class GameControlImpl implements GameControl {
    private static final Logger logger = Logger.getLogger(GameControlImpl.class.getName());
    private GameSituation gameSituation;

    private Round currentRound;
    private Stage currentStage;
    private PhaseInterface currentPhase;

    /* Should both Game/GameSituation and GameControl have a reference to GameSetup? */
    private GameSetup gameSetup;

    private GameStateChangeProcessor gameStateChangeProcessor;
    private EventManager eventManager;
    private SpellBookInitializer spellBookInitializer;
    private boolean client;

    /** Should we have a method reference here? */
    private RoundFactory roundFactory = () -> new Round(gameSituation);

    private PlayerFactory playerFactory = GameElementFactory::createPlayer;

    private PlayerCardFactory playerCardFactory = GameElementFactory::createPlayerCard;

    static {
        logger.setLevel(Level.FINEST);
    }

    // called by test for this class, and spring
    /***
     * Create an implementation of the {@link GameControl} interface.
     * @param gameSituation The game situation.
     * @param eventManager The event manager.
     * @param spellBookInitializer Spell book initializer.
     */
    @Inject
    public GameControlImpl(GameSituation gameSituation, EventManager eventManager, SpellBookInitializer spellBookInitializer) {
        this(gameSituation, eventManager, spellBookInitializer, false);
    }

    /***
     * Create an implementation of the {@link GameControl} interface.
     * @param gameSituation The game situation.
     * @param eventManager The event manager.
     * @param spellBookInitializer Spell book initializer.
     * @param client true if the Game is a client instance
     */
    public GameControlImpl(GameSituation gameSituation, EventManager eventManager, SpellBookInitializer spellBookInitializer, boolean client) {
        logger.finest("Creating GameControlImpl(1)");
        this.client = client;
        this.eventManager = eventManager;
        this.spellBookInitializer = spellBookInitializer;
        assert gameSituation != null : "GameSituation should not be null";
        this.gameSituation = gameSituation;
        addEventHandlers();
        gameSituation.setGameControl(this);
    }

    private void addEventHandlers() {
        gameSituation.getArena().addAddObjectListener(this::arenaAddObjectListener);
        eventManager.addEventHandler(PhaseEvent.COMPLETE_PHASE, this::onCompletePhaseEvent);
    }

    private void arenaAddObjectListener(GameObject gameObject) {
        if(!isClient()) {
            fireEvent(new GameEvent(GameEvent.ADD_OBJECT, gameObject));
        }
    }

    private void onCompletePhaseEvent(PhaseEvent gameEvent) {
        logger.fine("in onCompletePhaseEvent");
        try {
            if(currentPhase == null)
            {
                throw new InvalidGameStateException("currentPhase is null");
            }
            boolean readyToComplete = currentPhase.isReadyToComplete();
            if (!readyToComplete) {
                logger.warning("phase {} says not ready to complete, may experience failure" + currentPhase.getName());
            }
            currentPhase = nextPhase();
        }
        catch(RuntimeException ex)
        {
            throw new GameControlException("Exception in onCompletePhaseEvent: " + ex.getClass().getName(), ex);
        }
    }

    @Override
    public PhaseInterface initiateRound() {
        // check to make sure current round is finished
        //

        // game must be stared
        if (!isGameInProgress()) {
            throw new GameNotInProgressException();
        }

        //2016.07.29 - IOC
        Round newRound = roundFactory.createRound();

        setCurrentRound(newRound);

        PhaseInterface newPhase = newRound.nextStage(gameStateChangeProcessor);
        setCurrentStage(newRound.getStage());
        setCurrentPhase(newPhase);
        return newPhase;
    }// used in tests

    // used in GameView
    @Override
    public void startGame(GameSetup gameSetup) {
        this.gameSetup = gameSetup;
        logger.finest("entering startGame");
        if (gameSituation.isGameInProgress()) {
            throw new GameInitializationException(gameSetup, "Game is already in progress");
        }
        // should probably enumerate reasons to start game or not and throw more detailed exceptions
        ensureGameReadyToStart();

        logger.fine("processing players in " + this.gameSetup + " into playerInfo = " + getPlayerInfo());
        // this is an assumption that we can set the number of players like this
        getPlayerInfo().setNumPlayers(this.gameSetup.getNumPlayers());
        for (int i = 0; i < getGameSetup().getNumPlayers(); i++) {
            // Shouldn't gameSituation create our player object?
            Player player = playerFactory.createPlayer(i);

            getPlayerInfo().addPlayer(player);

            Mage mage = gameSetup.getPlayerMageCard(i);

            // If two players share the same mage, than they must have
            // separate instances, which currently isn't supported. Try to replace
            // use of mage with PlayerCard<Mage> which has player attached, and make
            // Mage immutable
            mage.setPlayer(player);
            PlayerCard playerMage = playerCardFactory.createPlayerCard(player, mage);
            player.setMagePlayerCard(playerMage);
            // move mage.initializespell book somewhere else
            // because it otherwise will cause a circular dependency

            // Should this take player or PlayerCard<Mage> ?
            spellBookInitializer.initializePlayerSpellBook(player);

            String mageName = mage.getCardEnum() != null ? mage.getCardEnum().name() : null;
            SpellBook spellBook = player.getSpellBook();
            assert spellBook != null;
            us.heptet.magewars.game.events.PlayerInfo pInfo = new us.heptet.magewars.game.events.PlayerInfo(null, i, mageName,
                    spellBook.getCards().stream().map(c -> c.getCard().getCardEnum().name()).collect(Collectors.toList()));
            GameEvent gameEvent = new GameEvent(GameEvent.PLAYER_INFO, pInfo);
            fireEvent(gameEvent);
        }

        gameSituation.createPlayerMageGameObjects();
        gameSituation.setGameInProgress(true);

        // need to fire game started event?

        // notes on july 5th 2016
        //
        // we need to consider the interactions between this class and the GameSituation,
        // which is our overall model of the game. this method, startGame, does not defer
        // to game situation - it manipulates the model to represent the beginning state
        // of the game.
        //
        // we now need to fire an event to show that the game is started.
        // right now we don't have the EventManager instance to support event firing.

        initiateRound();
    }

    void ensureGameReadyToStart() {
        logger.finest("ensureGameReadyToStart");
        if (gameSetup == null) {
            throw new GameInitializationException(gameSetup, "GameSetup should not be null.");
        }
        logger.finest("querying gameSetup for 'isSetupComplete' boolean");
        if (!gameSetup.isSetupComplete()) {
            logger.warning("player setup incomplete, throwing exception");
            throw new GameInitializationException(gameSetup, "player setup incomplete.");
        }
        if (gameSetup.getNumPlayers() <= 0) {
            throw new InvalidNumberOfPlayersException(gameSetup);
        }
        if(spellBookInitializer == null){
            throw new InvalidGameStateException("spellBookInitializer should not be null");
        }
    }


    @Override
    public Round getCurrentRound() {
        return currentRound;
    }

    @Override
    public Stage getCurrentStage() {
        if (currentStage == null) {
            logger.warning("getCurrentStage called and will be returning null. its a bug.");
        }
        return currentStage;
    }

    @Override
    public PhaseInterface getCurrentPhase() {
        return currentPhase;
    }

    /* This method is one way of advancing to the next phase. However,
       the phase can also be advanced if all players indicate they are ready
       and the "completePhaseWhenPlayersAreReady" property of the Phase is true.
     */
    @Override
    public PhaseInterface nextPhase() {
        // Call main routine for next phase processing.
        if(currentStage == null)
        {
            throw new InvalidGameStateException("currentStage is null");
        }
        PhaseInterface nextPhase = currentStage.nextPhase(gameStateChangeProcessor);
        if(nextPhase == null)
        {
            nextPhase = currentRound.nextStage(gameStateChangeProcessor);
            if(nextPhase == null)
            {
                // new round?
            }
        }
        return nextPhase;
    }

    @Override
    public PhaseInterface nextStage() {
        assert gameStateChangeProcessor != null;
        return getCurrentRound().nextStage(gameStateChangeProcessor);
    }

    public PlayerInfo getPlayerInfo() {
        return gameSituation.getPlayerInfo();
    }

    @Override
    public <T extends BaseEvent> void fireEvent(T event) {
        eventManager.fireEvent(event);
    }

    @Override
    public <T extends BaseEvent> void addEventHandler(EventType<T> eventType, EventHandler<? super T> handler) {
        eventManager.addEventHandler(eventType, handler);
    }

    @Override
    public <T extends BaseEvent> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> handler) {
        eventManager.removeEventHandler(eventType, handler);
    }

    @Override
    public GameSetup getGameSetup() {
        return gameSetup;
    }

    void setCurrentRound(Round currentRound) {
        /* this doesn't make sense because "game control" implies controlling of the game */
        this.currentRound = currentRound;
    }

    @Override
    public void setCurrentStage(Stage currentStage) {
        if (currentStage != this.currentStage) {
            eventManager.fireEvent(new GameEvent(GameEvent.NEW_STAGE, currentStage));
            this.currentStage = currentStage;
        }

    }

    @Override
    public void setCurrentPhase(PhaseInterface currentPhase) {
        if (currentPhase != this.currentPhase) {
            if(currentPhase != null) {
                eventManager.fireEvent(new PhaseEvent(PhaseEvent.NEW_PHASE, currentPhase));
            }
            this.currentPhase = currentPhase;
        }
    }

    private boolean isGameInProgress() {
        return gameSituation.isGameInProgress();
    }


    @Override
    public GameStateChangeProcessor getGameStateChangeProcessor() {
        return gameStateChangeProcessor;
    }

    @Inject
    @Override
    public void setGameStateChangeProcessor(GameStateChangeProcessor gameStateChangeProcessor) {
        this.gameStateChangeProcessor = gameStateChangeProcessor;
    }

    @Override
    public void setRoundFactory(RoundFactory roundFactory) {
        this.roundFactory = roundFactory;
    }

    public void setPlayerFactory(PlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
    }

    @Inject
    public void setSpellBookInitializer(SpellBookInitializer spellBookInitializer) {
        this.spellBookInitializer = spellBookInitializer;
    }

    @Override
    public GameSituation getGameSituation() {
        return gameSituation;
    }

    public void setGameSituation(GameSituation gameSituation) {
        this.gameSituation = gameSituation;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Inject
    public void setGameSetup(GameSetup gameSetup) {
        this.gameSetup = gameSetup;
    }

    public boolean isClient() {
        return client;
    }

    @Override
    public void setClient(boolean client) {
        this.client = client;
    }
}
