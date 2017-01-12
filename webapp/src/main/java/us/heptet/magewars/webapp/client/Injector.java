package us.heptet.magewars.webapp.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.webapp.client.gwt.GameViewImpl;

/* Created by kay on 4/20/2014. */
/**
 * Interface for our Ginjector
 */
@GinModules(InjectorModule.class)
public interface Injector extends Ginjector {
    /***
     * Get GameView
     * @return
     */
    GameViewImpl getGameView();

    /**
     * Get login service
     * @return
     */
    LoginServiceAsync getLoginService();

    /**
     * Get signup service
     * @return
     */
    SignupServiceAsync getSignupService();

    /**
     * Get game service
     * @return
     */
    GameServiceAsync getGameService();

    /**
     * Get app controller
     * @return
     */
    AppController getAppController();

    /**
     * Get Game Controller
     * @return
     */
    GameController getGameController();

    /**
     * Get UI Factory
     * @return
     */
    UiFactory getUiFactory();

    /**
     * Get Game control
     * @return
     */
    GameControl getGameControl();

    /**
     * Get Event Manager
     * @return
     */
    EventManager getEventManager();
}
