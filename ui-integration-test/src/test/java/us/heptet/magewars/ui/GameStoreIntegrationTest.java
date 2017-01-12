package us.heptet.magewars.ui;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.auto.Auto;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.exceptions.GameInitializationException;
import us.heptet.magewars.domain.persistence.jpa.*;
import us.heptet.magewars.domain.persistence.repository.GameRepository;
import us.heptet.magewars.domain.persistence.repository.MageRepository;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.events.*;
import us.heptet.magewars.gameservice.config.WebSocketMessagingConfig;
import us.heptet.magewars.gameservice.messaging.EventController;
import us.heptet.magewars.test.RPCEventMatcher;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;
import us.heptet.magewars.gameservice.store.GameStore;
import us.heptet.magewars.test.GameTestHelper;
import us.heptet.magewars.test.game.ConvertAndSendAction;
import us.heptet.magewars.ui.controller.GameController;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jade on 29/07/2016.
 */
@ContextConfiguration(classes = {StandaloneGameServiceConfig.class, TestConfig.class})
@WebAppConfiguration
@DirtiesContext
public class GameStoreIntegrationTest extends AbstractJUnit4SpringContextTests {
    @Inject
    GameStore gameStore;

    @Inject
    GameRepository<Integer> gameRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    MageRepository mageRepository;


    @Inject
    private Mockery mockery;

    private SimpMessageSendingOperations messaging;

    @Inject
    EventController eventController;

    @Auto
    Sequence events;
    private GameControl gameControl;

    TestScope<PlayerPK> playerScope;

    @Inject
    ConfigurableBeanFactory factory;

    @Before
    public void setUp() throws Exception {
        playerScope = new TestScope<>();

        factory.registerScope("player", playerScope);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

//        RequestContextHolder.setRequestAttributes(servletWebRequest);

        mockery = new Mockery();
        messaging = mockery.mock(SimpMessageSendingOperations.class, "messaging");
        gameStore.setTemplate(messaging);

        mockery.checking(new Expectations(){{

        }});

    }

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
        player1.setSpellBook(warlock.getDefaultSpellbook());

        Player player2 = new Player();
        player2.setUser(user2);
        Mage beastmaster = mageRepository.findOneByCardEnumName(CardEnum.BEASTMASTER.name());
        player2.setMage(beastmaster);
        player2.setGame(entity);
        player2.setSpellBook(beastmaster.getDefaultSpellbook());

        // test for collisions?
        player2.setPlayerSlot(1);

        entity.getPlayers().add(player1);
        entity.getPlayers().add(player2);

        gameRepository.save(entity);

        gameControl = gameStore.initializeGame(entity.getGameId());

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
    public void testStartGame() throws Exception {
        ConvertAndSendAction forward = new ConvertAndSendAction();

        setupGame();


        mockery.checking(new Expectations(){{
            exactly(2).of(messaging).convertAndSend(with(any(String.class)), (Object)with(RPCEventMatcher.matchesEventType(GameEvent.ADD_OBJECT)));
            will(forward);
            oneOf(messaging).convertAndSend(with(any(String.class)), (Object)with(RPCEventMatcher.matchesEventType(GameEvent.NEW_STAGE)));
            allowing(messaging).convertAndSend(with(any(String.class)), (Object)with(RPCEventMatcher.matchesEventType(PhaseEvent.NEW_PHASE)));
            allowing(messaging).convertAndSend(with(any(String.class)), (Object)with(RPCEventMatcher.matchesEventType(GameEvent.PLAYER_INFO)));
            will(forward);
            oneOf(messaging).convertAndSend(with(any(String.class)), (Object)with(RPCEventMatcher.matchesEventType(GameEvent.CHANGE_INITIATIVE)));
        }});



        Game game;

        GameSituation<Integer> gameSituation = gameControl.getGameSituation();

        assert gameSituation.getGameIdentity() != null;
        game = gameRepository.findOne(gameSituation.getGameIdentity());

        // this GameController is disconnected from the gamecontrol we create later. THIS IS BAD
        playerScope.setCurrentScope(new PlayerPK(game, 0));
        GameController gameController = applicationContext.getBean(GameController.class);
        gameController.setDescription("Client Player 1");

        playerScope.setCurrentScope(new PlayerPK(game, 1));
        GameController gameController2 = applicationContext.getBean(GameController.class);
        gameController2.setDescription("Client Player 2");
        gameController2.getGameControl().getGameSituation().setGameIdentity(game.getGameId());

        GameTestHelper testHelper = new GameTestHelper();

        EventChannel eventChannel1 = new MyEventChannel();
        eventChannel1.addEventAcceptor(new MyAcceptsEvent(game.getGameId(), 0));

        EventChannel eventChannel2 = new MyEventChannel();
        eventChannel2.addEventAcceptor(new MyAcceptsEvent(game.getGameId(), 1));
        //eventChannel1.addEventAcceptor(gameControl.getEventManager());

        forward.add(gameController.getGameControl().getEventManager());
        forward.add(gameController2.getGameControl().getEventManager());
        gameController.getGameControl().getEventManager().addEventChannel(eventChannel1);
        gameController2.getGameControl().getEventManager().addEventChannel(eventChannel2);

        try {

            gameControl.startGame(gameControl.getGameSetup());
        } catch(GameInitializationException ex)
        {
            logger.info("gameSetup = {}" + ex.getGameSetup());
            throw ex;
        }


        gameController.onReadyButtonClick();
        gameController2.onReadyButtonClick();

        gameController.onReadyButtonClick();
        gameController2.onReadyButtonClick();

        gameController.onReadyButtonClick();
        gameController2.onReadyButtonClick();

        gameController.onReadyButtonClick();
        gameController2.onReadyButtonClick();

        mockery.assertIsSatisfied();
    }

    class MyAcceptsEvent implements AcceptsEvent {
        private Integer gameID;
        private int playerIndex;

        public MyAcceptsEvent(Integer gameID, int playerIndex) {
            this.gameID = gameID;
            this.playerIndex = playerIndex;
        }

        @Override
        public <T extends BaseEvent> void acceptEvent(T event) {
            logger.info("AcceptsEvent: " + event.toString());
            event.setGameKey(gameID);
            event.setSourcePlayerIndex(playerIndex);
            eventController.handle(new RPCEvent(event));
        }
    }
    class MyEventChannel implements EventChannel {
        List<AcceptsEvent> acceptsEventList = new ArrayList<>();

        @Override
        public void openEventChannel() {

        }

        @Override
        public void addEventAcceptor(AcceptsEvent acceptsEvent) {
            acceptsEventList.add(acceptsEvent);

        }

        @Override
        public void subscribe(EventSubscription subscription) {

        }

        @Override
        public void reset() {

        }

        @Override
        public <T extends BaseEvent> void acceptEvent(T event) {
            acceptsEventList.forEach(a -> a.acceptEvent(event));
        }
    }
}