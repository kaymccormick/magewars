package us.heptet.magewars.ui.factory;

import com.google.inject.Singleton;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.ui.*;
import us.heptet.magewars.ui.generic.ArenaViewImpl;
import us.heptet.magewars.ui.gwt.ButtonImpl;
import us.heptet.magewars.ui.gwt.CardViewImpl;
import us.heptet.magewars.ui.gwt.ContainerImpl;
import us.heptet.magewars.ui.gwt.GameObjectView;
import us.heptet.magewars.ui.gwt.GridImpl;
import us.heptet.magewars.ui.gwt.LabelImpl;
import us.heptet.magewars.ui.gwt.VerticalBoxImpl;
import us.heptet.magewars.ui.gwt.ZoneViewImpl;
import us.heptet.magewars.ui.view.CardView;
import us.heptet.magewars.ui.view.GameView;

import java.io.Serializable;

/* Created by kay on 4/9/2014. */
/**
 * GWT UI Factory
 */
@Singleton
public class GwtUiFactory extends GenericUiFactory implements UiFactory, GameObjectViewFunction, Serializable {
    public GwtUiFactory() {
        /* nothing to do */
    }

    @Override
    public Button createButton(String text) {
        return new ButtonImpl(text);
    }

    @Override
    public Grid createGrid(int rows, int cols) {
        return new GridImpl(rows, cols);
    }

    @Override
    public Container createControlContainer() {
        return new ContainerImpl();
    }

    @Override
    public VerticalBox createVerticalBox() {

        return new VerticalBoxImpl();
    }

    @Override
    public Label createLabel(String text) {
        return new LabelImpl(text);
    }

    @Override
    public ZoneView createZoneView(Zone zone) {
        ZoneView zoneView = new ZoneViewImpl(zone, this);
        zoneView.setId("zone" + zone.getCol() + "-" + zone.getRow());
        return zoneView;
    }

    @Override
    public ArenaView createArenaView(Arena arena) {
        assert arena != null : "Arena cannot be null";
        return new ArenaViewImpl(this, arena);
    }

    @Override
    public GameView createGameView() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCardViewImage(CardView cardView, CardEnum cardEnum) {
        assert false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CardView createCardView(PlayerCard card) {
        return new CardViewImpl(card);
    }

    @Override
    public CardView createCardView(GameObject arenaObject) {
        return null;
    }

    @Override
    public CardView createCardView() {
        return new CardViewImpl();
    }

    @Override
    public Control getGameObjectView(GameObject gameObject) {
        return new GameObjectView(gameObject);
    }
}
