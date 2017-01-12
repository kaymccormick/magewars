package us.heptet.magewars.gameservice.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.Mage;
import us.heptet.magewars.domain.persistence.jpa.Player;
import us.heptet.magewars.domain.persistence.jpa.SpellBook;
import us.heptet.magewars.domain.persistence.repository.*;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.events.*;
import us.heptet.magewars.domain.game.setup.GameSetup;
import us.heptet.magewars.domain.game.setup.PlayerSetup;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*Created by jade on 29/07/2016.*/
/* *  Tests for this class are in  GameStoreIntegrationTest */

/**
 * Game Store
 * @param <T> Type of game identifier
 */
@Component
public class GameStore<T extends Serializable> implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private static Logger logger = LoggerFactory.getLogger(GameStore.class);

    private SimpMessageSendingOperations template;
    @Inject
    CardSet cardSet;

    @Inject
    ArenaObjectRepository arenaObjectRepository;
    @Inject
    CardRepository cardRepository;
    @Inject
    GameRepository<T> gameRepository;
    @Inject
    MageRepository mageRepository;
    @Inject
    PlayerRepository playerRepository;
    @Inject
    SpellBookRepository spellBookRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    ZoneRepository zoneRepository;

    private ConcurrentHashMap<T, GameInfo> gameInfoMap = new ConcurrentHashMap<>();

    public GameStore() {
        /* Default constructor */
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {

        this.applicationContext = applicationContext;
    }

    /**
     * Random exception class in the wrong place
     */
    public static class NoSuchGameException extends RuntimeException
    {
    }

    /**
     * Initialize the store for a particular game
     *
     * Called from GameEventHandler.startGame
     * @param gameIdentifier Game PK identifier
     * @return GameControl interface
     */
    @Transactional
    public GameControl initializeGame(T gameIdentifier)
    {
        GameInfo gameInfo = initializeForGame(gameIdentifier);
        return gameInfo.getGameControl();
    }

    /**
     * Find a GameControl instance for the given gameIdentifier
     * @param gameIdentifier
     * @return
     */
    public GameControl findGameControl(T gameIdentifier)
    {
        GameInfo gameInfo = gameInfoMap.get(gameIdentifier);
        return gameInfo.getGameControl();
    }

    private class GameInfo
    {
        T gameId;
        GameControl gameControl;
        GameSituation gameSituation;
        Map<String, Integer> playerMap = new HashMap<>();

        GameInfo(T gameId) {
            this.gameId = gameId;
        }

        public T getGameId() {
            return gameId;
        }

        public GameControl getGameControl() {
            return gameControl;
        }

        public void setGameControl(GameControl gameControl) {
            this.gameControl = gameControl;
        }

        public GameSituation getGameSituation() {
            return gameSituation;
        }

        void setGameSituation(GameSituation gameSituation) {
            this.gameSituation = gameSituation;
        }
    }



    private GameInfo initializeForGame(T gameId)
    {
        logger.debug("in initializeForGame({})", gameId);
        boolean contains = gameInfoMap.contains(gameId);
        if(contains)
        {
            throw new GameStoreException("Game " + gameId.toString() + " already in store.");
        }

        GameInfo gameInfo = new GameInfo(gameId);
        Game game = gameRepository.findOne(gameId);
        /*  We need a map of usernames to player indices so that we can later process authenticated stomp messages */
        game.getPlayers().forEach(p -> gameInfo.playerMap.put(p.getUser().getUserName(), p.getPlayerSlot()));

        GameControl gameControl = applicationContext.getBean("serviceGameControl", GameControl.class);
        HashSet<EventChannel> eventChannels = new HashSet<>();
        eventChannels.add(new Channel(gameId));

        gameControl.getEventManager().setEventChannels(eventChannels);

        // sort of odd to have an unordered set here ....
        Set<Player> players = game.getPlayers();
        GameSetup gameSetup = gameControl.getGameSetup();
        logger.info("game setup = " + gameSetup);
        int size = players.size();
        gameSetup.setNumPlayers(size);
        for(int i = 0; i < size; i++)
        {
            PlayerSetup playerSetup = gameSetup.getPlayerSetup(i);
            assert playerSetup != null;
            Player player = game.getPlayer(i);
            assert player != null;

            playerSetup.setUserName(player.getUser().getUserName());
            Mage mage = player.getMage();
            assert mage != null;
            playerSetup.setMageCardEnumName(mage.getCardEnumName());
            SpellBook spellBook = player.getSpellBook();
            assert spellBook != null;
            spellBook.getCards().forEach((card, count) -> playerSetup.setPlayerSpellbookCardCount(card.getCardEnumName(), count));
        }
        gameControl.getGameSituation().setGameIdentity(game.getGameId());


        gameInfo.setGameControl(gameControl);
        gameInfo.setGameSituation(gameControl.getGameSituation());

        gameInfoMap.put(gameId, gameInfo);
        return gameInfo;
    }

    /**
     * Find the player slot / index for the given gameId and username.s
     * @param gameId
     * @param username
     * @return
     */
    public int findUserPlayerSlot(T gameId, String username)
    {
        return gameInfoMap.get(gameId).playerMap.get(username);
    }

    @Autowired(required = false)
    public void setTemplate(SimpMessageSendingOperations template) {
        this.template = template;
    }

    /**
     * Our implementation of the EventChannel to intercept events and send them via
     * Spring Messaging
     */
    private class Channel implements EventChannel
    {
        private T gameId;


        Channel(T gameId) {
            this.gameId = gameId;
        }

        @Override
        public <E extends BaseEvent> void acceptEvent(E event) {
            GameStore.this.template.convertAndSend("/topic/table/" + gameId, new RPCEvent(event));
        }

        @Override
        public void openEventChannel() {
            /* no-op */

        }

        @Override
        public void addEventAcceptor(AcceptsEvent acceptsEvent) {
            /* no-op */
        }

        @Override
        public void subscribe(EventSubscription subscription) {
            /* no-op */
        }

        @Override
        public void reset() {
            /* no-op */
        }
    }

    public void setGameRepository(GameRepository<T> gameRepository) {
        this.gameRepository = gameRepository;
    }
}
