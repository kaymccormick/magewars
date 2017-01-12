package us.heptet.magewars.webapp.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import us.heptet.magewars.service.events.GameDetails;

import java.util.List;

/**
 * Created by jade on 19/08/2016.
 */
public class GamesViewImpl2 extends Composite implements GamesView2 {
    @Override
    public HasClickHandlers getCreateButton() {
        return null;
    }

    @Override
    public HasClickHandlers getRefreshButton() {
        return null;
    }

    @Override
    public void setData(List<GameDetails> data) {
    }

    @Override
    public void setPresenter(GamesView.Presenter presenter) {

    }
    @Override
    public void setId(String id) {
    }

    @Override
    public Object getControl() {
        return null;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

}
