package us.heptet.magewars.ui.javafx;

import us.heptet.magewars.ui.Label;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/10/2014. */
/**
 * Wrapper class for a JavaFX Label
 */
public class LabelImpl extends ControlImpl implements Label {
    private static Logger logger = Logger.getLogger("LabelImplLogger");
    javafx.scene.control.Label label;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Construct an instance.
     * @param text
     */
    public LabelImpl(String text) {
        super(new javafx.scene.control.Label(text));
        label = (javafx.scene.control.Label)getControl();
        logger.info("label = " + (label == null ? "(null)" : label.toString()));
    }

    @Override
    public void setText(String text) {
        logger.info("setting text for " + this.label + " to " + text);
        label.setText(text);
    }
}
