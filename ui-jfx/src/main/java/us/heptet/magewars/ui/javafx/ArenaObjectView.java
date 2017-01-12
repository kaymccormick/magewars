package us.heptet.magewars.ui.javafx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import us.heptet.magewars.domain.game.GameObject;

import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.ui.*;
import us.heptet.magewars.ui.javafx.controller.ArenaObjectController;
import us.heptet.magewars.ui.view.CardView;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 2/4/14. */
/**
 * ArenaObject view. Main subclass is {@link ArenaCreatureView}
 * @param <T> Type of object the view presents.
 */
public class ArenaObjectView<T extends GameObject> extends SelectableRegionBase<T> {
    private static Logger logger = Logger.getLogger(ArenaObjectView.class.getName());

    private SimpleBooleanProperty staggerHorizontally = new SimpleBooleanProperty(true);
    private SimpleBooleanProperty staggerVertically = new SimpleBooleanProperty(true);
    private SimpleIntegerProperty staggerX = new SimpleIntegerProperty(72 / 4);
    private SimpleIntegerProperty staggerY = new SimpleIntegerProperty(72 / 3);

    private ArenaObjectController controller;

    private us.heptet.magewars.ui.view.CardView cardView;
    private Group attachedCardGroup = new Group();

    private String nodeIdPrefix;
    private PhaseActionHandler phaseActionHandler;

    static {
        logger.setLevel(Level.FINEST);
    }

    // ViewSupplier methods used:  createCardView and getGameObjectView
    // createCardView is for the card that represents this game object
    // getGameObjectView is for any enchantments

    /**
     * Construct an ArenaObject view.
     * @param viewManager
     * @param arenaObject
     * @param phaseActionHandler
     */
    @SuppressWarnings("unchecked")
    ArenaObjectView(
            final ViewSupplier viewManager,
            final T arenaObject,
            PhaseActionHandler phaseActionHandler
    )
    {
        super(arenaObject);
        getStyleClass().add("ArenaObjectView");
        this.phaseActionHandler = phaseActionHandler;

        assert arenaObject.getControllingPlayer() != null;
        assert arenaObject != null;
        assert arenaObject.getPlayerCard() != null;
        assert arenaObject.getPlayerCard().getCard().getCardEnum() != null;

        String s = arenaObject.getPlayerCard().getCard().getIdPrefix();
        nodeIdPrefix = String.format("player-%d-%s-", arenaObject.getControllingPlayer().getPlayerIndex() + 1, s);

        setId(nodeIdPrefix + "arena-creature-view");
        logger.fine("node id is {}" + getId());

        cardView = viewManager.createCardView(getGameElement());
        assert cardView != null;
        cardView.setId(nodeIdPrefix + "card-view");
        //setBackground(new Background(new BackgroundImage(cardView.getImageView().getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false))));
        add(attachedCardGroup);

        // Actual JavaFX node is now abstracted so must use getControl to get at it
        add((Node)cardView.getControl());
    }

    void add(Node node)
    {
        getChildren().add(node);
    }

    @Override
    protected double computePrefWidth(double v) {
        return ((Region)cardView.getControl()).getPrefWidth() + (getStaggerHorizontally() ? attachedCardGroup.getChildren().size() * getStaggerX() : 0);
    }

    @Override
    protected double computePrefHeight(double v) {
        return ((Region)cardView.getControl()).getPrefHeight() + (getStaggerVertically() ? attachedCardGroup.getChildren().size() * getStaggerY() : 0);

    }

    @Override
    protected void layoutChildren()
    {
        double offsetX = getStaggerHorizontally() ? getStaggerX() : 0;
        double offsetY = getStaggerVertically() ? getStaggerY() : 0;

        super.layoutChildren();
        int max = attachedCardGroup.getChildren().size();
        for(int i = 0; i < max; i++)
        {
            Region x = (Region)attachedCardGroup.getChildren().get(i);

            x.relocate(i * offsetX, i * offsetY);
        }
        ((Region)cardView.getControl()).relocate(max * offsetX, max * offsetY);
    }

    public boolean getStaggerHorizontally() {
        return staggerHorizontally.get();
    }

    public void setStaggerHorizontally(boolean staggerHorizontally) {
        this.staggerHorizontally.set(staggerHorizontally);
    }

    public boolean getStaggerVertically() {
        return staggerVertically.get();
    }

    public void setStaggerVertically(boolean staggerVertically) {
        this.staggerVertically.set(staggerVertically);
    }

    public int getStaggerX() {
        return staggerX.get();
    }

    public void setStaggerX(int staggerX) {
        this.staggerX.set(staggerX);
    }

    public int getStaggerY() {
        return staggerY.get();
    }

    public void setStaggerY(int staggerY) {
        this.staggerY.set(staggerY);
    }

    public CardView getCardView() {
        return cardView;
    }

    public Region getCardViewRegion() {
        return (Region) getCardView().getControl();

    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public String getNodeIdPrefix() {
        return nodeIdPrefix;
    }

    public void setNodeIdPrefix(String nodeIdPrefix) {
        this.nodeIdPrefix = nodeIdPrefix;
    }

    /***
     * Call upon actions complete.
     */
    public void actionsComplete() {
        /* no-op */
    }

    public PhaseActionHandler getPhaseActionHandler() {
        return phaseActionHandler;
    }

    public ArenaObjectController getController() {
        return controller;
    }

    public void setController(ArenaObjectController controller) {
        this.controller = controller;
    }

    /**
     * Add attached cards to the view.
     * @param view
     */
    public void addAttachedCard(ArenaObjectView view)
    {
        attachedCardGroup.getChildren().add(view);
    }
}
