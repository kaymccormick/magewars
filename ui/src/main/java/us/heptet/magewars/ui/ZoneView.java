package us.heptet.magewars.ui;

import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.game.SelectableRegion;

/* Created by kay on 4/25/2014. */
/**
 * Interface for ZoneView.
 */
public interface ZoneView extends GameRegion<Zone>, SelectableRegion<Zone> {
    /**
     * Add a game object.
     * @param arenaObject
     * @param gameObjectView
     */
    void add(GameObject arenaObject, Object gameObjectView);

    /**
     * Remove a game object.
     * @param obj
     */
    void remove(GameObject obj);
}
