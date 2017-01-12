package us.heptet.magewars.gameservice.persistence.services.jpa;

import org.springframework.core.convert.ConversionService;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.jmock.lib.script.ScriptedAction.perform;
import static org.testng.Assert.*;
import org.jmock.Mockery;
import org.jmock.Expectations;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.GameStatus;
import us.heptet.magewars.domain.persistence.jpa.Mage;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.GameRepository;
import us.heptet.magewars.domain.persistence.repository.MageRepository;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.gameservice.core.events.games.*;
import us.heptet.magewars.gameservice.core.events.GameExtendedDetailsEvent;
import us.heptet.magewars.gameservice.core.events.RequestGameExtendedDetailsEvent;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePersistenceServiceImplTest {
    private final String gameName = "game name";
    private final int minPlayers = 2;
    private final int maxPlayers = 2;
    private final GameStatus gameStatus = GameStatus.SETUP;
    private final int gameId1 = 1;
    private final us.heptet.magewars.service.events.GameStatus detailsStatusSetup = us.heptet.magewars.service.events.GameStatus.SETUP;
    GamePersistenceServiceImpl gamePersistenceService;
    GameRepository gameRepository;
    UserRepository userRepository;
    MageRepository mageRepository;
    ConversionService conversionService;

    Mockery mockery;
    private final String testUsername1 = "testuser1";
    private final User foundTestUser1 = new User(testUsername1);
    private GameDetails gameDetails = new GameDetails(gameId1, gameName, testUsername1, minPlayers, maxPlayers, detailsStatusSetup);
    private List<GamePlayerDetails> gamePlayerDetails;
    private List<String> availableMages;
    private List<SpellbookDetails> defaultSpellbookDetailsList;
    private GameExtendedDetails gameExtendedDetails = new GameExtendedDetails(gameDetails, gamePlayerDetails, availableMages, defaultSpellbookDetailsList);
    private final User testuser1 = new User(testUsername1);
    // this jpaGame has no key, we need one with a key
    private final Game jpaGame = new Game(gameName, minPlayers, maxPlayers, testuser1, gameStatus);

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        gameRepository = mockery.mock(GameRepository.class);
        gamePersistenceService = new GamePersistenceServiceImpl();
        gamePersistenceService.setGameRepository(gameRepository);
        conversionService = mockery.mock(ConversionService.class);
        gamePersistenceService.setConversionService(conversionService);
        userRepository = mockery.mock(UserRepository.class);
        gamePersistenceService.setUserRepository(userRepository);
        mageRepository = mockery.mock(MageRepository.class);
        gamePersistenceService.setMageRepository(mageRepository);

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestAllGames() throws Exception {
        mockery.checking(new Expectations() {{
            oneOf(gameRepository).findAll();
            will(returnValue(Collections.singletonList(jpaGame)));
            oneOf(conversionService).convert(with(same(jpaGame)), with(same(GameDetails.class)));
            will(returnValue(gameDetails));

        }});
        AllGamesEvent allGamesEvent = gamePersistenceService.requestAllGames(new RequestAllGamesEvent());
        mockery.assertIsSatisfied();
        assertNotNull(allGamesEvent);
        assertNotNull(allGamesEvent.getGamesDetails());
        assertEquals(allGamesEvent.getGamesDetails().size(), 1);
        GameDetails gameDetails = allGamesEvent.getGamesDetails().get(0);
        assertEquals(gameDetails.getGameName(), gameName);
        assertEquals((int) gameDetails.getGameId(), gameId1);
        assertEquals(gameDetails.getCreatedByUsername(), testUsername1);
        assertEquals(gameDetails.getGameStatus(), detailsStatusSetup);
        assertEquals(gameDetails.getMinPlayers(), minPlayers);
        assertEquals(gameDetails.getMaxPlayers(), maxPlayers);

    }

    @Test
    public void testCreateGame() throws Exception {
        final Game expectedGame = new Game(gameDetails.getGameName(), gameDetails.getMinPlayers(), gameDetails.getMaxPlayers(), testuser1, gameStatus);
        mockery.checking(new Expectations() {{
            oneOf(userRepository).findOneByUserName(testUsername1);
            will(returnValue(foundTestUser1));
            oneOf(mageRepository).findAll();
            will(returnValue(new ArrayList<Mage>()));
            oneOf(gameRepository).save(with(equal(expectedGame))); // we should probably check this
            will(perform("$0.setGameId(gameId)").where("gameId", gameId1));
            // fix for MW-1
            oneOf(conversionService).convert(with(any(Game.class)), with(same(GameDetails.class)));
            will(returnValue(gameDetails));
        }
        });
        GameCreatedEvent gameCreatedEvent = gamePersistenceService.createGame(new CreateGameEvent(gameDetails));
        mockery.assertIsSatisfied();
        assertTrue(gameCreatedEvent.isGameCreated());
        assertEquals(gameCreatedEvent.getGameId(), gameId1);

    }

    @Test
    public void testRequestGameDetails() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameRepository).findOne(gameId1);
            will(returnValue(jpaGame)); // FIXME we need a jpa game with a key
            oneOf(conversionService).convert(with(same(jpaGame)), with(same(GameDetails.class)));
            will(returnValue(gameDetails));
        }
        });
        GameDetailsEvent gameDetailsEvent
                = gamePersistenceService.requestGameDetails(new RequestGameDetailsEvent(gameId1));
        mockery.assertIsSatisfied();
        assertEquals(gameDetailsEvent.getGameDetails(), gameDetails);
        assertEquals(gameDetailsEvent.getKey(), gameId1);
    }

    @Test
    public void testRequestGameExtendedDetails() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameRepository).findOne(gameId1);
            will(returnValue(jpaGame)); // FIXME we need a jpa game with a key
            oneOf(conversionService).convert(with(same(jpaGame)), with(same(GameExtendedDetails.class)));
            will(returnValue(gameExtendedDetails));
        }
        });
        GameExtendedDetailsEvent gameExtendedDetailsEvent = gamePersistenceService.requestGameExtendedDetails(
                new RequestGameExtendedDetailsEvent(gameId1));
        mockery.assertIsSatisfied();
        assertNotNull(gameExtendedDetailsEvent.getGameExtendedDetails());
        assertEquals(gameExtendedDetailsEvent.getGameExtendedDetails(), gameExtendedDetails);

    }

    @Test
    public void testJoinGame() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameRepository).findOne(gameId1);
            will(returnValue(jpaGame));
            oneOf(userRepository).findOneByUserName(testUsername1);
            will(returnValue(foundTestUser1));
            oneOf(gameRepository).save(with(same(jpaGame)));
        }
        });
        GameJoinedEvent gameJoinedEvent = gamePersistenceService.joinGame(new JoinGameEvent(testUsername1, gameId1));
        mockery.assertIsSatisfied();
        assertTrue(gameJoinedEvent.isSuccessfulJoin());
        assertEquals(gameJoinedEvent.getPlayerSlot(), 0);

    }

    @Test(groups={"unimp"})
    public void testStartGame() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(gameRepository).findOne(gameId1); will(returnValue(jpaGame));

        }
        });
        gamePersistenceService.startGame(new StartGameEvent(gameId1));

    }

    @Test(groups={"unimp"})
    public void testUpdateGamePlayer() throws Exception {

    }
}