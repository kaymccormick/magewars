package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import static org.easymock.EasyMock.*;
import junit.framework.TestCase;
import org.junit.Test;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.webapp.client.GameServiceAsync;
import us.heptet.magewars.webapp.client.view.TableView;

public class GwtTestTablePresenter extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testName() throws Exception {
        HandlerManager eventBus = createMock(HandlerManager.class);
        TableView tv = createMock(TableView.class);
        GameServiceAsync gs = createMock(GameServiceAsync.class);
        CardSet cardSet = new BaseCardSet();
        EventManager eventManager = createMock(EventManager.class);
        TablePresenter tp = new TablePresenter(tv, gs, cardSet, eventManager);
    }
}