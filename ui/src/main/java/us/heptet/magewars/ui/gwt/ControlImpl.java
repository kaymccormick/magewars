package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import us.heptet.magewars.ui.Control;

/* Created by kay on 4/30/2014. */
/**
 * Control implementation for wrapper
 */
public class ControlImpl extends Composite implements Control {
    @Override
    public void setId(String id) {
        DOM.setElementAttribute(getElement(), "id", id);
    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public double getWidth() {
        return getWidget().getOffsetWidth();
    }

    @Override
    public double getHeight() {
        return getWidget().getOffsetHeight();
    }
}
