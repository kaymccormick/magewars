package us.heptet.magewars.webapp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.UserIdSource;
import org.springframework.stereotype.Service;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.webapp.client.GameService;

/* Created by kay on 5/8/2014. */
/**
 * Our implementation of the "game service"
 */
@Service("gwtGameService")
public class GameServiceImpl implements GameService {
    private Logger logger = LoggerFactory.getLogger(us.heptet.magewars.webapp.server.GameServiceImpl.class);
    @Autowired
    us.heptet.magewars.gameservice.core.service.CombinedGameService gameService;

    @Autowired
    UserIdSource userIdSource;

    @Override
    public AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent) {
        return gameService.requestAllGames(requestAllGamesEvent);
    }

    @Override
    public GameCreatedEvent createGame(CreateGameEvent createGameEvent) {
        logger.debug("in createGame (" + createGameEvent + ")");
        createGameEvent.getDetails().setCreatedByUsername(userIdSource.getUserId());
        return gameService.createGame(createGameEvent);
    }

    @Override
    public GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent) {
        return gameService.requestGameExtendedDetails(requestGameExtendedDetailsEvent);
    }

    @Override
    public GameJoinedEvent joinGame(JoinGameEvent joinGameEvent) {
        return gameService.joinGame(joinGameEvent);
    }

    @Override
    public GameStartedEvent startGame(StartGameEvent startGameEvent) {
        logger.info("In startGame ({})", startGameEvent);
        GameStartedEvent gameStartedEvent = gameService.startGame(startGameEvent);
        logger.debug("Got " + gameStartedEvent);
        if(!gameStartedEvent.isGameStarted())
        {
            logger.warn("Game id {} not started: {}", startGameEvent.getGameId(), gameStartedEvent.getMessage());
        }

        return gameStartedEvent;
    }

}
