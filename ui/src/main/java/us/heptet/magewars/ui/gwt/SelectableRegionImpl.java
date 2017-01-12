package us.heptet.magewars.ui.gwt;

import us.heptet.magewars.domain.game.GameElement;
import us.heptet.magewars.game.SelectableRegion;
import us.heptet.magewars.game.SelectionState;

/* Created by kay on 4/26/2014. */

/**
 * Implementation of the SelectableRegion interface.
 * @param <T> Type of game element
 */
public class SelectableRegionImpl<T extends GameElement>
        extends GameRegionImpl<T>
        implements SelectableRegion<T> {

    /**
     * Construct an instance.
     * @param gameElement Game element for the region.
     */
    public SelectableRegionImpl(T gameElement) {
        super(gameElement);
    }

    @Override
    public void defaultState() {

    }

    @Override
    public SelectionState getSelectionState() {
        return null;
    }

    @Override
    public void setSelectionState(SelectionState selectionState) {

    }
}
