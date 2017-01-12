package us.heptet.magewars.ui.vaadin;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.VerticalBox;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class VerticalBoxImpl implements VerticalBox {
    VerticalLayout verticalLayout;

    public VerticalBoxImpl() {
        verticalLayout = new VerticalLayout();
    }

    @Override
    public void add(Control control) {
        verticalLayout.addComponent((Component)control.getControl());
    }

    @Override
    public void remove(Control control) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public Object getControl() {
        return verticalLayout;
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
