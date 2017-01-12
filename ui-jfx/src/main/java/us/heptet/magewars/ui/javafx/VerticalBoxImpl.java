package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.VerticalBox;

/* Created by kay on 4/10/2014. */
/**
 * A Wrapper around a JavaFX VBox
 */
public class VerticalBoxImpl implements VerticalBox {
    VBox vBox;

    /**
     * Construct an instance
     */
    public VerticalBoxImpl() {
        vBox = new VBox();
    }

    @Override
    public void add(Control control) {
        vBox.getChildren().add((Node)control.getControl());
    }

    @Override
    public void remove(Control control) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        vBox.getChildren().clear();

    }

    @Override
    public void setId(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVisible(boolean visible) {
        vBox.setVisible(false);
    }

    @Override
    public Object getControl() {
        return vBox;
    }

    @Override
    public double getWidth() {
        return vBox.getWidth();
    }

    @Override
    public double getHeight() {
        return getHeight();
    }
}
