package us.heptet.magewars.ui.vaadin;

import us.heptet.magewars.ui.Label;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class LabelImpl implements Label {
    com.vaadin.ui.Label label;

    public LabelImpl(String text) {
        label = new com.vaadin.ui.Label(text);
    }

    public void setText(String text) {
        label.setCaption(text);

    }

    @Override
    public void setId(String id) {
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public Object getControl() {
        return label;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
}
