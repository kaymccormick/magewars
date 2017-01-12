package us.heptet.magewars.gameservice.persistence.services;

import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

/* Created by kay on 3/15/14. */
/**
 * Game persistence service
 */
public interface GamePersistenceService {
    /**
     * Request all games
     * @param requestAllGamesEvent
     * @return
     */
    AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent);

    /**
     * Create game
     * @param createGameEvent
     * @return
     */
    GameCreatedEvent createGame(CreateGameEvent createGameEvent);

    /**
     * Request game details
     * @param requestGameDetailsEvent
     * @return
     */
    GameDetailsEvent requestGameDetails(RequestGameDetailsEvent requestGameDetailsEvent);

    /**
     * Request game extended details
     * @param requestGameExtendedDetailsEvent
     * @return
     */
    GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent);

    /**
     * Join event
     * @param joinGameEvent
     * @return
     */
    GameJoinedEvent joinGame(JoinGameEvent joinGameEvent);

    /**
     * Start game
     * @param startGameEvent
     * @return
     */
    GameStartedEvent startGame(StartGameEvent startGameEvent);

    /**
     * Update game player
     * @param updateGamePlayerEvent
     * @return
     */
    GamePlayerUpdatedEvent updateGamePlayer(UpdateGamePlayerEvent updateGamePlayerEvent);
}
