package us.heptet.magewars.ui.javafx;

import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import us.heptet.magewars.ui.Button;
import us.heptet.magewars.ui.EventHandler;

/* Created by kay on 4/9/2014. */
/**
 * JavaFX button implementation/wrapper thingy.
 */
public class ButtonImpl extends Region implements Button {
    private javafx.scene.control.Button button;

    /**
     * Create a button.
     * @param text
     */
    public ButtonImpl(String text) {
        button = new javafx.scene.control.Button(text);
        getChildren().add(button);
    }

    @Override
    public Object getControl() {
        return button;
    }

    @Override
    public void setOnActionHandler(final EventHandler handler) {
        button.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handler.handle();
            }
        });

    }
}
