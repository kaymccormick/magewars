package us.heptet.magewars.ui;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/* Created by kay on 6/17/2014. */
/**
 * Not sure, some kind of resizing code for undecorated jfx windows
 */
public class UndecoratedResize {
    class Resize
    {
        boolean inResizeZone = false;
        boolean resizing = false;

        double x;
        double y;
        Cursor oldCursor;
    }

    /**
     * Create an instance.
     * @param region
     * @param gameStage
     */
    public UndecoratedResize(final Node region, final Stage gameStage) {
        final Resize resize = new Resize();
        region.addEventFilter(MouseEvent.MOUSE_PRESSED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Mouse pressed");
                if (resize.inResizeZone) {
                    System.out.println("Mouse pressed in resize zone");

                    resize.x = event.getSceneX();
                    resize.y = event.getSceneY();
                    resize.resizing = true;
                }
            }
        });


        region.addEventFilter(MouseEvent.MOUSE_RELEASED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resize.resizing = false;
            }
        });
        region.addEventFilter(MouseEvent.MOUSE_DRAGGED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (resize.resizing) {
                    gameStage.setWidth(gameStage.getWidth() + event.getSceneX() - resize.x);
                    gameStage.setHeight(gameStage.getHeight() + event.getSceneY() - resize.y);
                    resize.x = event.getSceneX();
                    resize.y = event.getSceneY();
                }
            }
        });

        gameStage.addEventFilter(MouseEvent.MOUSE_MOVED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double range = 13;
                double x = event.getSceneX();
                double y = event.getSceneY();
                double xd = Math.abs(gameStage.getWidth() - x);
                double yd = Math.abs(gameStage.getHeight() - y);
                if ((xd < range && yd < range) && !resize.inResizeZone) {
                    resize.oldCursor = region.getCursor();
                    region.setCursor(Cursor.NW_RESIZE);
                    resize.inResizeZone = true;
                } else if (resize.inResizeZone && (xd >= range || yd >= range)) {
                    region.setCursor(resize.oldCursor);
                    resize.inResizeZone = false;
                }
            }
        });

    }
}
