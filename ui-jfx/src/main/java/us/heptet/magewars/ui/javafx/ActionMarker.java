package us.heptet.magewars.ui.javafx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/* Created by kay on 2/5/14. */
/**
 *
 */
class ActionMarker extends Region {
    private double radius = 35;
    private SimpleBooleanProperty active = new SimpleBooleanProperty();

    ActionMarker()
    {
        Polygon p = new Polygon();
        p.getStyleClass().add("action-marker-star");
        p.setFill(Color.WHITE);
        boolean innerPoint = false;
        getStyleClass().addAll("action-marker", "marker");
        for(double r = 0; r < 2 * Math.PI; r += Math.PI / 24)
        {
            if(!innerPoint)
                p.getPoints().addAll(radius * .85 * Math.cos(r) + radius, radius * .85 * Math.sin(r) + radius);
            else
                p.getPoints().addAll(radius * .70 * Math.cos(r) + radius, radius * .70 * Math.sin(r) + radius);
            innerPoint = !innerPoint;

        }
        //p.setTranslateX(radius);
        //p.setTranslateY(radius);
        //p.opacityProperty().bind(opacityProperty());
        //getChildren().add(p);
        //Polygon activePolygon = new Polygon();
        Circle outerCircle = new Circle(radius, radius, radius, Color.BLUE);
        outerCircle.getStyleClass().add("circle");
        //outerCircle.opacityProperty().bind(opacityProperty());
        //Circle innerCircle = new Circle(radius, radius, radius - 10, Color.WHITE);
        p.visibleProperty().bind(activeProperty());

        getChildren().addAll(outerCircle, p);

        }

    public boolean getActive() {
        return active.get();
    }

    SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }
}
