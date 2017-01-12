package us.heptet.magewars.ui.gwt;

import us.heptet.magewars.ui.Button;
import us.heptet.magewars.ui.EventHandler;

/* Created by kay on 4/9/2014. */
/**
 * GWT Button implementation
 */
public class ButtonImpl extends ControlImpl implements Button {
    com.google.gwt.user.client.ui.Button button;

    /**
     * Create a button
     * @param text
     */
    public ButtonImpl(String text) {
        button = new com.google.gwt.user.client.ui.Button(text);
        initWidget(button);
    }

    @Override
    public void setId(String id) {
        /* not implemented */
    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public void setOnActionHandler(final EventHandler handler) {
        button.addClickHandler(event -> handler.handle());
    }
}
