package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.ui.Control;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/9/2014. */

/**
 * Our wrapper for a JavaFX pane.
 * @param <T> Type of Pane to wrap.
 */
public class ContainerImpl<T extends Pane> implements Container {
    private static Logger logger = Logger.getLogger(ContainerImpl.class.getName());
    private T control;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Construct a wrapper instance for the given pane.
     * @param control
     */
    public ContainerImpl(T control) {
        this.control = control;
    }

    @Override
    public void add(Control control) {
        logger.fine("Adding " + control.getControl().toString() + " to " + this.control.toString());
        this.control.getChildren().add((Node)control.getControl());

    }

    @Override
    public void remove(Control control) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.control.getChildren().clear();
    }

    @Override
    public void setId(String id) {
        this.control.setId(id);
    }

    @Override
    public void setVisible(boolean visible) {
        this.control.setVisible(visible);

    }

    @Override
    public Object getControl() {
        return this.control;
    }

    @Override
    public double getWidth() {
        return control.getWidth();
    }

    @Override
    public double getHeight() {
        return control.getHeight();
    }
}
