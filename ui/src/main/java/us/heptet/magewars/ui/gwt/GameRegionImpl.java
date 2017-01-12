package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.ui.FlowPanel;
import us.heptet.magewars.domain.game.GameElement;
import us.heptet.magewars.ui.GameRegion;

/* Created by kay on 4/26/2014. */

/**
 * Game region implementation.
 * @param <T> type of game element
 */
public class GameRegionImpl<T extends GameElement>
        extends ControlImpl
        implements GameRegion<T> {
    private T gameElement;
    private FlowPanel flowPanel;

    /**
     * Create an instance
     * @param gameElement
     */
    public GameRegionImpl(T gameElement) {
        this.gameElement = gameElement;
        flowPanel = new FlowPanel();
        initWidget(flowPanel);
    }

    @Override
    public T getGameElement() {
        return gameElement;
    }

    @Override
    public void setGameElement(T gameElement) {
        this.gameElement = gameElement;
    }

    protected FlowPanel getFlowPanel() {
        return flowPanel;
    }
}
