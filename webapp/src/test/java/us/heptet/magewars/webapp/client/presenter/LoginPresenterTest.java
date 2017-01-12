package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.events.EventManager;
import us.heptet.magewars.game.events.NavigationEvent;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.LoginServiceAsync;
import us.heptet.magewars.webapp.client.view.LoginView;


public class LoginPresenterTest /* extends PowerMockTestCase*/  {
    private static Logger logger = LoggerFactory.getLogger(GamesPresenterTest.class);
    Mockery context;
    LoginPresenter loginPresenter;
    LoginView loginView;
    LoginServiceAsync loginServiceAsync;
    EventManager eventManager;

    @BeforeMethod
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        context =  new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        loginView = context.mock(LoginView.class);
        loginServiceAsync = context.mock(LoginServiceAsync.class);
        eventManager = context.mock(EventManager.class);
        context.checking(new Expectations(){{
            oneOf(loginView).setPresenter(with(any(LoginView.Presenter.class)));
        }});
        loginPresenter = new LoginPresenter(loginView, loginServiceAsync, eventManager);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGo() throws Exception {
        final Container container = context.mock(Container.class);
        context.checking(new Expectations(){{
            oneOf(container).clear();
            oneOf(container).add(with(same(loginView)));
        }});
        loginPresenter.go(container);
        context.assertIsSatisfied();
    }

    @Test
    //@PrepareForTest({History.class, GWT.class})
    @SuppressWarnings("unchecked")
    public void testOnLoginButtonClicked() throws Exception {
        final AsyncAction<Void> asyncAction = new AsyncAction<>();
        final TakesValue status = context.mock(TakesValue.class);
        final HasValue usernameValue = context.mock(HasValue.class, "username");
        final HasValue passwordValue = context.mock(HasValue.class, "password");
        final String username = "user";
        final String password = "password";
        final String classname = History.class.getName();
        //MockRepository.addSuppressStaticInitializer(classname);
        //PowerMock.mockStatic(History.class, History.class.getMethod("newItem", String.class));
        //PowerMock.mockStatic(GWT.class, GWT.class.getMethod("create", Class.class));
        context.checking(new Expectations() {{
            oneOf(loginView).getStatus();
            will(returnValue(status));
            atLeast(1).of(status).setValue(with(any(String.class)));
            oneOf(loginView).getUsername();
            will(returnValue(usernameValue));
            oneOf(usernameValue).getValue();
            will(returnValue(username));
            oneOf(loginView).getPassword();
            will(returnValue(passwordValue));
            oneOf(passwordValue).getValue();
            will(returnValue(password));
            oneOf(loginServiceAsync).login(with(equal(username)), with(equal(password)),
                    with(any(AsyncCallback.class)));
            will(asyncAction);
            atLeast(1).of(loginView).setStatusVisible(true);
            oneOf(eventManager).fireEvent(with(any(NavigationEvent.class)));
            oneOf(eventManager).resetEventChannels();
        }});
        loginPresenter.onLoginButtonClicked();
        asyncAction.succeedGiving(null);
    }
}