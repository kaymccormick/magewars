package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.ZoneView;

import java.util.IdentityHashMap;

/* Created by kay on 4/26/2014. */

/**
 * Zone view implementation.
 */
public class ZoneViewImpl
        extends SelectableRegionImpl<Zone>
        implements ZoneView {
    private IdentityHashMap<GameObject, Object> arenaObjectMap = new IdentityHashMap<>();
    Container container;

    /**
     * Create an instnace.
     *
     * @param gameElement
     * @param uiFactory
     */
    public ZoneViewImpl(Zone gameElement, final UiFactory uiFactory) {
        super(gameElement);
        container = uiFactory.createControlContainer();
        getFlowPanel().add((Widget) container.getControl());
    }

    @Override
    public void add(GameObject arenaObject, Object gameObjectView) {
        arenaObjectMap.put(arenaObject, gameObjectView);
        container.add((Control) gameObjectView);
    }

    @Override
    public void remove(GameObject obj) {
        Object n = arenaObjectMap.get(obj);
        assert n != null;
        container.remove((Control) n);
        arenaObjectMap.remove(obj);
    }

}
