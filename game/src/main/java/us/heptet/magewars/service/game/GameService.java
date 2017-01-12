package us.heptet.magewars.service.game;

import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

/* Created by kay on 6/16/2014. */
/**
 * Game service. Core "game service" definition for java (non-gwt) client side.
 * Only defined method is "requestAllGames" due to progressive implementation and need for client side
 * methods.
 *
 * The <code>CombinedGameService</code> interface is the server-side interface.
 */
public interface GameService {
    /**
     * Request all games
     * @param requestAllGamesEvent Request information
     * @return Response information
     */
    AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent);
}
