package us.heptet.magewars.webapp.client.view;

import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.ui.view.View;

/* Created by kay on 5/23/2014. */
/**
 * This is the interface for the GWT view that hosts the game.
 */
public interface GameView extends View {
    /**
     * Presenter interface. I'm not sure it should extend {@link us.heptet.magewars.ui.view.GameView.Controller}, though. In fact, it probably shouldn't,
     * because all that happens is delegation.
     */
    interface Presenter extends us.heptet.magewars.ui.view.GameView.Controller {
    }

    /**
     * Set the presenter for the GameView
     * @param presenter The presenter
     */
    void setPresenter(Presenter presenter);

    /**
     * get the view as a widget.
     * @return The widget.
     */
    Widget asWidget();
}
