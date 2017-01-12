package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.SignUpEvent;
import us.heptet.magewars.webapp.client.SignedUpEvent;
import us.heptet.magewars.webapp.client.SignupServiceAsync;
import us.heptet.magewars.webapp.client.view.SignupView;

/* Created by kay on 6/1/2014. */
/**
 * Presenter for the Signup view.
 */
public class SignupPresenter implements Presenter, SignupView.Presenter {

    private final SignupView view;
    private final SignupServiceAsync signupServiceAsync;
    private EventManager eventManager;

    /**
     * Create instance
     * @param signupView
     * @param signupServiceAsync
     * @param eventManager
     */
    public SignupPresenter(SignupView signupView, SignupServiceAsync signupServiceAsync, EventManager eventManager) {
        this.view = signupView;
        this.signupServiceAsync = signupServiceAsync;
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
    public void onSignupButtonClicked() {
        if(!view.getPassword().getValue().equals(view.getConfirmPassword().getValue())) {
            view.getStatus().setValue("Passwords do not match.");
            return;
        }
        SignUpEvent signUpEvent = new SignUpEvent(view.getUsername().getValue(),
                view.getConfirmPassword().getValue(), view.getEmail().getValue());
        signupServiceAsync.signup(signUpEvent, new AsyncCallback<SignedUpEvent>() {
            @Override
            public void onFailure(Throwable throwable) {
                view.getStatus().setValue("Sign up failed.");
            }

            @Override
            public void onSuccess(SignedUpEvent signedUpEvent) {
                view.setStatusVisible(true);
                view.getStatus().setValue("Sign up succeeded!");
                view.getConfirmPassword().setValue("");
                view.getPassword().setValue("");
                eventManager.resetEventChannels();

                History.newItem("games");
            }
        });
    }
}
