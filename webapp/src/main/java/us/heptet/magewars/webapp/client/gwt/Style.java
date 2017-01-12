package us.heptet.magewars.webapp.client.gwt;

import com.google.gwt.resources.client.CssResource;

/**
 * Created by jade on 12/08/2016.
 */

/***
 * Interface for GSS classes.
 */
public interface Style extends CssResource {
    /**
     * test css class (Although it is used and should be renamed)
     * @return CSS class name
     */
    String mytest();

    /**
     * Style for login-related labels
     * @return CSS class name
     */
    String loginLabel();

    /**
     * Style for the login panel
     * @return CSS class name
     */
    String loginPanel();

    /**
     * Style for the login button
     * @return CSS class name
     */
    String loginButton();

    /**
     * Style for the "main view" panel
     * @return CSS class name
     */
    String mainViewPanel();

    /**
     * Style for a "main action"
     * @return CSS class name
     */
    String mainAction();

    /**
     * Style for the "main view" header
     * @return CSS class name
     */
    String mainHeader();

    /**
     * Style for the "Start" button
     * @return CSS class name
     */
    String startButton();

    /**
     * Style for the current stage label
     * @return CSS class name
     */
    String stageLabel();

    /**
     * Style for the phase label
     * @return CSS class name
     */
    String phaseLabel();

    /**
     * Style for the next phase button
     * @return CSS class name
     */
    String nextPhaseButton();

    /**
     * Style for 0 opacity
     * @return CSS class name
     */
    String zeroOpacity();

    /**
     * Style for 1 opacity
     * @return CSS class name
     */
    String oneOpacity();
}
