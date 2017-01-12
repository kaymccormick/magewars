package us.heptet.magewars.ui.javafx;

import javafx.scene.layout.Region;
import us.heptet.magewars.domain.game.GameElement;
import us.heptet.magewars.ui.GameRegion;

/* Created by kay on 2/22/14. */

/***
 * Abstract base class for a GameRegion implementation. JavaFX-specific.
 * @param <T> Type of Game Element represented by the region.
 */
public abstract class GameRegionBase<T extends GameElement>
        extends Region implements GameRegion<T> {
    protected T gameElement;

    /**
     * Create instance,
     * @param gameElement
     */
    public GameRegionBase(T gameElement)
    {
        setGameElement(gameElement);
    }

    @Override
    public T getGameElement() {
        return gameElement;
    }

    @Override
    public void setGameElement(T gameElement) {
        this.gameElement = gameElement;
    }

}
