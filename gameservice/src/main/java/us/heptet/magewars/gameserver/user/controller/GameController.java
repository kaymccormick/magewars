package us.heptet.magewars.gameserver.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;

import java.util.Collection;

/* Created by kay on 5/6/2014. */
/**
 * Controller for Spring MVC.
 */
@Controller
public class GameController {
    private static final String GAMELIST_VIEW = "gamelist";
    @Autowired
    CombinedGameService gameService;

    /**
     * Populate games
     * @return
     */
    @ModelAttribute("games")
    public Collection<GameDetails> populateGames()
    {
        return gameService.requestAllGames(new RequestAllGamesEvent()).getGamesDetails();
    }

    /**
     * game list
     * @return
     */
    @RequestMapping({"/", "/gamelist"})
    public String list()
    {
        return GAMELIST_VIEW;
    }
}
