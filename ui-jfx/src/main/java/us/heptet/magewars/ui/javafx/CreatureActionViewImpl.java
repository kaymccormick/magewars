package us.heptet.magewars.ui.javafx;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Attack;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.Spell;
import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.game.action.*;
import us.heptet.magewars.game.phase.CreatureActionPhase;
import us.heptet.magewars.game.action.UiAction;
import us.heptet.magewars.ui.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 2/22/14. */
/**
 * Creature Action View.
 */
@Component
public class CreatureActionViewImpl extends Region implements us.heptet.magewars.ui.CreatureActionView {
    private static Logger logger = Logger.getLogger("CreatureActionViewLogger");
    private SimpleObjectProperty<CreatureActionPhase> creatureActionPhase = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Player> actingPlayer = new SimpleObjectProperty<>();
    private VBox actionBox;
    private SimpleObjectProperty<ArenaCreature> arenaCreature = new SimpleObjectProperty<>();
    private SimpleObjectProperty<UiAction> currentAction = new SimpleObjectProperty<>();
    private MenuButton quickAttackMenuButton;
    PhaseActionHandler phaseActionHandler;

    // there isn't consistency or logic behind what is a property versus an expression
    // thoughts: there are contexts where the result of an action isn't known when
    //   the button is clicked -- clicking the button merely initiates the action
    //   and more user input is required for completion. In this case, the completion of
    //   the action is performed by the subclass of UiAction, which has no knowledge
    //   of how it was initiated and no reference to the CreatureActionView instance.
    //   GameEvent is one way to communicate this, but it is a cumbersome mechanism
    //   that requires listening to all events and programmatically confirming the event
    //   matches the action requested.
    //   another method would be a callback interface indicating completion - probably
    //   more effective.

    // it's also unclear what belongs on CreatureActionPhase versus CreatureActionView
    // there is a good argument for all of them being on CreatureActionPhase
    private SimpleBooleanProperty initialMovePassed = new SimpleBooleanProperty();
    private SimpleBooleanProperty initialMoveTaken = new SimpleBooleanProperty();
    private SimpleBooleanProperty quickActionTaken = new SimpleBooleanProperty();
    private SimpleBooleanProperty fullActionTaken = new SimpleBooleanProperty();

    private BooleanExpression haveCreature;
    private BooleanExpression quickActionReady;

    private Button moveButton;
    private Button doNotMoveButton;
    private MenuButton quickSpellMenuButton;
    private Button guardButton;
    private Button doNothingButton;
    private MenuButton fullAttackMenuButton;
    private MenuButton fullSpellMenuButton;
    private MenuButton fullSpecialActionMenuButton;
    private boolean prepared = false;


    static {
        logger.setLevel(Level.FINEST);
    }
    @Override
    @SuppressWarnings("unchecked")
    public void prepare()
    {
        logger.info("in prepare");
        List<ButtonBase> buttons = new ArrayList<>();
        setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));

        haveCreature = Bindings.isNotNull(arenaCreatureProperty());

        // for new semantics of this value is wrong
        // also, for consistencies sake, not all boolean expressions are bound to properties on
        // the creature action phase but this should be consistent
        /*
        initialMoveTaken = new IndirectBooleanBinding<CreatureActionPhase>(creatureActionPhaseProperty(),
                new Function<CreatureActionPhase, BooleanExpression>() {
                    @Override
                    public BooleanExpression apply(CreatureActionPhase creatureActionPhase) {
                        return creatureActionPhase.initialMoveCompletedProperty();
                    }
                }, true);
        */
        // binding manager was an experiment
        //BindingManager bindingManager = new BindingManager();

        //final BooleanExpression initialMoveTaken = null, initialMovePassed = null;
        quickActionReady = Bindings.or(initialMoveTaken, initialMovePassed);

        String idPrefix = "creature-action-view-";
        moveButton = new Button("Move");
        moveButton.textProperty().bind(Bindings.when(initialMoveTaken).then("Move Again").otherwise("Move"));
        moveButton.setId(idPrefix + "move-button");
        logger.fine("move button id is " + moveButton.getId());
        moveButton.disableProperty().bind(haveCreature.not().or(quickActionTaken.or(fullActionTaken)));
        //moveButton.setUserData(bindingManager);

        buttons.add(moveButton);

        //moveButton.disableProperty().bind(haveCreature.not().or(moveCompleted));
        moveButton.setOnAction(actionEvent -> {
            // this starts the move action but does not complete it.
            logger.fine("Move button clicked");
            MoveUiAction moveUiAction = new MoveUiAction(getCreatureActionPhase(), getActingPlayer(), getArenaCreature(),
//                        phaseActionHandler::consumeAction
                    action -> phaseActionHandler.consumeAction(action)
            );
            moveUiAction.setHandler((uiAction, success) -> {
                logger.fine("in handler");

                if(success) {
                    initialMoveTaken.set(true);
                }
            });
            moveUiAction.initiateAction();
            setCurrentAction(moveUiAction);
//                selectionStateProperty().bind(moveUiAction.selectionStateProperty());
        });

        doNotMoveButton = new Button("Do Not Move");
        doNotMoveButton.setId(idPrefix + "do-not-move-button");
        doNotMoveButton.disableProperty().bind(
                initialMoveTaken.or(quickActionTaken.or(fullActionTaken)));
        buttons.add(doNotMoveButton);
        //doNotMoveButton.disableProperty().bind(haveCreature.not().or(initialMoveTaken));
        doNotMoveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getCreatureActionPhase().setInitialMovePassed(true);

            }
        });



        quickAttackMenuButton = new MenuButton("Quick Attack");
        quickAttackMenuButton.setId(idPrefix + "quick-attack-menu-button");
        buttons.add(quickAttackMenuButton);
        quickAttackMenuButton.disableProperty().bind(
                haveCreature.not().or(initialMoveTaken.or(initialMovePassed).not().or(Bindings.isEmpty(quickAttackMenuButton.getItems()))));

        quickSpellMenuButton = new MenuButton("Quick Spell");
        quickSpellMenuButton.setId(idPrefix + "quick-spell-menu-button");
        buttons.add(quickSpellMenuButton);
        quickSpellMenuButton.disableProperty().bind(haveCreature.not().or(initialMoveTaken.not().or(Bindings.isEmpty(quickSpellMenuButton.getItems()))));

        guardButton = new Button("Guard");
        guardButton.setId(idPrefix + "guard-button");
        buttons.add(guardButton);
        guardButton.disableProperty().bind(haveCreature.not().or(initialMoveTaken.not()));
        guardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArenaCreature arenaCreature1 = getArenaCreature();

            }
        });

        doNothingButton = new Button("Do Nothing");
        doNothingButton.setId(idPrefix + "do-nothing-button");
        doNothingButton.disableProperty().bind(haveCreature.not());
        buttons.add(doNothingButton);
        doNothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // this is dysfunctional
                //viewManager.getGameObjectView(getArenaCreature()).actionsComplete();
                // this is only about removing the creatureactionview from the scene graph, lame!
                //phaseActionHandler.completeActions();
                setVisible(false);

                phaseActionHandler.passAction();
                setArenaCreature(null);
                //getCreatureActionPhase().setReadyToComplete(true);
            }
        });

        fullAttackMenuButton = new MenuButton("Full Attack");
        fullAttackMenuButton.setId(idPrefix + "full-attack-menu-button");

        buttons.add(fullAttackMenuButton);
        // Full Attack is disabled if there's no creature or the attack menu is empty
        fullAttackMenuButton.disableProperty().bind(haveCreature.not().or(
                Bindings.isEmpty(fullAttackMenuButton.getItems())));

        fullSpellMenuButton = new MenuButton("Full Spell");
        fullSpellMenuButton.setId(idPrefix + "full-spell-menu-button");

        buttons.add(fullSpellMenuButton);
        fullSpellMenuButton.disableProperty().bind(
                haveCreature.not().or(Bindings.isEmpty(fullSpellMenuButton.getItems())));

        fullSpecialActionMenuButton = new MenuButton("Full Special Action");
        fullSpecialActionMenuButton.setId(idPrefix + "full-special-action-button");

        buttons.add(fullSpecialActionMenuButton);
        fullSpecialActionMenuButton.disableProperty().bind(haveCreature.not().or(Bindings.isEmpty(fullSpecialActionMenuButton.getItems())));

        arenaCreatureProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null)
            {
                return;
            }
            // reset properties
            initialMovePassed.set(false);
            logger.info("Setting initialMoveTaken to false");
            initialMoveTaken.set(false);
            quickActionTaken.set(false);
            fullActionTaken.set(false);

            final ArenaCreature finalArenaCreature = newValue;
            quickAttackMenuButton.getItems().clear();
            fullAttackMenuButton.getItems().clear();
            Iterator<Attack> attacks = newValue.getAttacks();
            while(attacks.hasNext())
            {
                Attack a = attacks.next();
                final Attack attack = a;
                    MenuItem attackMenuItem = new MenuItem(a.getAttackName());
                // TODO Refactor handler out of view.
                    attackMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            AttackUiAction attackUiAction = new AttackUiAction(
                                getCreatureActionPhase(),
                                getActingPlayer(), finalArenaCreature, attack,
                                    action -> getCreatureActionPhase().consumeAction(action)
                                    //getCreatureActionPhase()::consumeAction
                                    );

                        //selectionStateProperty().bind(attackUiAction.selectionStateProperty());

                        attackUiAction.initiateAction();
                            setCurrentAction(attackUiAction);
                    }});
                if(a.isQuickAttack())
                {
                    quickAttackMenuButton.getItems().add(attackMenuItem);
                }else {
                    fullAttackMenuButton.getItems().add(attackMenuItem);
                }
            }
            quickSpellMenuButton.getItems().clear();
            fullSpellMenuButton.getItems().clear();
            List<Spell> spells = newValue.getSpells();
            for(final Spell spell:spells)
            {
                MenuItem spellMenuItem = new MenuItem(spell.getSpellName());

                if(spell.isQuickspell())
                {
                    quickSpellMenuButton.getItems().add(spellMenuItem);
                } else {
                    logger.fine("Adding spell " + spell.getSpellName() + " to full spell menu.");
                    fullSpellMenuButton.getItems().add(spellMenuItem);
                    spellMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            CastUiAction action = new CastUiAction(
                                    null,
                                    getActingPlayer(),
                                    getArenaCreature(),
                                    action1 -> phaseActionHandler.consumeAction(action1)
                                    //phaseActionHandler::consumeAction
                                    );

                            action.setSpell(spell);
                            action.initiateAction();
                            //playerrefactor
                            //                                getActingPlayer().getPlayerGameState().setCurrentAction(action);
                            actionEvent.consume();
                        }
                    });
                }
            }
             });
//        quickAttackButton.disableProperty().bind(((CreatureActionPhase)game.getCurrentPhase()).quickActionEnabledProperty().not());
        //        guardButton.disableProperty().bind(((CreatureActionPhase)game.getCurrentPhase()).quickActionEnabledProperty().not());

        // here we want to bind on a property of a subtype of Phase
        // unfortunately, currentPhaseProperty.get returns the Phase class.



        actionBox = new VBox(0);
        //actionBox.visibleProperty().bind(Bindings.equal(arenaCreature, getGameSituation().getUiState().activeCreatureProperty()));
       //1.8 buttons.stream().forEach(x -> { x.setMaxWidth(Double.MAX_VALUE);  x.setTextFill(Color.WHITE); });
        actionBox.getChildren().addAll(buttons);

        getChildren().add(actionBox);
        prepared = true;
    }

    public MenuButton getQuickAttackMenuButton() {
        return quickAttackMenuButton;
    }

    public void setQuickAttackMenuButton(MenuButton quickAttackMenuButton) {
        this.quickAttackMenuButton = quickAttackMenuButton;
    }

    @Override
    public ArenaCreature getArenaCreature() {
        return arenaCreature.get();
    }

    private SimpleObjectProperty<ArenaCreature> arenaCreatureProperty() {
        return arenaCreature;
    }

    @Override
    public void setArenaCreature(ArenaCreature arenaCreature) {
        this.arenaCreature.set(arenaCreature);
    }

    @Override
    public CreatureActionPhase getCreatureActionPhase() {
        return creatureActionPhase.get();
    }

    @Override
    public void setCreatureActionPhase(CreatureActionPhase creatureActionPhase) {
        this.creatureActionPhase.set(creatureActionPhase);
    }

    @Override
    public Player getActingPlayer() {
        return actingPlayer.get();
    }

    public void setActingPlayer(Player actingPlayer) {
        this.actingPlayer.set(actingPlayer);
    }
    @Override
    public UiAction getCurrentAction() {
        return currentAction.get();
    }

    private void setCurrentAction(UiAction currentAction) {
        this.currentAction.set(currentAction);
    }


    @Override
    public Boolean getHaveCreature() {
        return haveCreature.get();
    }

    public Button getMoveButton() {
        return moveButton;
    }

    public Button getDoNotMoveButton() {
        return doNotMoveButton;
    }

    public MenuButton getQuickSpellMenuButton() {
        return quickSpellMenuButton;
    }

    public Button getGuardButton() {
        return guardButton;
    }

    public Button getDoNothingButton() {
        return doNothingButton;
    }

    public MenuButton getFullAttackMenuButton() {
        return fullAttackMenuButton;
    }

    public MenuButton getFullSpellMenuButton() {
        return fullSpellMenuButton;
    }

    public MenuButton getFullSpecialActionMenuButton() {
        return fullSpecialActionMenuButton;
    }

    @Override
    public boolean isPrepared() {
        return prepared;
    }

    @Override
    public PhaseActionHandler getPhaseActionHandler() {
        return phaseActionHandler;
    }

    @Override
    public void setPhaseActionHandler(PhaseActionHandler phaseActionHandler) {
        this.phaseActionHandler = phaseActionHandler;
    }

    @Override
    public boolean getInitialMovePassed() {
        return initialMovePassed.get();
    }

    @Override
    public boolean getInitialMoveTaken() {
        return initialMoveTaken.get();
    }

    @Override
    public boolean getQuickActionTaken() {
        return quickActionTaken.get();
    }

    @Override
    public boolean getFullActionTaken() {
        return fullActionTaken.get();
    }

    @Override
    public Boolean getQuickActionReady() {
        return quickActionReady.get();
    }

    @Override
    public Object getControl() {
        return this;
    }
}
