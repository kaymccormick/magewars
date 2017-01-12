package us.heptet.magewars.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.persistence.jpa.*;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.Mage;
import us.heptet.magewars.domain.persistence.jpa.Player;
import us.heptet.magewars.domain.persistence.repository.GameRepository;
import us.heptet.magewars.domain.persistence.repository.MageRepository;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.game.*;
import us.heptet.magewars.game.events.*;
import us.heptet.magewars.game.phase.ReadyPhaseType;
import us.heptet.magewars.game.spellbook.SpellBookInitializer;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;
import us.heptet.magewars.gameservice.store.GameStore;
import us.heptet.magewars.test.matchers.GameEventMatcher;
import us.heptet.magewars.ui.config.TestConfig;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.view.GameView;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by jade on 17/08/2016.
 */
@ContextConfiguration(classes = {StandaloneGameServiceConfig.class, TestConfig.class})
@RunWith(SpringRunner.class)
public class GameControllerIntegrationTest  {
    private static Logger logger = LoggerFactory.getLogger(GameControllerIntegrationTest.class);

    ObjectMapper objectMapper = new ObjectMapper();

    MessageSendingOperations operations = new MessageSendingOperations();


    GameController gameController;

    @Inject
    GameStore gameStore;

    @Inject
    CardSet cardSet;

    @Inject
    GameRepository gameRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    MageRepository mageRepository;

    private GameView gameView;
    private Mockery mockery;
    private GameControl storeGameControl;

    @Before
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        gameStore.setTemplate(operations);

        mockery = new Mockery();
        gameView = mockery.mock(GameView.class);
    }

    class MessageSendingOperations implements SimpMessageSendingOperations
    {

        @Override
        public void convertAndSendToUser(String user, String destination, Object payload) throws MessagingException {

        }

        @Override
        public void convertAndSendToUser(String user, String destination, Object payload, Map<String, Object> headers) throws MessagingException {

        }

        @Override
        public void convertAndSendToUser(String user, String destination, Object payload, MessagePostProcessor postProcessor) throws MessagingException {

        }

        @Override
        public void convertAndSendToUser(String user, String destination, Object payload, Map<String, Object> headers, MessagePostProcessor postProcessor) throws MessagingException {

        }

        @Override
        public void send(Message<?> message) throws MessagingException {

        }

        @Override
        public void send(String destination, Message<?> message) throws MessagingException {

        }

        @Override
        public void convertAndSend(Object payload) throws MessagingException {

        }

        /*
         * This is the primary method used.
         */
        @Override
        public void convertAndSend(String destination, Object payload) throws MessagingException {
            try {
                logger.info("[{}] got {}", destination, objectMapper.writeValueAsString(payload));
                if(RPCEvent.class.isAssignableFrom(payload.getClass()))
                {
                    gameController.getGameControl().fireEvent(((RPCEvent)payload).getEvent());
                }

            } catch(Exception ex)
            {
                ex.printStackTrace();
                throw new RuntimeException(ex);
                //logger.error("{}", ex);
            }
        }

        @Override
        public void convertAndSend(String destination, Object payload, Map<String, Object> headers) throws MessagingException {

        }

        @Override
        public void convertAndSend(Object payload, MessagePostProcessor postProcessor) throws MessagingException {

        }

        @Override
        public void convertAndSend(String destination, Object payload, MessagePostProcessor postProcessor) throws MessagingException {

        }

        @Override
        public void convertAndSend(String destination, Object payload, Map<String, Object> headers, MessagePostProcessor postProcessor) throws MessagingException {

        }
    }


    @Transactional
    void setupGame()
    {
        User theUser = getOrCreateUser("user1");
        User user2 = getOrCreateUser("user2");

        Game entity = new Game("my game", 2, 2, theUser, GameStatus.SETUP);

        //fixme
        // no mages specified!! should not work
        Player player1 = new Player();
        player1.setUser(theUser);
        Mage warlock = mageRepository.findOneByCardEnumName(CardEnum.WARLOCK.name());
        player1.setMage(warlock);
        player1.setGame(entity);

        Player player2 = new Player();
        player2.setUser(user2);
        Mage beastmaster = mageRepository.findOneByCardEnumName(CardEnum.BEASTMASTER.name());
        player2.setMage(beastmaster);
        player2.setGame(entity);

        // test for collisions?
        player2.setPlayerSlot(1);

        entity.getPlayers().add(player1);
        entity.getPlayers().add(player2);

        gameRepository.save(entity);

        storeGameControl = gameStore.initializeGame(entity.getGameId());

    }

    private User getOrCreateUser(String user1) {
        User oneByUserName = userRepository.findOneByUserName(user1);
        if(oneByUserName == null) {
            User theUser = new User(user1);
            userRepository.save(theUser);
            return theUser;
        }
        return oneByUserName;
    }

    @Test
    public void testJabbers() throws Exception {
        gameController = new GameController();
        gameController.setDescription("Object under test");

        SpellBookInitializer spellBookInitializer = mockery.mock(SpellBookInitializer.class);
        mockery.checking(new Expectations(){{
            oneOf(gameView).setController(gameController);
        }});

        gameController.setView(gameView);
        Arena arena = new ArenaImpl();
        EventManager eventManager = new EventManagerImpl();
        us.heptet.magewars.game.Game game = new us.heptet.magewars.game.Game(arena, cardSet);
        game.addPlayer(GameElementFactory.createPlayer(0));
        game.addPlayer(GameElementFactory.createPlayer(1));

        GameControl gameControl = new GameControlImpl(game, eventManager, spellBookInitializer);

        setupGame();

        mockery.checking(new Expectations(){{
            oneOf(gameView).onNewPhase(with(Matchers.both(GameEventMatcher.matchesEventType(PhaseEvent.NEW_PHASE)).and(
                    Matchers.hasProperty("phase", Matchers.hasProperty("phaseType", Matchers.is(ReadyPhaseType.INITIATIVE))))));

        }});


        storeGameControl.startGame(storeGameControl.getGameSetup());

        mockery.assertIsSatisfied();

    }
}
