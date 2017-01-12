package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import junit.framework.Assert;
import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.events.EventHandler;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.EventType;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.GameServiceAsync;
import us.heptet.magewars.webapp.client.view.GamesView;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class GamesPresenterTest {
    private static Logger logger = LoggerFactory.getLogger(GamesPresenterTest.class);
    Mockery context;
    GamesPresenter gamesPresenter;
    GamesView gamesView;
    GameServiceAsync gameServiceAsync;
    EventManager eventManager ;

    public class ActionClickHandlers implements Action {
        private ClickHandler handler;

        public void giving(ClickEvent event) {
            checkCallback();
            handler.onClick(event);
        }


        public void describeTo(Description description) {
            description.appendText("requests an async callback");
        }

        private void checkCallback() {
            if (handler == null) {
                Assert.fail("handler not scheduled");
            }
        }

        @SuppressWarnings("unchecked")
        public Object invoke(Invocation invocation) throws Throwable {
            handler = (ClickHandler)invocation.getParameter(0);
            return null;
        }
    }
    @BeforeMethod
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        context =  new Mockery();
        //context.setImposteriser(ClassImposteriser.INSTANCE);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        gamesView = context.mock(GamesView.class);
        gameServiceAsync  = context.mock(GameServiceAsync.class);
        eventManager = context.mock(EventManager.class);

        context.checking(new Expectations(){{
            oneOf(eventManager).addEventHandler(with(any(EventType.class)), with(any(EventHandler.class)));
            oneOf(gamesView).setPresenter(with(any(GamesView.Presenter.class)));
        }
        });
        gamesPresenter = new GamesPresenter(null, gamesView, gameServiceAsync, eventManager);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGo() throws Exception {
        final Container container = context.mock(Container.class);
        final AsyncAction<AllGamesEvent> asyncAction = new AsyncAction<>();
        final ActionClickHandlers actionClickHandlers = new ActionClickHandlers();
        final ActionClickHandlers refreshClickHandlersAction = new ActionClickHandlers();
        final HasClickHandlers refreshClickHandlers = context.mock(HasClickHandlers.class, "refreshClickHandlers");
        final HasClickHandlers createClickHandlers = context.mock(HasClickHandlers.class, "createClickHandlers");
        final List<GameDetails> gameDetailsList = Collections.singletonList(new GameDetails(1, "my game", "me", 2, 2));
        context.checking(new Expectations(){{
            oneOf(container).clear();
            oneOf(container).add(gamesView);
            atLeast(1).of(gameServiceAsync).requestAllGames(with(any(RequestAllGamesEvent.class)),
                    with(any(AsyncCallback.class)));
            will(asyncAction);
            oneOf(gamesView).setData(with(same(gameDetailsList)));
        }});
        gamesPresenter.go(container);
        AllGamesEvent allGamesEvent = new AllGamesEvent(gameDetailsList);
        asyncAction.succeedGiving(allGamesEvent);
        context.assertIsSatisfied();
    }

    @Test
    public void testName() throws Exception {


    }
}