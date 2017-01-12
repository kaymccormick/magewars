package us.heptet.magewars.ui.javafx;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.domain.game.Zone;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by jade on 12/09/2016.
 */

/**
 * Zoom utility class.
 */
public class ZoomUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZoomUtil.class);
    private int numCols;
    private int numRows;
    private Deque<Double> savedZoomLevelScaling = new LinkedList<>();
    private SimpleDoubleProperty scaleRatio = new SimpleDoubleProperty(1.0);
    SimpleDoubleProperty unitWidth = new SimpleDoubleProperty();
    SimpleDoubleProperty unitHeight = new SimpleDoubleProperty();
    SimpleObjectProperty<Bounds> viewportBounds = new SimpleObjectProperty<>();

    ZoomUtil(double unitWidth, double unitHeight) {
        this.unitHeight.set(unitHeight);
        this.unitWidth.set(unitWidth);
    }

    void setZoomLevel(int numZones, boolean saveOldZoomLevel) {
        if (saveOldZoomLevel) {
            savedZoomLevelScaling.push(scaleRatio.get());
        }
        int num = numZones;
        double zoneWidth = getUnitWidth();
        double zoneHeight = getUnitHeight();

        DoubleBinding viewportWidth = Bindings.createDoubleBinding(() -> getViewportBounds().getWidth(), viewportBoundsProperty());
        DoubleBinding viewportHeight = Bindings.createDoubleBinding(() -> getViewportBounds().getHeight(), viewportBoundsProperty());

        scaleRatio.bind(Bindings.min(viewportWidth.divide(zoneWidth).divide(Math.min(getNumCols(), num)),
                viewportHeight.divide(zoneHeight).divide(Math.min(getNumRows(), num))));
    }

    /**
     * Restore the previous zoom level.
     */
    public void restoreZoomLevel() {
        scaleRatio.set(savedZoomLevelScaling.pop());
    }

    /**
     * focus on a particular zone.
     * @param zone
     */
    public void focusZone(Zone zone) {
        setZoomLevel(1, true);
    }


    private double getUnitWidth() {
        return unitWidth.get();
    }

    private double getUnitHeight() {
        return unitHeight.get();
    }

    private Bounds getViewportBounds() {
        return viewportBounds.get();
    }

    private SimpleObjectProperty<Bounds> viewportBoundsProperty() {
        return viewportBounds;
    }

    private int getNumCols() {
        return numCols;
    }

    void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    private int getNumRows() {
        return numRows;
    }

    void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    SimpleDoubleProperty scaleRatioProperty() {
        return scaleRatio;
    }

    void zoomIn() {
        scaleRatio.set(scaleRatio.get() + .2);
    }

    void zoomOut() {
        scaleRatio.set(scaleRatio.get() - .2);
    }

    void showNumUnits(int num) {
        assert num > 0;
        double zoneWidth = getUnitWidth();
        double zoneHeight = getUnitHeight();
        DoubleBinding viewportWidth = Bindings.createDoubleBinding(() -> getViewportBounds().getWidth(), viewportBoundsProperty());
        DoubleBinding viewportHeight = Bindings.createDoubleBinding(() -> getViewportBounds().getHeight(), viewportBoundsProperty());
        viewportWidth.addListener((ob, old, n) -> logger.info("width {}", n));
        viewportHeight.addListener((ob, old, n) -> logger.info("height {}", n));
        logger.info("{}x{}", viewportWidth.get(), viewportHeight.get());

        logger.info("zoneWidth = {}", zoneWidth);
        logger.info("zoneHeight = {}", zoneWidth);
        scaleRatio.bind(Bindings.min(viewportWidth.divide(zoneWidth).divide(Math.min(getNumCols(), num)),
                viewportHeight.divide(zoneHeight).divide(Math.min(getNumRows(), num))));
        logger.info("scaleratio = {}", scaleRatio.get());

    }
}
