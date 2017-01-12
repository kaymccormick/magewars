package us.heptet.magewars.ui.javafx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/* Created by kay on 3/9/14. */
/**
 * A class for representing a quickcast marker. It is a javaFX region / control.
 */
public class QuickcastMarker extends Region {
    private SimpleBooleanProperty active = new SimpleBooleanProperty();
    private double radius = 35;

    /**
     * Construct an instance.
     */
    public QuickcastMarker() {
        Circle outerCircle = new Circle(radius, radius, radius, Color.BLACK);
        double[] points = new double[] {
                12.75, 0.0,
                19.25, 5.5,
                26.25, 0.0,
                37.5, 7.75,
                37.5, 25.75,
                39.75, 27.471000000000004,
                30.75, 27.471000000000004,
                29.0, 26.0,
                29.0, 10.0,
                26.25, 8.25,
                24.5, 11.25,
                24.0, 25.5,
                26.25, 27.25,
                14.0, 27.471000000000004,
                15.5, 25.5,
                15.25, 10.5,
                12.75, 9.25,
                10.25, 11.0,
                10.25, 25.5,
                8.75, 27.25,
                0.0, 27.471000000000004,
                1.5, 25.5,
                1.5, 8.25
        };

        Polygon polygon = new Polygon(points);
        Polygon polygon2 = new Polygon(points);
        polygon.setFill(Color.WHITE);
        polygon2.setFill(Color.WHITE);
        polygon2.getTransforms().add(new Rotate(180, 19.875, 29.47));
        Group polygonGroup = new Group();
        polygonGroup.visibleProperty().bind(activeProperty());
        polygonGroup.getChildren().addAll(polygon, polygon2);
        polygonGroup.setTranslateX(15);
        polygonGroup.setTranslateY(6);

        getStyleClass().addAll("quickcast-marker", "marker");
        getChildren().addAll(outerCircle, polygonGroup);
//        getChildren().add(polygon2);
    }

    public boolean getActive() {
        return active.get();
    }

    /**
     * JavaFX property.
     * @return
     */
    public SimpleBooleanProperty activeProperty() {
        return active;
    }
}
