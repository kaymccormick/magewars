package us.heptet.magewars.ui;

/* Created by kay on 4/8/2014. */
/**
 * Inteface for a generic button.
 */
public interface Button extends Control {
    /***
     * Set the handler for when the button is clicked.
     * @param handler The handler.
     */
    void setOnActionHandler(EventHandler handler);
}
