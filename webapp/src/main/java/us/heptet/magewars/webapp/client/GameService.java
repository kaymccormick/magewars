package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

/* Created by kay on 5/8/2014. */
/**
 * Game service (GWT RemoteService)
 */
@RemoteServiceRelativePath("springGwtServices/gwtGameService")
public interface GameService extends RemoteService {
    /***
     * Request all games.
     * @param requestAllGamesEvent The request parameters.
     * @return The result.
     */
    AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent);

    /***
     * Create game.
     * @param createGameEvent Request parameters.
     * @return The result.
     */
    GameCreatedEvent createGame(CreateGameEvent createGameEvent);

    /***
     * Request game extended details.
     * @param requestGameExtendedDetailsEvent The request parameters.
     * @return The result.
     */
    GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent);

    /***
     * Join a game.
     * @param joinGameEvent Request details.
     * @return The result.
     */
    GameJoinedEvent joinGame(JoinGameEvent joinGameEvent);

    /***
     * Start a game.
     * @param startGameEvent Request details.
     * @return The result.
     */
    GameStartedEvent startGame(StartGameEvent startGameEvent);

}
