package us.heptet.magewars.ui;

/* Created by kay on 4/9/2014. */
/**
 * Control interface. Some common methods allowing us to treat multifarious controls in a similar manner.
 */
public interface Control {
    /***
     * Set the "id" of the control.
     * @param id
     */
    void setId(String id);

    /**
     * Set whether the control is visible.
     * @param visible Boolean, true for visible.
     */
    void setVisible(boolean visible);

    /**
     * Get the underlying object that represents the control
     * in the user interface toolkit of choice, either JavaFX
     * or GWT. May or may not be different from the object that
     * implements the Control interface.
     * @return An object.
     */
    Object getControl();

    /***
     * Get the width of the control.
     * @return
     */
    double getWidth();

    /**
     * Get the height of the control.
     * @return
     */
    double getHeight();
}
