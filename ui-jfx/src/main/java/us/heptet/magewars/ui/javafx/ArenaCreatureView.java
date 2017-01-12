package us.heptet.magewars.ui.javafx;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Spell;
import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.game.action.QuickcastUiAction;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 2/11/14. */
/**
 *
 */
class ArenaCreatureView<T extends ArenaCreature> extends ArenaObjectView<T> {
    private static Logger logger = Logger.getLogger(ArenaCreatureView.class.getName());
    // note - no IOC here.
    private ActionMarker actionMarker = new ActionMarker();
    private QuickcastMarker quickcastMarker;
    private HBox actionHBox ;
    private CreatureActionViewImpl actionView;

    static {
        logger.setLevel(Level.FINEST);
    }

    ArenaCreatureView(
            final ViewManager viewManager,
            final T arenaCreature,
            //ArenaCreature<Creature, us.heptet.magewars.domain.game.ArenaCreature> arenaCreature,
            final PhaseActionHandler phaseActionHandler
    )
    {
        super(viewManager, arenaCreature, phaseActionHandler);
        assert phaseActionHandler != null;

        AnchorPane anchorPane = new AnchorPane();
        us.heptet.magewars.domain.game.Player player = arenaCreature.getControllingPlayer();
        logger.fine("Creating arenaCreatureView [" + player + "] for " + arenaCreature);

        /*
         * Not sure how to control the behavior of the "action marker" - It can only be selected
          * when the game is in the Creature action phase, and when the owning player has control, and (of course)
          * when it hasn't already been activated during the round.
         */
        actionMarker.setId(getNodeIdPrefix() + "action-marker");
        // removed commented out code ID ArenaCreatureView-1
        add(actionMarker);
        if(getGameElement().hasQuickcastAbility())
        {
            logger.fine("[" + this + "] gameElement (" + getGameElement() + ") has Quickcast Ability. Adding quickcast marker.");
            quickcastMarker = new QuickcastMarker();
            quickcastMarker.setId(getNodeIdPrefix() + "quickcast-marker");
            logger.fine("quickcast id is " + quickcastMarker.getId());

            quickcastMarker.setOnMousePressed(new MouseEventEventHandler(phaseActionHandler));
            add(quickcastMarker);
        }
        else
        {
            logger.fine("[" + this + "] gameElement (" + getGameElement() + ") does not have Quickcast Ability. Not adding quickcast marker.");
        }
        actionMarker.setPickOnBounds(false);
        //playerrefactor
        //actionMarker.activeProperty().bindBidirectional(arenaCreature.activeProperty());

        DamageCounter damageCounter = new DamageCounter();
  //      MouseControlUtil.makeDraggable(damageCounter);

        getChildren().add(damageCounter);
        //playerrefactor
        //damageCounter.damageAmountProperty().bind(arenaCreature.damageProperty());

        // this event handler needs to know what the "selection mode" is. (model / state, likely state)
        // it can do this through a (likely bound) property on the object
        // or it can hold a reference to an intermediary object (either Game or a non-Game object)
        // that holds the relevant information
        //
    }

    @Override
    void add(Node node) {
        getChildren().add(node);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        actionMarker.relocate(getCardViewRegion().getBoundsInParent().getMaxX() - actionMarker.getLayoutBounds().getWidth() - 20,
                getCardViewRegion().getBoundsInParent().getMaxY() - actionMarker.getLayoutBounds().getHeight() - 20);
        if(quickcastMarker != null)
        {
        quickcastMarker.relocate(getCardViewRegion().getBoundsInParent().getMaxX() - actionMarker.getLayoutBounds().getWidth() - 20,
                getCardViewRegion().getBoundsInParent().getMaxY() - actionMarker.getLayoutBounds().getHeight() - quickcastMarker.getLayoutBounds().getHeight() - 30);
        }
    }

    @Override
    public void actionsComplete() {
        super.actionsComplete();
        logger.fine("in actionsComplete (does this get called?");
        getChildren().remove(actionView);
    }

    CreatureActionViewImpl _testGetCreatureActionView()
    {
        return actionView;
    }

    private class MouseEventEventHandler implements EventHandler<MouseEvent> {
        private final PhaseActionHandler phaseActionHandler;

        public MouseEventEventHandler(PhaseActionHandler phaseActionHandler) {
            this.phaseActionHandler = phaseActionHandler;
        }

        @Override
        public void handle(MouseEvent event) {
            //arenaCreature.quickcastAvailableProperty().setPlayerCard(false);
            if(event.getButton() == MouseButton.SECONDARY)
            {
                logger.fine("Secondary button clicked, passing quickcast.");
                phaseActionHandler.passAction();
                return;
            }

            //playerrefactor
            /*final PlannedSpells plannedSpells = getGameElement().getControllingPlayer().getPlayerGameState().getPlannedSpells();

            if(!(plannedSpells.getFirstSpell().getCard().isQuickSpell() || plannedSpells.getSecondSpell().getCard().isQuickSpell()))
            {
                return;
            }
            */

            final Tooltip t = new Tooltip();

            final GridPane gridPane = new GridPane();
            t.setGraphic(gridPane);
            // removed commented out code ArenaCreatureView-5

            final EventHandler<MouseEvent> handleQuickCast = new HandleQuickcast(gridPane, t, getGameElement());
            // removed commented out code ArenaCreatureView-2
            // removed commented out code ArenaCreatureView-3

            t.show(ArenaCreatureView.this, 0, 0);
            // removed commented out code ArenaCreatureView-4
            event.consume();

        }

        private class HandleQuickcast implements EventHandler<MouseEvent> {
            private final GridPane gridPane;
            private final Tooltip t;
            private T gameElement;

            public HandleQuickcast(GridPane gridPane, Tooltip t, T gameElement) {
                this.gridPane = gridPane;
                this.t = t;
                this.gameElement = gameElement;
            }

            @Override
            public void handle(MouseEvent event1) {
                logger.fine("player for quickcast ui action is " + gameElement.getPlayerCard().getPlayer());
                QuickcastUiAction action = new QuickcastUiAction(null, //getGameSituation().getCurrentPhase(),
                        gameElement.getPlayerCard().getPlayer(), gameElement,
                        phaseActionHandler::consumeAction
                );
                action.setSpell((Spell) ((us.heptet.magewars.ui.view.CardView) event1.getTarget()).getPlayerCard());
                action.initiateAction();
                //playerrefactor
                //                            getGameElement().getControllingPlayer().getPlayerGameState().setCurrentAction(action);
                ArenaCreatureView.this.getChildren().remove(gridPane);
                t.hide();
                event1.consume();
            }
        }
    }
}
