package us.heptet.magewars.gameservice.persistence.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.GameStatus;
import us.heptet.magewars.domain.persistence.jpa.Mage;
import us.heptet.magewars.domain.persistence.jpa.Player;
import us.heptet.magewars.domain.persistence.jpa.SpellBook;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.GameRepository;
import us.heptet.magewars.domain.persistence.repository.MageRepository;
import us.heptet.magewars.domain.persistence.repository.PlayerRepository;
import us.heptet.magewars.domain.persistence.repository.SpellBookRepository;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.exceptions.CreateGameException;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.persistence.services.GamePersistenceService;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 5/6/2014. */

/**
 * JPA implementation of GamePersistenceService. Uses Spring Data JPA repositories to interface with the jpa layer.
 * The real McCoy.
 */
public class GamePersistenceServiceImpl implements GamePersistenceService {
    private Logger logger = LoggerFactory.getLogger(GamePersistenceServiceImpl.class);

    GameRepository<Integer> gameRepository;

    UserRepository userRepository;

    MageRepository mageRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SpellBookRepository spellBookRepository;

    ConversionService conversionService;

    /*
     * Request all games available on the server.
     */
    @Override
    public AllGamesEvent requestAllGames(RequestAllGamesEvent requestAllGamesEvent) {
        List<GameDetails> generatedDetails = new ArrayList<>();
        for (Game game : gameRepository.findAll()) {
            /* This is where we use JpaToGameDetails.
             * I'm not sure it is "proper" to use the conversion service, but I wanted to try it.
             *
             *
             */
            generatedDetails.add(conversionService.convert(game, GameDetails.class));
        }
        return new AllGamesEvent(generatedDetails);


    }

    /*
     * Create a game on the server. Game details are provided in a member of CreateGameEvent.
     */
    @Override
    @Transactional
    public GameCreatedEvent createGame(CreateGameEvent createGameEvent) {
        Game game = new Game();
        game.setName(createGameEvent.getDetails().getGameName());

        // We have to have a valid user to create the game.
        String createdByUsername = createGameEvent.getDetails().getCreatedByUsername();
        User user = userRepository.findOneByUserName(createdByUsername);
        if (user == null) {
            throw new CreateGameException("User " + createdByUsername + " not found.", createGameEvent);
        }

        game.setCreatedByUser(user);

        // There is a discrepancy between how mages are discovered
        // here and injected via discovering subclasses of Mage
        Iterable<Mage> mages = mageRepository.findAll();
        for (Mage mage : mages) {
            game.getAvailableMages().add(mage);
        }

        game.setMaxPlayers(createGameEvent.getDetails().getMaxPlayers());
        game.setMinPlayers(createGameEvent.getDetails().getMinPlayers());
        try {
            gameRepository.save(game);
        } catch (Exception e) {
            logger.error("unable to persist game: {}", e.toString(), e);
            throw new CreateGameException("unable to persist game", e, createGameEvent);
        }
        return new GameCreatedEvent(game.getGameId(), conversionService.convert(game, GameDetails.class));
    }

    /*
     * Request game details for a specific game.
     */
    @Override
    public GameDetailsEvent requestGameDetails(RequestGameDetailsEvent requestGameDetailsEvent) {
        Game game = gameRepository.findOne(requestGameDetailsEvent.getKey());
        GameDetails details = conversionService.convert(game, GameDetails.class);
        return new GameDetailsEvent(requestGameDetailsEvent.getKey(), details);
    }

    /*
     * Request exteended game details for a specific game. Involves more querying.
     */
    @Override
    @Transactional
    public GameExtendedDetailsEvent requestGameExtendedDetails(RequestGameExtendedDetailsEvent requestGameExtendedDetailsEvent) {
        Game game = gameRepository.findOne(requestGameExtendedDetailsEvent.getGameId());

        GameExtendedDetails details;
        details = conversionService.convert(game, GameExtendedDetails.class);
        return new GameExtendedDetailsEvent(details);
    }

    /*
     * Join a game.
     */
    @Override
    @Transactional
    public GameJoinedEvent joinGame(JoinGameEvent joinGameEvent) {
        Game game = gameRepository.findOne(joinGameEvent.getGameId());
        assert game != null;
        int size = game.getPlayers().size();
        int maxPlayers = game.getMaxPlayers();
        assert size <= maxPlayers;
        for (Player player : game.getPlayers()) {
            if (player.getUser().getUserName().equals(joinGameEvent.getUsername())) {
                assert false : "player already joined game";
            }
        }
        User user = userRepository.findOneByUserName(joinGameEvent.getUsername());
        assert user != null;
        GameJoinedEvent joinedEvent = new GameJoinedEvent();
        boolean addedPlayer = false;
        for (int i = 0; i < maxPlayers; i++) {
            boolean slotTaken = false;
            for (Player player : game.getPlayers()) {
                if (player.getPlayerSlot() == i) {
                    slotTaken = true;
                    break;
                }
            }
            if (!slotTaken) {
                Player newPlayer = new Player();
                newPlayer.setPlayerSlot(i);
                newPlayer.setUser(user);
                newPlayer.setGame(game);
                game.getPlayers().add(newPlayer);
                joinedEvent.setPlayerSlot(i);
                joinedEvent.setSuccessfulJoin(true);
                break;
            }
        }
        if (joinedEvent.isSuccessfulJoin()) {
            gameRepository.save(game);
        }
        return joinedEvent;
    }

    /*
     * Method that handles the start game logic in the persistence layer. Merely sets the status to STARTING
     */
    @Override
    public GameStartedEvent startGame(StartGameEvent startGameEvent) {
        Game game = gameRepository.findOne(startGameEvent.getGameId());
        game.setStatus(GameStatus.STARTING);
        return new GameStartedEvent();
    }

    @Override
    @Transactional
    public GamePlayerUpdatedEvent updateGamePlayer(UpdateGamePlayerEvent updateGamePlayerEvent) {
        Game game = gameRepository.findOne(updateGamePlayerEvent.getGameId());
        for (Player player : game.getPlayers()) {
            if (player.getUser().getUserName().equals(updateGamePlayerEvent.getUsername())) {
                SpellBook spellBook = spellBookRepository.findOne(updateGamePlayerEvent.getSpellbookId());
                player.setSpellBook(spellBook);

                // this is a redundant field, sort of
                player.setMage(spellBook.getMage());

                playerRepository.save(player);
            }
        }
        return new GamePlayerUpdatedEvent();
    }

    @Inject
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setMageRepository(MageRepository mageRepository) {
        this.mageRepository = mageRepository;
    }

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void setSpellBookRepository(SpellBookRepository spellBookRepository) {
        this.spellBookRepository = spellBookRepository;
    }

    @Inject
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
}
