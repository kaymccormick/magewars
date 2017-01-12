package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.gameservice.core.events.games.CreateGameEvent;
import us.heptet.magewars.gameservice.core.events.games.GameCreatedEvent;
import us.heptet.magewars.gameservice.core.events.games.GameJoinedEvent;
import us.heptet.magewars.gameservice.core.events.games.GameStartedEvent;
import us.heptet.magewars.gameservice.core.events.games.JoinGameEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.gameservice.core.events.games.StartGameEvent;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;

/* Created by kay on 5/8/2014. */
/**
 * The GWT asynchronous game service - mirrors "CombinedGameService" on the server side, I believe.
 */
public interface GameServiceAsync {
    /***
     * Request all games.
     * @param requestAllGamesEvent The request parameters/
     * @param callback The async callback.
     */
    void requestAllGames(RequestAllGamesEvent requestAllGamesEvent, AsyncCallback<AllGamesEvent> callback);

    /***
     * Create a game.
     * @param createGameEvent Request parameters.
     * @param callback Async callback.
     */
    void createGame(CreateGameEvent createGameEvent, AsyncCallback<GameCreatedEvent> callback);

    /***
     * Request game extended details.
     * @param requestGameExtendedDetailsEvent The request parameters.
     * @param callback The async callback.
     */
    void requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent, AsyncCallback<GameExtendedDetailsEvent> callback);

    /***
     * Join a game.
     * @param joinGameEvent Request details.
     * @param callback Callback.
     */
    void joinGame(JoinGameEvent joinGameEvent, AsyncCallback<GameJoinedEvent> callback);

    /***
     * Start a game.
     * @param startGameEvent Request details.
     * @param callback callback.
     */
    void startGame(StartGameEvent startGameEvent, AsyncCallback<GameStartedEvent> callback);
}
