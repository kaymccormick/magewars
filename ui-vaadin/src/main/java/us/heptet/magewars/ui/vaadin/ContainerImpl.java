package us.heptet.magewars.ui.vaadin;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.ui.Control;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class ContainerImpl implements Container {
    Panel panel;

    public ContainerImpl() {
        panel = new Panel();
    }

    @Override
    public void add(Control control) {
        panel.setContent((Component)control.getControl());
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
        return panel;
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
