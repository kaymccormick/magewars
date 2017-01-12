package us.heptet.magewars.ui.javafx;

import javafx.scene.shape.Polygon;

/* Created by kay on 3/4/14. */
/**
 *
 * <img src="doc-files/directed-arrow.png" alt="directed arrow">
 */
public class DirectedArrow extends Polygon {
    /***
     * Create a directed arrow.
     */
    public DirectedArrow()
    {
        double shaftLength = 100;
        double shaftHeight = 20;
        double headHeight = 40;
        double headlength = 40;
        double shaftY = (headHeight - shaftHeight) / 2;
        double tipX = shaftLength + headlength;
        getPoints().addAll(0.0, shaftY,
                shaftLength, shaftY,
                shaftLength, 0.0,
                tipX, headHeight / 2,
                shaftLength, headHeight,
                shaftLength, headHeight - shaftY,
                0.0, headHeight - shaftY
        );
    }
}
