package us.heptet.magewars.ui;

/* Created by kay on 4/9/2014. */
/**
 * Interface for a generic container.
 */
public interface Container extends Control {
    /**
     * Add a control to the container.
     * @param control
     */
    void add(Control control);

    /**
     * Remove a control from the container.
     * @param control
     */
    void remove(Control control);

    /**
     * Clear the container of controls.
     */
    void clear();
}
