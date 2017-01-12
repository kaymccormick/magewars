package us.heptet.magewars.ui.vaadin;

import us.heptet.magewars.ui.Button;
import us.heptet.magewars.ui.EventHandler;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class ButtonImpl implements Button {
    com.vaadin.ui.Button button;

    public ButtonImpl(String text) {
        button = new com.vaadin.ui.Button(text);
    }

    @Override
    public void setOnActionHandler(final EventHandler handler) {
        button.addClickListener(new com.vaadin.ui.Button.ClickListener() {
            @Override
            public void buttonClick(com.vaadin.ui.Button.ClickEvent clickEvent) {
                handler.handle();
            }
        });
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public Object getControl() {
        return button;
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
