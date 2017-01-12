package us.heptet.magewars.gameserver.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.gameservice.core.events.games.CreateGameEvent;
import us.heptet.magewars.gameservice.core.events.games.GameCreatedEvent;
import us.heptet.magewars.gameservice.core.events.games.GameJoinedEvent;
import us.heptet.magewars.gameservice.core.events.games.JoinGameEvent;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.rest.domain.Game;

import javax.servlet.ServletContext;

/* Created by kay on 3/17/14. */
/**
 * REST controller for Game commands.
 */
@Controller
@RequestMapping("/aggregators/games")
public class GameCommandsController {
    private static Logger logger = LoggerFactory.getLogger(GameCommandsController.class);

    @Autowired
    private CombinedGameService gameService;

    @Autowired
    ServletContext context;

    // this is a bit weird! is this used?
    // it might be useful to do some error checking on the input, or catch exceptions
    // the user name must match an existing user - right now it just blows up with an assert.
    // we also need some security here.

    /***
     * Create a game.
     * @param game The game to create.
     * @param builder Injected UriComponentsBuilder for composing the url of the created game.
     * @return Response entity containing the newly created game.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Game> createGame(@RequestBody Game game, UriComponentsBuilder builder)
    {
        GameCreatedEvent gameCreatedEvent;
        try {
            gameCreatedEvent = gameService.createGame(new CreateGameEvent(game.toGameDetails()));
        } catch(Exception ex)
        {
            logger.error("Unable to create game: {}", ex.getMessage(), ex);
            logger.error("{}", ex.toString());
            // This used to return a gameCreatedEvent with boolean false

            return new ResponseEntity<>(new Game(), HttpStatus.BAD_REQUEST);

        }
        if(!gameCreatedEvent.isGameCreated()) {
            return new ResponseEntity<>(new Game(), HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/aggregators/games/{id}").buildAndExpand(Integer.toString(gameCreatedEvent.getGameId())).toUri());
        Game newGame = Game.fromGameDetails(gameCreatedEvent.getGameDetails());
        GameEvent gameEvent = new GameEvent(GameEvent.GAME_CREATED, gameCreatedEvent.getGameId(),
                newGame.getGameName(), newGame.getCreatedByUsername(), null);
        // TODO messaging
        logEvent(gameEvent);

        return new ResponseEntity<>(newGame, headers, HttpStatus.CREATED);
    }

    /***
     * REST Controller method for joining a game.
     * @param gameId    The game ID to join (underlying primary key).
     * @param userName  The username for the user to join the game.
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "{gameId}/join/{userName}")
    public ResponseEntity<Boolean> joinGame(@PathVariable int gameId, @PathVariable String userName)
    {
        JoinGameEvent joinGameEvent = new JoinGameEvent(userName, gameId);
        GameJoinedEvent gameJoinedEvent = gameService.joinGame(joinGameEvent);
        if(gameJoinedEvent.isSuccessfulJoin()) {

            GameEvent gameEvent = new GameEvent(GameEvent.GAME_JOINED, joinGameEvent.getGameId(), null,joinGameEvent.getUsername(),gameJoinedEvent.getPlayerSlot());
            // TODO messaging
            logEvent(gameEvent);

        }

        return new ResponseEntity<>(true, HttpStatus.FOUND);
    }

    private void logEvent(BaseEvent gameEvent) {
        logger.debug("logging event {}", gameEvent);
    }
}
