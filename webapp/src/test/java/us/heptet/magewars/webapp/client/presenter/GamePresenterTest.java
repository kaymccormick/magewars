package us.heptet.magewars.webapp.client.presenter;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.webapp.client.view.GameView;

public class GamePresenterTest {

    private Mockery context;
    private GameView gameView;
    private GamePresenter gamePresenter;
    private us.heptet.magewars.ui.view.GameView.Controller controller;

    @BeforeMethod
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        context =  new Mockery();
        gameView = context.mock(GameView.class);
        context.checking(new Expectations(){{
            oneOf(gameView).setPresenter(with(any(GameView.Presenter.class)));
        }});
        controller = context.mock(us.heptet.magewars.ui.view.GameView.Controller.class);
        gamePresenter = new GamePresenter(gameView, controller, null);



    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnStartButtonClicked() throws Exception {
        context.checking(new Expectations(){{
            oneOf(controller).onStartButtonClicked();
        }});
        gamePresenter.onStartButtonClicked();
        context.assertIsSatisfied();
    }
}