package us.heptet.magewars.webapp.client.view;

import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.ui.view.View;

/* Created by kay on 5/16/2014. */
/**
 * Interface for the Home View
 */
public interface HomeView extends View {
    /**
     * Get the view as a widget.
     * @return
     */
    Widget asWidget();

    /**
     * Set a boolean if we are fully authenticated.
     * @param fullyAuthenticated
     */
    void setFullyAuthenticated(boolean fullyAuthenticated);
}
