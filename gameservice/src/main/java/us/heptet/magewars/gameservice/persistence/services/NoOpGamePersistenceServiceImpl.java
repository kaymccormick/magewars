package us.heptet.magewars.gameservice.persistence.services;

import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

import java.util.ArrayList;

/* Created by kay on 5/11/2014. */
/**
 * Random no-op implementation
 */
public class NoOpGamePersistenceServiceImpl implements GamePersistenceService {
    @Override
    public AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent) {
        return new AllGamesEvent(new ArrayList<GameDetails>());
    }

    @Override
    public GameCreatedEvent createGame(CreateGameEvent createGameEvent) {
        return null;
    }

    @Override
    public GameDetailsEvent requestGameDetails(RequestGameDetailsEvent requestGameDetailsEvent) {
        return null;
    }

    @Override
    public GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent) {
        return null;
    }

    @Override
    public GameJoinedEvent joinGame(JoinGameEvent joinGameEvent) {
        return null;
    }

    @Override
    public GameStartedEvent startGame(StartGameEvent startGameEvent) {
        return null;
    }

    @Override
    public GamePlayerUpdatedEvent updateGamePlayer(UpdateGamePlayerEvent updateGamePlayerEvent) {
        return null;
    }
}
