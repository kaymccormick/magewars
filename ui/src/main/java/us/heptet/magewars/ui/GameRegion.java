package us.heptet.magewars.ui;

import us.heptet.magewars.domain.game.GameElement;

/* Created by kay on 3/8/14. */

/**
 * Interface for a game region independent of UI toolkit.
 * @param <T>     Type of game element represented by the region.
 */
public interface GameRegion<T extends GameElement> extends Control {
    /**
     * Get the game element represented by the region.
     * @return
     */
    T getGameElement();

    /**
     * Set the game element represented by the region.
     * @param gameElement
     */
    void setGameElement(T gameElement);
}
