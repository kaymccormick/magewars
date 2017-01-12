package us.heptet.magewars.ui;

import us.heptet.magewars.domain.game.GameObject;

/* Created by kay on 3/4/14. */
/**
 * Retrieve the view for a GameObject in the form of a reference
 * to its Control interface.
 * <h2>Reverse Dependencies:</h2>
 * <ul>
 *     <li><a href="javafx/ZoneViewImpl.html">us.heptet.magewars.ui.javafx.ZoneViewImpl</a></li>
 * </ul>
 *
 */
@FunctionalInterface
public interface GameObjectViewFunction {
    /**
     * Get the view for a game object.
     * @param gameObject The game object.
     * @return The game object view.
     */
    Control getGameObjectView(GameObject gameObject);
}
