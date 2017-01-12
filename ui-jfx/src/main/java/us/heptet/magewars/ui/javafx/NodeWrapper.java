package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;

/* Created by kay on 4/30/2014. */
/**
 * Some other kind of node wrapping class.
 * @param <T> Type of node to wrap
 */
public class NodeWrapper<T extends Node> extends ControlImpl {
    /**
     * Construct an instance.
     * @param control
     */
    public NodeWrapper(T control) {
        super(control);
    }
}
