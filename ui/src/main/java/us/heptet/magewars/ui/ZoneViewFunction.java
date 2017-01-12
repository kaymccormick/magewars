package us.heptet.magewars.ui;


import us.heptet.magewars.domain.game.Zone;

/* Created by kay on 3/4/14. */
/**
 * Functional interface to get a zone view.
 */
@FunctionalInterface
public interface ZoneViewFunction {
    /**
     * Get the zone view for a zone.
     * @param zone
     * @param gameObjectViewFunction
     * @return
     */
    ZoneView getZoneView(Zone zone, GameObjectViewFunction gameObjectViewFunction);
}
