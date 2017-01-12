package us.heptet.magewars.webapp.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.ui.view.View;

import java.util.List;

/**
 * Created by jade on 19/08/2016.
 */
public interface GamesView2 extends View {
     interface Presenter
    {
         void onCreateButtonClicked();
         void onRefreshButtonClicked();
    }
    HasClickHandlers getCreateButton();
    HasClickHandlers getRefreshButton();
    void setData(List<GameDetails> data);
    Widget asWidget();
    void setPresenter(GamesView.Presenter presenter);
}

