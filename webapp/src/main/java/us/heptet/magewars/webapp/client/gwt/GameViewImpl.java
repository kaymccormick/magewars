package us.heptet.magewars.webapp.client.gwt;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.ArenaView;
import us.heptet.magewars.ui.gwt.ControlImpl;
import us.heptet.magewars.ui.view.PlanSpellsView;
import us.heptet.magewars.webapp.client.view.GameView;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/8/2014. */
/**
 * I think this class implementing ui.view.GameView is redundant because webapp.client.view.GameView extends ui.view.GameView
 */
@Singleton
public class GameViewImpl extends ControlImpl implements GameView, us.heptet.magewars.ui.view.GameView {
    private static Logger logger = Logger.getLogger(GameViewImpl.class.getName());
    private final Button readyButton;

    private LayoutPanel layoutPanel;
    private AbsolutePanel absolutePanel;
    private boolean useAbsolutePanel = true;
    private Controller controller; // <-- this is gameController. the interface to gameControl is through gameController
    private final Label stageLabel;
    private final Label phaseLabel;
    private final Button nextPhaseButton;
    private final FlowPanel rootFlowPanel;
    private final FlowPanel topFlowPanel;
    private ArenaView arenaView;
    private UiFactory uiFactory;
    private Presenter presenter;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Create a GameViewImpl
     * @param uiFactory GwtUiFactory
     */
    @Inject
    public GameViewImpl(
            UiFactory uiFactory
            ) {

        logger.finest("In GameViewImpl constructor");
        this.uiFactory = uiFactory;

        // we won't have this yet!!

        rootFlowPanel = new FlowPanel();
        topFlowPanel = new FlowPanel();
        initWidget(rootFlowPanel);
        Button startGame = new Button("Start Game");
        startGame.addClickHandler(clickEvent -> controller.onStartButtonClicked());

        startGame.addStyleName(MyResources.INSTANCE.gss().startButton());
        topFlowPanel.add(startGame);
        stageLabel = new Label();
        stageLabel.addStyleName(MyResources.INSTANCE.gss().stageLabel());

        phaseLabel = new Label();
        phaseLabel.addStyleName(MyResources.INSTANCE.gss().phaseLabel());

        readyButton = new Button("Ready");
        readyButton.addClickHandler(event -> controller.onReadyButtonClick());

        topFlowPanel.add(readyButton);

        nextPhaseButton = new Button("Next Phase");
        nextPhaseButton.addStyleName(MyResources.INSTANCE.gss().nextPhaseButton());
        nextPhaseButton.addClickHandler(event -> {
            try {
                logger.info("calling gameplayController.nextPhase");
                controller.onNextPhaseInitiated();
                logger.info("returning gameplayController.nextPhase");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "GameController.nextPhase returned exception: " + ex.getMessage(), ex);
            }
        });


        topFlowPanel.add(nextPhaseButton);
        topFlowPanel.add(stageLabel);
        topFlowPanel.add(phaseLabel);

        rootFlowPanel.add(topFlowPanel);
        useAbsolutePanel = true;
    }

    @Override
    public void setGameSituation(GameSituation gameSituation)
    {
        //this.gameplayController.setGameSituation(gameSituation); // fix me refactoring removing gameplaycontroller
        // what about: passing the game situation to the controller and to gamecontrol


        arenaView = uiFactory.createArenaView(gameSituation.getArena());
        if(useAbsolutePanel)
        {
            absolutePanel = new AbsolutePanel();
            absolutePanel.add((Widget) arenaView.getControl(), 0, 0);

            absolutePanel.setWidth("2304px");
            absolutePanel.setHeight("1728px");
            rootFlowPanel.add(absolutePanel);
        }
        else {
            layoutPanel = new LayoutPanel();
            layoutPanel.add((Widget) arenaView.getControl());
            layoutPanel.setWidgetHorizontalPosition((Widget) arenaView.getControl(), Layout.Alignment.BEGIN);
            layoutPanel.setWidgetVerticalPosition((Widget) arenaView.getControl(), Layout.Alignment.BEGIN);
            layoutPanel.setWidgetLeftWidth((Widget) arenaView.getControl(), 0, Style.Unit.PX, 2000, Style.Unit.PX);
            layoutPanel.setWidgetTopHeight((Widget) arenaView.getControl(), 100, Style.Unit.PX, 1000, Style.Unit.PX);
            rootFlowPanel.add(layoutPanel);
        }
    }

    @Override
    public void onNewPhase(PhaseEvent gameEvent) {
        logger.fine("in onNewPhase");
        if(phaseLabel.getText().isEmpty())
        {
            phaseLabel.setText(gameEvent.getPhaseName());
            return;
        }
/*
        EventListener eventListener = DOM.getEventListener(phaseLabel.getElement());
        com.google.gwt.user.client.DOM.setEventListener(phaseLabel.getElement(),
                new EventListener() {
                    @Override
                    public void onBrowserEvent(Event e) {
                        NativeEvent ne = e.cast();
                        logger.fine(ne.getType());
                        if ("transitionend".equals(ne.getType())) {
                            DOM.setEventListener(phaseLabel.getElement(), eventListener);
*/

                        new Timer() {

                            @Override
                            public void run() {
                                phaseLabel.setText(gameEvent.getPhaseName());
                                phaseLabel.removeStyleName(MyResources.INSTANCE.gss().zeroOpacity());
                            }
                        }.schedule(2000);
/*
                            com.google.gwt.user.client.DOM.setEventListener(phaseLabel.getElement(), null);
                        } else {
                            eventListener.onBrowserEvent(e);
                        }
                    }
                });
*/
        phaseLabel.addStyleName(MyResources.INSTANCE.gss().zeroOpacity());

    }

    @Override
    public void onAddObject(GameEvent gameEvent) {
        GameObject gameObject = gameEvent.getGameObject();
        Control control = uiFactory.getGameObjectView(gameObject);
        if(control == null)
        {
            logger.warning("gameObjectView is null for " + gameObject);
            return;
        }
        arenaView.getZoneView(gameEvent.getZoneCol(), gameEvent.getZoneRow()).add(gameObject, control);

    }

    @Override
    public void onChangeInitiative(GameEvent gameEvent) {
        // update a UI element if necessary
    }

    @Override
    public void onShown() {
        /* no op */
    }

    @Override
    public void createMainMenu() {
        logger.severe("Call into unimplemented createMainMenu");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void showSpellbookViewer(PlanSpellsView view) {
        logger.fine("entering showSpellbookViewer");
        view.setVisible(true);
        if(useAbsolutePanel) {
            absolutePanel.add((Widget)view.getControl(), 150, 0);
        }
        else {
            layoutPanel.add((Widget) view.getControl());
            layoutPanel.setWidgetHorizontalPosition((Widget) view.getControl(), Layout.Alignment.BEGIN);
            layoutPanel.setWidgetVerticalPosition((Widget) view.getControl(), Layout.Alignment.BEGIN);
            layoutPanel.setWidgetLeftWidth((Widget) view.getControl(), 0, Style.Unit.PX, 1200, Style.Unit.PX);
            layoutPanel.setWidgetTopHeight((Widget) view.getControl(), 0, Style.Unit.PX, 1000, Style.Unit.PX);
            logger.fine("Added " + view + " to " + layoutPanel);
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
