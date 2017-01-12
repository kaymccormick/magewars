package us.heptet.magewars.gameserver.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import us.heptet.magewars.gameservice.core.events.games.GameDetailsEvent;
import us.heptet.magewars.gameservice.core.events.games.RequestGameDetailsEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.rest.domain.Game;

import java.util.*;

/* Created by kay on 3/17/14. */
/**
 * REST Controller for querying Games
 */
@Controller
@RequestMapping("/aggregators/games")
public class GameQueriesController {

    @Autowired
    private CombinedGameService gameService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Game> getAllGames()
    {
        List<Game> games = new ArrayList<>();
	    for(GameDetails detail : gameService.requestAllGames(new RequestAllGamesEvent()).getGamesDetails())
        {
            games.add(Game.fromGameDetails(detail));
        }
	    return games;
    }

    /***
     * Test method that returns false
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/false")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Model getFalse(Model model)
    {
        model.addAttribute("yes", false);
        return model;
    }

    /***
     * Return game information.
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Game> viewGame(@PathVariable int id) {
        GameDetailsEvent details = gameService.requestGameDetails(new RequestGameDetailsEvent(id));
        if(!details.isEntityFound())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Game game = Game.fromGameDetails(details.getGameDetails());
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

}
