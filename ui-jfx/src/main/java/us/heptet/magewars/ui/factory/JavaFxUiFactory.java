package us.heptet.magewars.ui.factory;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.ui.*;
import us.heptet.magewars.ui.javafx.*;
import us.heptet.magewars.ui.view.CardView;
import us.heptet.magewars.ui.generic.ArenaViewImpl;
import us.heptet.magewars.ui.view.GameView;

import javax.inject.Inject;

/* Created by kay on 4/9/2014. */
/**
 * JavaFX implementation class for {@link UiFactory} interface.
 * <h2>Dependencies</h2>
 * <ul>
 *     <li>{@link CardImageManager}</li>
 *     <li>{@link ViewSupplier}</li>
 *     <li>{@link CardViewFactory}</li>
 * </ul>
 */
@Component
public class JavaFxUiFactory extends GenericUiFactory implements UiFactory {
    private CardImageManager cardImageManager;
    private ViewSupplier viewManager;
    private CardViewFactory cardViewFactory;

    /***
     * Create an instance of the JavaFxUiFactory.
     * @param cardImageManager
     * @param viewManager
     * @param cardViewFactory
     */
    @Inject
    public JavaFxUiFactory(CardImageManager cardImageManager, ViewSupplier viewManager, CardViewFactory cardViewFactory) {
        this.cardImageManager = cardImageManager;
        this.viewManager = viewManager;
        this.cardViewFactory = cardViewFactory;
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
        Pane pane = new StackPane();
        return new ContainerImpl<>(pane);
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
        return viewManager.getZoneView(zone, this);
    }

    @Override
    public ArenaView createArenaView(Arena arena) {
        return new ArenaViewImpl(this, arena);
    }

    @Override
    public GameView createGameView() {
        // gameview is not in the right module for this!!
        throw new UnsupportedOperationException();
    }

    @Override
    public Control getGameObjectView(GameObject gameObject) {
        return viewManager.getGameObjectView(gameObject);
    }

    @Override
    public void setCardViewImage(CardView cardView, CardEnum cardEnum)
    {
        assert cardImageManager != null;
        ((JavaFxCardView) cardView).getImageView().setImage(cardImageManager.getCardImage(cardEnum));
    }

    @Override
    public CardView createCardView(PlayerCard card) {
        return cardViewFactory.createCardView(card);
    }

    @Override
    public CardView createCardView(GameObject arenaObject) {
        return cardViewFactory.createCardView(arenaObject);
    }

    @Override
    public CardView createCardView() {
        return cardViewFactory.createCardView();
    }
}
