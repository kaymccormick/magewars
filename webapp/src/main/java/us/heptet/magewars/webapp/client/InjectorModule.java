package us.heptet.magewars.webapp.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.multibindings.GinMultibinder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;
import us.heptet.magewars.game.*;
import us.heptet.magewars.game.Game;
import us.heptet.magewars.game.events.EventChannel;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.domain.game.setup.GameSetup;
import us.heptet.magewars.domain.game.setup.GameSetupImpl;
import us.heptet.magewars.game.spellbook.SpellBookInitializer;
import us.heptet.magewars.game.state.GameStateChangeProcessor;
import us.heptet.magewars.game.state.GameStateChangeProcessorImpl;
import us.heptet.magewars.ui.SpellBookViewer;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.factory.GwtUiFactory;
import us.heptet.magewars.game.events.EventManagerImpl;
import us.heptet.magewars.ui.view.GameView;
import us.heptet.magewars.ui.view.PlanSpellsView;
import us.heptet.magewars.webapp.client.gwt.GameViewImpl;
import us.heptet.magewars.webapp.client.presenter.GwtGameController;

/* Created by kay on 4/20/2014. */
/**
 * This class defines the bindings for Gin.
 */
public class InjectorModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(UiFactory.class).to(GwtUiFactory.class);
        bind(CardSet.class).to(BaseCardSet.class);
        bind(GameSetup.class).to(GameSetupImpl.class);
        bind(GameStateChangeProcessor.class).to(GameStateChangeProcessorImpl.class);
        bind(SpellBookInitializer.class).to(SpellBookManager.class);


        bind(GameController.class).to(GwtGameController.class);
        bind(PlayerInfo.class).to(PlayerInfoImpl.class).asEagerSingleton();

        // are these the same?!?
        bind(EventManager.class).to(EventManagerImpl.class).asEagerSingleton();

        GinMultibinder.newSetBinder(binder(), EventChannel.class);
        bind(Arena.class).to(ArenaImpl.class);
        GinMultibinder<Mage> mageMultibinder = GinMultibinder.newSetBinder(binder(), Mage.class);
        mageMultibinder.addBinding().to(BeastMaster.class);
        mageMultibinder.addBinding().to(Warlock.class);
        bind(GameView.class).to(GameViewImpl.class);
        bind(PlanSpellsView.class).to(SpellBookViewer.class);
    }

    /**
     *
     * @param gameSituation
     * @param eventManager
     * @param spellBookInitializer
     * @return
     */
    @Singleton
    @Provides
    GameControl gameControl(GameSituation gameSituation, EventManager eventManager, SpellBookInitializer spellBookInitializer)
    {
        GameControl gameControl = new GameControlImpl(gameSituation, eventManager, spellBookInitializer, true);
        gameControl.setClient(true);
        return gameControl;
    }

    @Singleton
    @Provides
    HandlerManager handlerManager()
    {
        return new HandlerManager(null);
    }

    @Provides
    @Singleton
    GameSituation gameSituation(Game game) { return game; }

    @Singleton
    @Provides
    Game game(Arena arena, CardSet cardSet)
    {
        Game game = new Game(arena, cardSet);
        game.setDescription("Created by GIN through @Provides-method");
        return game;
    }
}
