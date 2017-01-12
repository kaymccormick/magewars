package us.heptet.magewars.ui.gwt;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

/* Created by kay on 6/3/2014. */
/**
 * Action marker, GWT version
 */
public class ActionMarker extends ControlImpl {
    /**
     * Construct an action marker.
     */
    public ActionMarker() {
        boolean innerPoint = false;
        Canvas canvas = Canvas.createIfSupported();

        initWidget(canvas);
        addStyleName(UiResources.INSTANCE.gss().actionMarker());

        Context2d context2d = canvas.getContext2d();
        context2d.setFillStyle("#000000");
        double radius = 35;
        context2d.arc(radius, radius, radius, 0, 2 * Math.PI);
        context2d.fill();

        context2d.setFillStyle("#ffffff");


        context2d.beginPath();

        boolean firstPoint = true;
        for(double r = 0; r < 2 * Math.PI; r += Math.PI / 24)
        {
            double x;
            double y;
            if(!innerPoint) {
                x = radius * .80 * Math.cos(r) + radius;
                y = radius * .80 * Math.sin(r) + radius;
            }
            else
            {
                x = radius * .65 * Math.cos(r) + radius;
                y = radius * .65 * Math.sin(r) + radius;
            }
            if(firstPoint)
            {
                context2d.moveTo(x, y);
                firstPoint = false;
            }
            else
            {
                context2d.lineTo(x, y);
            }
            innerPoint = !innerPoint;
        }
        context2d.closePath();
        context2d.fill();

    }
}
