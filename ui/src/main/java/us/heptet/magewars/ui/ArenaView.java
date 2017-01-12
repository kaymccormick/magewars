package us.heptet.magewars.ui;

import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.ZoneView;

/* Created by kay on 4/30/2014. */
/**
 * Interface for an ArenaView
 */
public interface ArenaView extends Control {
    /**
     * Retrieve a zone view.
     * @param col Column of zone.
     * @param row Row of zone.
     * @return Zone view.
     */
    ZoneView getZoneView(int col, int row);
}
