package us.heptet.magewars.ui.factory;

import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.ui.ArenaView;
import us.heptet.magewars.ui.Button;
import us.heptet.magewars.ui.CardViewFactory;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.ui.GameObjectViewFunction;
import us.heptet.magewars.ui.Grid;
import us.heptet.magewars.ui.Label;
import us.heptet.magewars.ui.VerticalBox;
import us.heptet.magewars.ui.ZoneView;
import us.heptet.magewars.ui.view.CardView;
import us.heptet.magewars.ui.view.GameView;
import us.heptet.magewars.ui.view.MultiCardView;

/* Created by kay on 4/9/2014. */
/**
 * UiFactory interface that is responsible for creating View instances.
 */
public interface UiFactory extends CardViewFactory, GameObjectViewFunction {
    /***
     * Create a button.
     * @param text Text to display on the button surface.
     * @return
     */
    Button createButton(String text);

    /***
     * Create a Grid container.
     * @param rows Number of rows.
     * @param cols Number of columns.
     * @return Grid view.
     */
    Grid createGrid(int rows, int cols);

    /***
     * Create a generic control container.
     * @return
     */
    Container createControlContainer();

    /***
     * Create a vertical box container.
     * @return Vertical box container.
     */
    VerticalBox createVerticalBox();

    /***
     * Create a label.
     * @param text Text to display on the label surface.
     * @return
     */
    Label createLabel(String text);

    /***
     * Create a Zone View.
     * @param zone The underlying Zone for the ZoneView.
     * @return The zone view.
     */
    ZoneView createZoneView(Zone zone);

    /***
     * Create an Arena View.
     * @param arena The underlying Arena for the ArenaView.
     * @return The arena view.
     */
    ArenaView createArenaView(Arena arena);

    /**
     * Create a game view (unused).
     * @return The game view.
     */
    GameView createGameView();

    /***
     * Set an image to display in the given Card View.
     * @param cardView The card view.
     * @param cardEnum The CardEnum value associated with the image.
     */
    void setCardViewImage(CardView cardView, CardEnum cardEnum);

    /***
     * Create a "multicard" view - a grid containing all Card View instances.
     * @param cols Number of columns.
     * @param rows Number of rows.
     * @return The multicard view.
     */
    MultiCardView createMultiCardView(int cols, int rows);

}
