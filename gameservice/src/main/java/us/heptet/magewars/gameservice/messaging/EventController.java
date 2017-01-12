package us.heptet.magewars.gameservice.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.events.RPCEvent;
import us.heptet.magewars.gameservice.core.events.games.UpdateGamePlayerEvent;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.gameservice.security.SecurityUtils;
import us.heptet.magewars.gameservice.store.GameStore;

import javax.inject.Inject;

/**
 * Created by jade on 12/08/2016.
 */

/**
 * Handler for Spring/STOMP messaging.
 */
@Controller
public class EventController {
    CombinedGameService gameService;

    SimpMessageSendingOperations template;

    GameStore gameStore;

    private static Logger logger = LoggerFactory.getLogger(EventController.class);


    /**
     * Create an instance of EventController with its required dependencies.
     * @param gameService
     * @param template
     * @param gameStore
     */
    @Inject
    public EventController(CombinedGameService gameService, SimpMessageSendingOperations template, GameStore gameStore) {
        this.gameService = gameService;
        this.template = template;
        this.gameStore = gameStore;
    }

    /**
     * Handle an application event.
     * @param rpcEvent
     * @return
     */
    @MessageMapping("/event")
    public RPCEvent handle(@Payload RPCEvent rpcEvent)
    {
        BaseEvent baseEvent = rpcEvent.getEvent();

        logger.info("got {}", rpcEvent);

        String principalUsername = rpcEvent.getUserName();
        if(principalUsername == null) {
            principalUsername = SecurityUtils.getPrincipalUsername();
        }

        logger.debug("principal is {}", principalUsername);

        if(baseEvent.getEventType() == GameEvent.SPELLBOOK_SELECTED) {
            GameEvent gameEvent = (GameEvent) baseEvent;
            gameEvent.setUsername(principalUsername);
            if(gameService == null)
            {
                logger.error("Gameservice is null");
                return null;
            }
            gameService.updateGamePlayer(new UpdateGamePlayerEvent(gameEvent.getGameId(),
                    gameEvent.getUsername(), gameEvent.getSpellbookId()));
            GameEvent newGameEvent = new GameEvent(GameEvent.SPELLBOOK_SELECTED);
            newGameEvent.setUsername(gameEvent.getUsername());
            newGameEvent.setGameId(gameEvent.getGameId());
            newGameEvent.setMage(gameEvent.getMage());
            fire(newGameEvent);

        } else if(baseEvent.getEventType() == GameEvent.START_GAME)
        {
            GameEvent gameEvent = (GameEvent) baseEvent;
            GameEvent gameStartedEvent = new GameEvent(GameEvent.GAME_STARTED, gameEvent.getGameId(), null, null, null);
            fire(gameStartedEvent);
        } else if(baseEvent.getEventType() == PhaseEvent.READY_NEXT_PHASE)
        {
            PhaseEvent gameEvent = (PhaseEvent) baseEvent;
            int playerSlot;
            int gameId;
            if(baseEvent.getGameKey() != null)
            {
                playerSlot = baseEvent.getSourcePlayerIndex();
                gameId = (int)baseEvent.getGameKey();
            }
            else
            {
                gameId = gameEvent.getGameId();
                playerSlot = gameStore.findUserPlayerSlot(gameEvent.getGameId(), principalUsername);
            }
            GameControl gameControl = gameStore.findGameControl(gameId);
            gameControl.getCurrentPhase().setPlayerReady(gameControl.getGameSituation().getPlayer(playerSlot), true);
        }

        return new RPCEvent();
    }

    private void fire(GameEvent gameEvent) {
        template.convertAndSend("/topic/global", new RPCEvent(gameEvent));
    }

}
