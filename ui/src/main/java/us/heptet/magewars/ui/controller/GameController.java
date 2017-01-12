package us.heptet.magewars.ui.controller;

import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.exceptions.GameException;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.PlayerInfo;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.game.phase.PlanningPhase;
import us.heptet.magewars.game.stage.Stage;
import us.heptet.magewars.ui.view.GameView;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 6/17/2014. */
/**
 * By default, this controller adds event handlers for GameEvent instances that it receives.
 *
 *
 * this appears used by GamePresenter in webapp but not by jfx client
 *   -- this is wrong - now JfxGameController extendas this class. This implements GameView.Controller
 */
@Component("gameUiController")
public class GameController implements Controller, GameView.Controller {
    private static Logger logger = Logger.getLogger(GameController.class.getName());
    protected GameView view;
    private boolean performAnimations;
    private PlayerInfo playerInfo;
    PlanSpellsController planSpellsController;

    static {
        logger.setLevel(Level.FINE);
    }

    protected GameControl gameControl;
    private String description;

    public GameController() {
        /* empty. default constructor. */
    }

    /* This constructor is only called by the test class for this class. */

    /**
     * Create an instance of {@link GameController}.
     * @param view
     * @param gameControl
     * @param planSpellsController
     */
    @Inject
    public GameController(GameView view, GameControl gameControl, PlanSpellsController planSpellsController) {
        setView(view);
        setGameControl(gameControl);
        this.playerInfo = gameControl.getGameSituation();
        this.planSpellsController = planSpellsController;
    }

    public void setPerformAnimations(boolean performAnimations) {
        this.performAnimations = performAnimations;
    }

    public boolean isPerformAnimations() {
        return performAnimations;
    }

    /**
     * Show the main menu.
     */
    public void showMainMenu() {
        view.createMainMenu();
    }

    // Right now only called by GWT presenter, but nothing calls its method that it delegates to us.
    @Override
    public void onStageChanged(Stage stage) {
        /* do nothing for now */
    }

    // Test created, not fully baked - even this function is not fully baked.
    // this is our implementation of GameView.Controller.onPhaseChanged
    @Override
    public void onPhaseChanged(PhaseInterface newValue) {
        if(newValue != null) {
            logger.info("[" + getDescription() + "] Phase changed to " + newValue.getName());
        }
        if(newValue instanceof PlanningPhase)
        {
            logger.fine("[" + getDescription() + "] initializing player queue for planning phase");
            List<Player> initiativeOrder = playerInfo.getPlayersInitiativeOrder();
            logger.fine("[" + getDescription() + "] got initiative order = " + initiativeOrder);
            Queue<Player> players = new LinkedList<>(initiativeOrder);

            logger.fine("[" + getDescription() + "] player queue is " + players);
            Player player = players.remove();
            logger.fine("[" + getDescription() + "] removed player from queue: " + player);

            logger.fine("[" + getDescription() + "] Calling into showSpellBookViewer.");
            planSpellsController.showForPlayer(player);
            view.showSpellbookViewer(planSpellsController.getView());

        }
    }

    // this does something distinctly different from its subclass!
    // this method doesn't make a lot of sense for GWT as-is, because Start Game is actually in a different view.
    @Override
    public void onStartButtonClicked() {
        /* This is "game start" event not game started event! */
        GameEvent startGameEvent = new GameEvent(GameEvent.START_GAME);
        fireEvent(startGameEvent);
    }

    private <T extends BaseEvent> void fireEvent(T gameEvent) {
        logger.fine("GameController[" + this.getDescription() + "].fireEvent(" + gameEvent.toString() + ")");
        getGameControl().fireEvent(gameEvent);
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
        this.view.setController(this);
    }

    @Override
    public GameView getView() {
        return view;
    }

    @Override
    public void onReadyButtonClick() {
        Object gameIdentity = gameControl.getGameSituation().getGameIdentity();
        Integer gameId = null;
        if(gameIdentity instanceof Integer)
        {
            gameId = (Integer) gameIdentity;
        }
        PhaseEvent gameEvent = new PhaseEvent(PhaseEvent.READY_NEXT_PHASE, gameId);
        fireEvent(gameEvent);
    }

    /* This should fire a game event like the method above? (onReadyButtonClick) */
    @Override
    public void onPassAction() {
        getGameControl().getCurrentPhase().passAction();
    }

    private void setGameControl(GameControl gameControl) {
        assert this.gameControl == null;
        this.gameControl = gameControl;
        setEventDispatcher(gameControl);
    }

    public GameControl getGameControl() {
        return gameControl;
    }

    @Override
    public void onNextPhaseInitiated() {
        PhaseInterface nextPhase = getGameControl().nextPhase();
        if(nextPhase == null)
        {
            /* Stage complete. Next stage */
            getGameControl().nextStage();
        }
    }

    public void setEventDispatcher(EventDispatcher eventDispatcher)
    {
        // should this remove handlers from previous?
        logger.fine("[" + getDescription() + "] setEventDispatcher called with " + eventDispatcher);
        /*
         * Adding event handlers here requires that the test GameControllerTest be updated
         */
        eventDispatcher.addEventHandler(PhaseEvent.NEW_PHASE, this::onNewPhase);
        eventDispatcher.addEventHandler(GameEvent.ADD_OBJECT, this::onAddObject);
        eventDispatcher.addEventHandler(GameEvent.CHANGE_INITIATIVE, this::onChangeInitiative);
        eventDispatcher.addEventHandler(GameEvent.PLAYER_INFO, this::onPlayerInfo);
        /*
         * Adding event handlers here requires that the test {@link GameControllerTest} be updated
         */

    }

    private void onPlayerInfo(GameEvent event) {
        us.heptet.magewars.game.events.PlayerInfo eventPlayerInfo = event.getPlayerInfo();
        logger.fine("eventPlayerInfo = {}" + eventPlayerInfo);

        Mage card = (Mage) gameControl.getGameSituation().getCardSet().getCard(eventPlayerInfo.getMageCardEnumString());
        Player player = gameControl.getGameSituation().getPlayer(eventPlayerInfo.getPlayerIndex());
        player.setMagePlayerCard(GameElementFactory.createPlayerCard(player, card));
        SpellBook spellBook = new SpellBook(player);
        eventPlayerInfo.getDeckCards().forEach(c -> spellBook.addCard(gameControl.getGameSituation().getCardSet().getCard(c)));
        player.setSpellBook(spellBook);
    }

    private void onChangeInitiative(GameEvent gameEvent) {
        getGameControl().getGameSituation().setInitiativeIndex(gameEvent.getInitiativeIndex());
        view.onChangeInitiative(gameEvent);
    }

    protected void onAddObject(GameEvent gameEvent)
    {
        /* This seems like code that could well be in a non-ui class. */
        try {
            GameSituation gameSituation = getGameControl().getGameSituation();

            // This seems overly verbose.
            Zone zone = gameSituation.getArena().getZone(gameEvent.getZoneCol(), gameEvent.getZoneRow());
            CardSet cardSet = gameSituation.getCardSet();
            assert cardSet != null;
            Card card = cardSet.getCard(gameEvent.getCardEnum());

            Player player = gameSituation.getPlayer(gameEvent.getOwningPlayer());
            PlayerCard<Card> playerCard = GameElementFactory.createPlayerCard(player, card);

            // this looks fucked
            GameObjectBase gameObjectBase = GameElementFactory.createGameObjectBase(playerCard);
            logger.info("[" + getDescription() + "] adding " + playerCard + " to " + zone);
            zone.addObject(gameObjectBase);
            logger.fine("[" + getDescription() + "] zone objects " + zone.getObjects());
            gameEvent.setGameObject(gameObjectBase);
        } catch(Exception ex)
        {
            logger.severe("[" + getDescription() + "] Exception in onAddObject: " + ex.toString());
            throw new GameException(ex);
        }
    }

    private void onNewPhase(PhaseEvent gameEvent) {
        logger.fine("[" + getDescription() + "] onNewPhase");

        onPhaseChanged(gameEvent.getPhase());
        getView().onNewPhase(gameEvent);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
