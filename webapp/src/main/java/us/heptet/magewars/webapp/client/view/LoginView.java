package us.heptet.magewars.webapp.client.view;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.ui.view.View;

/* Created by kay on 5/23/2014. */
/**
 * View interface for LoginView.
 * @see LoginViewImpl
 * @see us.heptet.magewars.webapp.client.presenter.LoginPresenter
 */
public interface LoginView extends View {
    /**
     * Presenter interface for login view presenters
     */
    interface Presenter
    {
        /**
         * Handler for login button clicked
         */
        void onLoginButtonClicked();
    }

    /**
     * Set the presenter
     * @param presenter
     */
    void setPresenter(Presenter presenter);

    /**
     * Get the view as a GWT widget
     * @return
     */
    Widget asWidget();

    /**
     * Set the status label to visible
     * @param b
     */
    void setStatusVisible(boolean b);

    /**
     * TakesValue interface for the Status Label
     * @return
     */
    TakesValue<String> getStatus();

    /**
     * HasValue interface for the Username input
     * @return
     */
    HasValue<String> getUsername();

    /**
     * HasValue interface for the Password input
     * @return
     */
    HasValue<String> getPassword();
}
