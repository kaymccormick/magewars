package us.heptet.magewars.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import us.heptet.magewars.domain.game.GameElement;
import us.heptet.magewars.game.SelectableRegion;
import us.heptet.magewars.game.SelectionState;
import us.heptet.magewars.ui.javafx.GameRegionBase;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 2/15/14. */

/***
 * This is javafx specific, despite appearing in the general ui module.
 * selectable region base type
 * TODO update javadoc.
 *
 * @param <T> type of game element
 */
public class SelectableRegionBase<T extends GameElement>
        extends GameRegionBase<T> implements SelectableRegion<T> {
    private static Logger logger = Logger.getLogger(SelectableRegionBase.class.getName());
    private SimpleObjectProperty<SelectionState> selectionState = new SimpleObjectProperty<>();
    //1.8
//    private Border oldBorder;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Create instance.
     * @param gameElement game element..
     */
    public SelectableRegionBase(T gameElement) {
        super(gameElement);
        addEventHandler(MouseEvent.MOUSE_ENTERED, SelectableRegionBase.this::onMouseEntered);
        addEventHandler(MouseEvent.MOUSE_EXITED, SelectableRegionBase.this::onMouseExited);
        addEventHandler(MouseEvent.MOUSE_CLICKED, SelectableRegionBase.this::onMouseClicked);
    }

    /***
     * Create instance (jfx specific)
     * @param gameElement
     * @param contentNode
     */
    public SelectableRegionBase(T gameElement, Node contentNode) {
        this(gameElement);
        getChildren().add(contentNode);
    }

    @Override
    public void defaultState() {
        //1.8 setBorder(Border.EMPTY);
    }

    private void onMouseEntered(MouseEvent event) {

        logger.fine("Entered selectable region for " + getGameElement());
        SelectionState state = getSelectionState();

        logger.fine("Selection state = " + (state == null ? "(null)" : state.toString()));
        if (state == null || !state.isSelecting()) {
            return;
        }
        if (state.isSelectable(getGameElement())) {
            logger.fine("Setting hovered region");
            state.setHoveredRegion(this);
//1.8
//            oldBorder = getBorder();
//1.8
//            setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(6), new BorderWidths(3), new Insets(-2))));
        }
    }

    private void onMouseExited(MouseEvent event) {
        if (getSelectionState() != null && getSelectionState().getHoveredRegion() == this) {
            getSelectionState().setHoveredRegion(null);
//1.8
//            setBorder(oldBorder);
        }
    }

    private void onMouseClicked(MouseEvent event) {
        SelectionState state = getSelectionState();
        logger.fine("Selectable Region clicked  for " + getGameElement());

        if (state != null && state.isSelectable(getGameElement())) {
            logger.fine("Acquiring selection target.");
            state.acquireSelectionTarget(getGameElement());
            event.consume();
            //state.setSelectionTarget(getGameElement());
        }


    }

    @Override
    public SelectionState getSelectionState() {
        return selectionState.get();
    }

    @Override
    public void setSelectionState(SelectionState selectionState) {
        this.selectionState.set(selectionState);
    }

    @Override
    public Object getControl() {
        return this;
    }
}
