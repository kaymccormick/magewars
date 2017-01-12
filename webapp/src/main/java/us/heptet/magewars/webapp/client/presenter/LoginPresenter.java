package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.NavigationEvent;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.LoginServiceAsync;
import us.heptet.magewars.webapp.client.view.LoginView;

/* Created by kay on 5/23/2014. */
/**
 * Presenter implementation for the {@link LoginView}
 */
public class LoginPresenter implements Presenter, LoginView.Presenter {
    private LoginView view;
    private LoginServiceAsync loginServiceAsync;
    private EventManager eventManager;

    /**
     * Create a LoginPresenter.
     * @param loginView The login view.
     * @param loginServiceAsync The GWT async login service.
     * @param eventManager The event manager.
     */
    public LoginPresenter(LoginView loginView, LoginServiceAsync loginServiceAsync, EventManager eventManager) {
        this.view = loginView;
        this.loginServiceAsync = loginServiceAsync;
        this.eventManager = eventManager;
        this.view.setPresenter(this);
    }

    @Override
    public void go(Container container) {
        container.clear();
        container.add(view);
    }

    @Override
    public void onNavigateAway() {
        /* nothing to do */
    }

    @Override
    public void onLoginButtonClicked() {
        final TakesValue<String> statusLabel = view.getStatus();
        statusLabel.setValue("Logging in...");
        view.setStatusVisible(true);


        loginServiceAsync.login(view.getUsername().getValue(),
                view.getPassword().getValue(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                statusLabel.setValue("Login failed.");
                view.setStatusVisible(true);
            }

            @Override
            public void onSuccess(Void aVoid) {
                statusLabel.setValue("Login successful.");
                view.setStatusVisible(true);
                eventManager.resetEventChannels(); // we need to wrap this is an exception handler
                eventManager.fireEvent(new NavigationEvent("games"));
            }
        });
    }
}
