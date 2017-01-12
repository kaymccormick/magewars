package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.user.client.History;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.AuthenticationState;
import us.heptet.magewars.webapp.client.view.HomeView;

/* Created by kay on 5/16/2014. */
/**
 * Home Presenter
 */
public class HomePresenter implements us.heptet.magewars.webapp.client.presenter.Presenter {
    private final HomeView view;
    private final AuthenticationState authenticationState;

    /**
     * Create instance
     * @param view
     * @param authenticationState
     */
    public HomePresenter(HomeView view,
                         AuthenticationState authenticationState) {
        this.view = view;
        this.authenticationState = authenticationState;
    }

    @Override
    public void go(Container container) {
        if(!authenticationState.isFullyAuthenticated())
        {
            History.newItem("login");

            return;
        }
        bind();
        container.clear();
        container.add(view);
        view.setFullyAuthenticated(authenticationState.isFullyAuthenticated());
    }

    @Override
    public void onNavigateAway() {
        /* nothing to do */

    }


    private void bind() {

        /* nothing to do */
    }
}
