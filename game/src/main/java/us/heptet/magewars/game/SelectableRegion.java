package us.heptet.magewars.game;

import us.heptet.magewars.domain.game.GameElement;
import us.heptet.magewars.game.Selectable;

/* Created by kay on 3/8/14. */

/**
 * A region that is selectable.
 * @param <T> Type of game element that the region represents.
 */
public interface SelectableRegion<T extends GameElement>
        extends Selectable {
    /**
     * Trigger the UI change for the "default" unselected state
     */
    void defaultState();

    /**
     * Get the underlying game element.
     * @return Game element
     */
    T getGameElement();
}
