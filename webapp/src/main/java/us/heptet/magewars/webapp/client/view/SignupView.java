package us.heptet.magewars.webapp.client.view;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.ui.view.View;

/* Created by kay on 6/1/2014. */
/**
 * Interface for the signup view.
 */
public interface SignupView extends View {
    /**
     * Interface for the presenter for the signup view
     */
    interface Presenter
    {
        /**
         * Handler for when the "signup button" is clicked
         */
        void onSignupButtonClicked();
    }

    /**
     * Set the presenter for the view.
     * @param presenter
     */
    void setPresenter(Presenter presenter);

    /**
     * Get the view as a GWT widget
     * @return
     */
    Widget asWidget();

    /**
     * Set the visiblity of the status labell
     * @param b
     */
    void setStatusVisible(boolean b);

    /**
     * A {@link TakesValue} interface for the Status label
     * @return
     */
    TakesValue<String> getStatus();

    /**
     * A hasValue interface for the username
     * @return
     */
    HasValue<String> getUsername();

    /**
     * A hasValue interface for the password
     * @return
     */
    HasValue<String> getPassword();

    /**
     * A hasvalue interface for the confirmation password.
     * @return
     */
    HasValue<String> getConfirmPassword();

    /**
     * A HasValue interface for the email address.
     * @return
     */
    HasValue<String> getEmail();

}
