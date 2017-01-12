package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;
import us.heptet.magewars.ui.Control;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/30/2014. */
/**
 * Our wrapper for a generic javafx node.
 */
public class ControlImpl implements Control {
    private static Logger logger = Logger.getLogger("ControlImplLogger");
    protected Node control;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Greate an instance
     * @param control
     */
    public ControlImpl(Node control) {
        logger.info("setting control to " + (control == null ? "(null)" : control.toString()));
        this.control = control;
    }

    @Override
    public void setId(String id) {
        control.setId(id);
    }

    @Override
    public void setVisible(boolean visible) {
        control.setVisible(visible);
    }

    @Override
    public Object getControl() {
        return control;
    }

    @Override
    public double getWidth() {
        return control.getLayoutBounds().getWidth();
    }

    @Override
    public double getHeight() {
        return control.getLayoutBounds().getHeight();
    }
}
