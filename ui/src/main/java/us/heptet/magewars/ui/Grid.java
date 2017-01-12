package us.heptet.magewars.ui;

/* Created by kay on 4/9/2014. */
/**
 * Wrapper interface for a UI Grid.
 */
public interface Grid extends Control {
    /**
     * Add a wrapped control to the grid.
     * @param control
     * @param col
     * @param row
     */
    void add(Control control, int col, int row);

    /**
     * Remove a wrapped control from the grid.
     * @param control
     */
    void remove(Control control);
}
