package us.heptet.magewars.gameservice.core.service;

import org.springframework.stereotype.Service;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

/* Created by kay on 3/15/14. */
/**
 *
 * Interface for all related "game services" originally used by the GWT web application. It's "combined" because
 * originally the functions were to be split amongst services, causing a unnecessary proliferation of interfaces.
 *
 * These methods are more or less for managing the list of games, and not for actually playing the game.
 */
@Service
public interface CombinedGameService {
    /***
     * Request all games that exist and are "active."
     * @param requestAllGamesEvent
     * @return
     */
    AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent);

    /***
     * Create a game as specified by the event passed in.
     * @param createGameEvent The event.
     * @return An event related to the result.
     */
    GameCreatedEvent createGame(CreateGameEvent createGameEvent);

    /***
     * Request details about a game, as specified in the event.
     * @param requestGameDetailsEvent The specification for the game requested.
     * @return The details.
     */
    GameDetailsEvent requestGameDetails(RequestGameDetailsEvent requestGameDetailsEvent);

    /***
     * Request "extended details" about a game.
     * @param requestGameExtendedDetailsEvent Our specification of the game we are interested in.
     * @return The result.
     */
    GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent);

    /***
     * Process a join game request.
     * @param joinGameEvent
     * @return
     */
    GameJoinedEvent joinGame(JoinGameEvent joinGameEvent);

    /***
     * Process a start game request.
     * @param startGameEvent
     * @return
     */
    GameStartedEvent startGame(StartGameEvent startGameEvent);

    /***
     * Update information about a player in the game.
     * @param updateGamePlayerEvent
     * @return
     */
    GamePlayerUpdatedEvent updateGamePlayer(UpdateGamePlayerEvent updateGamePlayerEvent);
}
