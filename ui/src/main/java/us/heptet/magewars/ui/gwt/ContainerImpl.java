package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.ui.Control;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/9/2014. */
/**
 * GWT Container implementation.
 */
public class ContainerImpl extends ControlImpl implements Container {
    private static Logger logger = Logger.getLogger(ContainerImpl.class.getName());
    private FlowPanel panel;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create an instance.
     */
    public ContainerImpl() {
        panel = new FlowPanel();
        initWidget(panel);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        logger.info("I'm being attached!");
    }

    @Override
    public void add(Control control) {
        panel.add((Widget)control.getControl());
    }

    @Override
    public void remove(Control control) { panel.remove((Widget)control.getControl()); }

    @Override
    public void clear() {
        panel.clear();
    }

    @Override
    public Object getControl() {
        return this;
    }
}
