package us.heptet.magewars.ui.controller;

import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.service.game.GameService;
import us.heptet.magewars.ui.view.GameListView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 7/5/2014. */
/**
 * Games list controller.
 */
public class GamesListController implements GameListView.Controller {
    private static Logger logger = Logger.getLogger(GamesListController.class.getName());
    private EventManager eventManager;
    private GameListView view;
    private GameService gameService;
    private List<GameDetails> gamesDetails;

    /**
     * Construct an instance.
     * @param eventManager
     * @param view
     * @param gameService
     */
    public GamesListController(EventManager eventManager, final GameListView view, GameService gameService) {
        this.eventManager = eventManager;
        this.eventManager.addEventHandler(GameEvent.GAME_CREATED, event -> {
            view.newGameDetails(new GameDetails(event.getGameId(), event.getGameName(), event.getUsername(),
                    2, 2));//need more infos
            logger.info("woot");
        });
        this.view = view;
        this.gameService = gameService;
        view.setController(this);
        requestAllGames();
    }

    /**
     * Request all games.
     */
    public void requestAllGames()
    {
        AllGamesEvent allGamesEvent;
        try {
            allGamesEvent = gameService.requestAllGames(new RequestAllGamesEvent());

            gamesDetails = allGamesEvent.getGamesDetails();
            view.setGamesDetails(gamesDetails);
        } catch(Exception ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
}
