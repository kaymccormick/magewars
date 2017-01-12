package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.VerticalBox;

/* Created by kay on 4/10/2014. */
/**
 * Vertical box implementation, wrapper class
 */
public class VerticalBoxImpl extends Composite implements VerticalBox {
    private VerticalPanel verticalPanel;

    /**
     * Create an instance.
     */
    public VerticalBoxImpl() {
        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
    }

    @Override
    public void setId(String id) {
    }

    @Override
    public void setVisible(boolean visible) {

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

    @Override
    public void add(Control control) {
        verticalPanel.add((Widget)control.getControl());
    }

    @Override
    public void remove(Control control) {

    }

    @Override
    public void clear() {
        verticalPanel.clear();
    }
}
