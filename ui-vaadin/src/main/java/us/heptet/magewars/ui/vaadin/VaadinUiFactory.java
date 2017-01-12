package us.heptet.magewars.ui.vaadin;

import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.game.Arena;
import us.heptet.magewars.ui.*;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.view.CardView;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class
        VaadinUiFactory implements UiFactory {
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
    public ZoneView createZoneView(us.heptet.magewars.game.Zone zone) {
        return null;
    }

    @Override
    public ArenaView createArenaView(Arena arena) {
        return null;
    }

    @Override
    public CardView createCardView(PlayerCard card) {
        return new CardViewImpl(card);
    }

    @Override
    public CardView createCardView(GameObject arenaObject) {
        return createCardView(arenaObject.getPlayerCard());
    }

    @Override
    public CardView createCardView() {
        return null;
    }

    @Override
    public Control getGameObjectView(us.heptet.magewars.game.GameObject gameObject) {
        return null;
    }
}
