package us.heptet.magewars.ui;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import us.heptet.magewars.domain.game.ArenaImpl;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.game.*;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.EventManagerImpl;
import us.heptet.magewars.game.spellbook.SpellBookInitializer;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.gameservice.core.service.GameEventHandler;
import us.heptet.magewars.gameservice.messaging.EventController;
import us.heptet.magewars.gameservice.store.GameStore;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.controller.PlanSpellsController;
import us.heptet.magewars.ui.view.GameView;
import us.heptet.magewars.ui.view.PlanSpellsView;

/**
 * Created by jade on 13/09/2016.
 */
@Lazy
public class TestConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    EventController
            eventController()
    {
        return new EventController(combinedGameService(), simpMessagingTemplate(), applicationContext.getBean(GameStore.class));
    }

    private SimpMessageSendingOperations simpMessagingTemplate() {
        return new TestSimpMessagingTemplate();
    }

    private CombinedGameService combinedGameService() {
        return new GameEventHandler(applicationContext.getBean(GameStore.class));
    }

    @Bean
    @Scope(scopeName = "player")
    GameController
    gameController()
    {
        GameController gameController = new GameController(gameView(mockery()), playerGameControl(), planSpellsController());
        return gameController;
    }

    @Bean
    @Scope(scopeName = "player")
    private GameControl playerGameControl() {
        return new GameControlImpl(playerGameSituation(), playerEventManager(), applicationContext.getBean(SpellBookInitializer.class));
    }

    @Bean
    @Scope(scopeName = "player")
    private EventManager playerEventManager() {
        return new EventManagerImpl();
    }

    @Bean
    @Scope(scopeName = "player")
    private GameSituation playerGameSituation() {
        return new Game(new ArenaImpl(), new BaseCardSet());
    }


    @Bean
    PlayerInfo
            playerInfo() {
        return new PlayerInfoImpl();
    }

    @Bean
    PlanSpellsController
            planSpellsController()
    {
        return new PlanSpellsController(planSpellsView(mockery()));
    }

    @Bean
    PlanSpellsView planSpellsView(Mockery mockery)
    {
        PlanSpellsView planSpellsView = mockery.mock(PlanSpellsView.class);
        mockery.checking(new Expectations(){{
            ignoring(planSpellsView);
        }});
        return planSpellsView;
    }

    @Bean
    GameView
    gameView(Mockery mockery)
    {
        GameView gameView = mockery.mock(GameView.class);
        mockery.checking(new Expectations(){{
            ignoring(gameView);
        }});
        return gameView;
    }

    @Bean
    Mockery
    mockery()
    {
        return new Mockery();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
