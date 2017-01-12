package us.heptet.magewars.gameservice.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.heptet.magewars.domain.game.AvailableMages;
import us.heptet.magewars.domain.game.Mage;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.exceptions.CreateGameException;
import us.heptet.magewars.gameservice.core.exceptions.StartGameException;
import us.heptet.magewars.gameservice.persistence.services.*;
import us.heptet.magewars.gameservice.store.GameStore;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 3/15/14. */
/**
 * Implementation of {@link CombinedGameService} providing basic methods for working with games.
 */
@Service("gameService")
public class GameEventHandler implements CombinedGameService {
    private static Logger logger = LoggerFactory.getLogger(GameEventHandler.class);
    private AvailableMages availableMages;

    private GameStore gameStore;

    private GamePersistenceService gamePersistenceService;


    /**
     * Construct an instance of {@link GameEventHandler} implementing {@link CombinedGameService} with its dependency {@link GameStore}.
     * @param gameStore Dependency {@link GameStore}.
     */
    @Inject
    public GameEventHandler(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    @Override
    public AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent) {
        return gamePersistenceService.requestAllGames(requestAllGamesEvent);
    }

    @Override
    public GameCreatedEvent createGame(CreateGameEvent createGameEvent) {
        String createdByUsername = createGameEvent.getDetails().getCreatedByUsername();
        if(createdByUsername == null || createdByUsername.isEmpty())
        {
            throw new CreateGameException("Username cannot be null or empty string", createGameEvent);
        }

        return gamePersistenceService.createGame(createGameEvent);
    }

    @Override
    public GameDetailsEvent requestGameDetails(RequestGameDetailsEvent requestGameDetailsEvent) {
        return gamePersistenceService.requestGameDetails(requestGameDetailsEvent);
    }

    @Override
    public GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent) {
        GameExtendedDetailsEvent gameExtendedDetailsEvent = gamePersistenceService.requestGameExtendedDetails(requestGameExtendedDetailsEvent);
        logger.debug("result = {}", gameExtendedDetailsEvent);
        GameExtendedDetails gameExtendedDetails = gameExtendedDetailsEvent.getGameExtendedDetails();
        for(GamePlayerDetails details:gameExtendedDetails.getPlayers()) { // npe
            if (details.getPlayerUsername().equals(requestGameExtendedDetailsEvent.getActiveUser())) {
                details.setRequestingPlayerSlot(true);
            }
        }

        return gameExtendedDetailsEvent;
    }

    @Override
    //    @PreAuthorize()
    public GameJoinedEvent joinGame(JoinGameEvent joinGameEvent) {
        // messaging should go here!

        return gamePersistenceService.joinGame(joinGameEvent);
    }

    /*
     * Server-side code that handles the "start game" logic invoked when the user clicks "start game" on the
     * TableView in the GWT webApp.
      */
    @Override
    @Transactional
    public GameStartedEvent startGame(StartGameEvent startGameEvent) {
        GameExtendedDetailsEvent gameExtendedDetailsEvent = gamePersistenceService.requestGameExtendedDetails(new RequestGameExtendedDetailsEvent(startGameEvent.getGameId()));

        GameExtendedDetails details = gameExtendedDetailsEvent.getGameExtendedDetails();
        assert details != null : "requestGameExtendedDetails returned null property gameExtendedDetails";

        List<Mage> mages = new ArrayList<>(availableMages.getAvailableMageSet());

        if(details.getPlayers() == null || details.getMinPlayers() <= 0 || details.getMaxPlayers() < 1)
        {
            throw new StartGameException("Invalid details for game.");
        }

        int numPlayers = details.getPlayers().size();

        if(numPlayers < details.getMinPlayers() || numPlayers > details.getMaxPlayers())
        {
            return new GameStartedEvent(false, "Game has incorrect number of players.");
        }
        boolean[] hasPlayer = new boolean[details.getMaxPlayers()];
        Mage[] chosenMages = new Mage[details.getPlayers().size()];
        for(GamePlayerDetails playerDetails:details.getPlayers())
        {

            int playerSlot = playerDetails.getPlayerSlot();
            if(hasPlayer[playerSlot])
            {
                return new GameStartedEvent(false, "Multiple players with slot " + playerSlot);
            }
            hasPlayer[playerSlot] = true;

            for(Mage mage:mages)
            {
                if(mage.getCardEnum().name().equals(playerDetails.getMageEnumName()))
                {
                    chosenMages[playerSlot] = mage;
                }
            }
            if(chosenMages[playerSlot] == null)
            {
                return new GameStartedEvent(false, "User " + playerDetails.getPlayerUsername() + " (slot " + playerSlot + ") selected mage '" + playerDetails.getMageEnumName() + "' did not match an available mage.");
            }
        }
        GameStartedEvent gameStartedEvent = gamePersistenceService.startGame(startGameEvent);
        if(gameStartedEvent.isGameStarted())
        {
            GameControl gameControl;
            try {
                gameControl = gameStore.initializeGame(startGameEvent.getGameId());
            } catch(RuntimeException ex)
            {
                String message = "Unable to initializeGame: " + ex.getMessage();
                logger.warn("startGame: {}", message, ex);

                gameStartedEvent.setMessage(message);
                gameStartedEvent.setGameStarted(false);
                return gameStartedEvent;
            }

            try {
                gameControl.startGame(gameControl.getGameSetup());
            } catch(RuntimeException ex)
            {
                String message = "Unable to startGame: " + ex.getMessage();
                logger.warn("startGame: {}", message, ex);
                gameStartedEvent.setMessage(message);
                gameStartedEvent.setGameStarted(false);
                return gameStartedEvent;
            }

        }
        return gameStartedEvent;
    }

    @Override
    public GamePlayerUpdatedEvent updateGamePlayer(UpdateGamePlayerEvent updateGamePlayerEvent) {
        return gamePersistenceService.updateGamePlayer(updateGamePlayerEvent);
    }


    @Inject
    public void setGamePersistenceService(GamePersistenceService gamePersistenceService) {
        this.gamePersistenceService = gamePersistenceService;
    }

    @Inject
    public void setAvailableMages(AvailableMages availableMages) {
        this.availableMages = availableMages;
    }
}
