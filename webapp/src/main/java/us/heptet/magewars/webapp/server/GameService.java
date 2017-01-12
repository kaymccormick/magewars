package us.heptet.magewars.webapp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.RPCEvent;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.events.games.GameCreatedEvent;
import us.heptet.magewars.gameservice.core.events.games.GameJoinedEvent;
import us.heptet.magewars.gameservice.core.events.games.GameStartedEvent;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.security.SecurityUtils;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

import javax.inject.Inject;
import javax.servlet.ServletContext;

/* Created by kay on 5/19/2014. */
/**
 * Server-side implementation of CombinedGameService for the primary Web Application.
 *
 * Should we call this CombinedGameServiceImpl for clarity?
 *
 * Some of this logic belongs in gameservice - such as messaging, would need to happen upon invocation of the REST methods,
 * and we currently have duplicated logic.
 */
public class GameService implements us.heptet.magewars.gameservice.core.service.CombinedGameService {
    private us.heptet.magewars.gameservice.core.service.CombinedGameService combinedGameService;
    private Logger logger = LoggerFactory.getLogger(GameService.class);

    @Inject
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    ServletContext context;

    public void setCombinedGameService(us.heptet.magewars.gameservice.core.service.CombinedGameService combinedGameService) {
        this.combinedGameService = combinedGameService;
    }

    @Override
    public AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent) {
        return combinedGameService.requestAllGames(requestAllGamesEvent);
    }

    @Override
    public GameCreatedEvent createGame(CreateGameEvent createGameEvent) {
        logger.debug("in createGame (" + createGameEvent + ")");
        GameCreatedEvent r = combinedGameService.createGame(createGameEvent);
        if(r.isGameCreated())
        {
            GameEvent gameEvent = new GameEvent(GameEvent.GAME_CREATED, r.getGameId(), createGameEvent.getDetails().getGameName(), createGameEvent.getDetails().getCreatedByUsername(), null);

            //FIXME bad topic
            messagingTemplate.convertAndSend("/topic/global", new RPCEvent(gameEvent));
        }
        return r;
    }

    @Override
    public GameDetailsEvent requestGameDetails(RequestGameDetailsEvent requestGameDetailsEvent) {
        return combinedGameService.requestGameDetails(requestGameDetailsEvent);
    }

    @Override
    public GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent) {
        requestGameExtendedDetailsEvent.setActiveUser(SecurityUtils.getPrincipalUsername());
        return combinedGameService.requestGameExtendedDetails(requestGameExtendedDetailsEvent);
    }

    // the clue to why this may not work could be related to the code path.
    // executes GwtRpcSerialier.write while the other messages do not ....
    @Override
    public GameJoinedEvent joinGame(JoinGameEvent joinGameEvent) {
        joinGameEvent.setUsername(SecurityUtils.getPrincipalUsername());

        GameJoinedEvent gameJoinedEvent = combinedGameService.joinGame(joinGameEvent);
        if(gameJoinedEvent.isSuccessfulJoin()) {
            // Messaging
            GameEvent gameEvent = new GameEvent(GameEvent.GAME_JOINED, joinGameEvent.getGameId(), null,joinGameEvent.getUsername(),gameJoinedEvent.getPlayerSlot());

            messagingTemplate.convertAndSend("/topic/global", new RPCEvent(gameEvent));

        }
    return gameJoinedEvent;
    }

    @Override
    public GameStartedEvent startGame(StartGameEvent startGameEvent) {
        logger.info("in startGame {}", startGameEvent);
        GameStartedEvent r = combinedGameService.startGame(startGameEvent);
        if(r.isGameStarted()) {
            // Messaging GameEvent.GAME_STARTED
            GameEvent gameEvent = new GameEvent(GameEvent.GAME_STARTED, startGameEvent.getGameId(), null, null, null);
            messagingTemplate.convertAndSend("/topic/global", new RPCEvent(gameEvent));
        }
        return r;
    }

    @Override
    public GamePlayerUpdatedEvent updateGamePlayer(UpdateGamePlayerEvent updateGamePlayerEvent) {
        return combinedGameService.updateGamePlayer(updateGamePlayerEvent);
    }

}

